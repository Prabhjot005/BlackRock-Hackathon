package com.example.demo.services;

import com.example.demo.common.models.ResponseDTO;
import com.example.demo.common.utils.ResponseUtil;
import com.example.demo.models.ExpenseModel;
import com.example.demo.models.InvalidTransaction;
import com.example.demo.models.TransactionModel;
import com.example.demo.models.TransactionValidatorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static com.example.demo.common.Constants.*;

@Service
public class TransactionServiceImp implements TransactionService{

    @Override
    public ResponseEntity<ResponseDTO> parseTransactions(List<ExpenseModel> expenses, String apiTitle) throws Exception{
        List<TransactionModel> transactions = new ArrayList<>();
        for(ExpenseModel expense: expenses){
            transactions.add(new TransactionModel(expense.getAmount(), expense.getDate()));
        }
        return ResponseUtil.sendResponse(
                transactions,
                Timestamp.valueOf(LocalDateTime.now()),
                HttpStatus.OK,
                STATUS_200,
                SUCCESSFULLY,
                apiTitle
                );
    }

    // Assuming that transaction amount can be greater than wage

    @Override
    public TransactionValidatorResponse validateTransactions(Double wage, List<TransactionModel> transactions, String endPoint) throws Exception {

        try {
            HashMap<LocalDateTime, Set<Double>> validatedTransactions = new HashMap<>();
            TransactionValidatorResponse response = new TransactionValidatorResponse();
            response.setValid(new ArrayList<>());
            response.setInvalid(new ArrayList<>());
            for (TransactionModel transaction : transactions) {

                String message = "";
                if (transaction.getAmount() < 0)
                    message += "Negative Amounts are not allowed. ";
                if (transaction.getCeiling() % transactionRoundingOffset != 0)
                    message += "Ceiling is not multiple of rounding offset. ";
                if (transaction.getAmount() + transaction.getRemanent() != transaction.getCeiling())
                    message += "Ceiling, Remanent and Amount do not match. ";
                if (validatedTransactions.containsKey(transaction.getDate()) &&
                        validatedTransactions.get(transaction.getDate()).contains(transaction.getAmount()))
                    message += "Duplicate Transaction. ";

                if (message == "") {

                    response.getValid().add(transaction);

                    if (!validatedTransactions.containsKey(transaction.getDate()))
                        validatedTransactions.put(transaction.getDate(), new HashSet<>());
                    validatedTransactions.get(transaction.getDate()).add(transaction.getAmount());

                } else {
                    InvalidTransaction invalidTransaction = new InvalidTransaction();
                    invalidTransaction.setAmount(transaction.getAmount());
                    invalidTransaction.setDate(transaction.getDate());
                    invalidTransaction.setCeiling(transaction.getCeiling());
                    invalidTransaction.setRemanent((transaction.getRemanent()));
                    invalidTransaction.setMessage(message);
                    response.getInvalid().add(invalidTransaction);
                }
            }
            return response;
        } catch (Exception e) {
            throw e;
        }
    }
}
