package com.example.demo.services;

import com.example.demo.common.models.ResponseDTO;
import com.example.demo.common.utils.ResponseUtil;
import com.example.demo.models.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.example.demo.common.Constants.*;
import static com.example.demo.common.utils.ExpenseUtil.validateExpense;

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
            TreeMap<LocalDateTime, Set<Double>> validatedTransactions = new TreeMap<>();
            TransactionValidatorResponse response = new TransactionValidatorResponse();
            response.setValid(new ArrayList<>());
            response.setInvalid(new ArrayList<>());
            for (TransactionModel transaction : transactions) {

                String message = validateExpense(transaction.getAmount(), transaction.getDate(), validatedTransactions);
                if (transaction.getCeiling() % transactionRoundingOffset != 0)
                    message += "Ceiling is not multiple of rounding offset. ";
                if (transaction.getAmount() + transaction.getRemanent() != transaction.getCeiling())
                    message += "Ceiling, Remanent and Amount do not match. ";


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

    @Override
    public TransactionFilterResponse filterTransactions(Double wage, List<ExpenseModel> expenses, List<QPeriodModel> q, List<PPeriodModel> p, List<KPeriodModel> k, String endPoint) throws Exception{
        try{

            // -1 value for kstart period
            // -2 value for kend period
            // non-negative values for amounts
            TreeMap<LocalDateTime, Set<Double>> allExpensesAndPeriodsByDate = new TreeMap<>();
            TransactionFilterResponse response = new TransactionFilterResponse();
            response.setValid(new ArrayList<>());
            response.setInvalid(new ArrayList<>());

            for(KPeriodModel kperiod : k){
                if(!allExpensesAndPeriodsByDate.containsKey(kperiod.getStart()))
                    allExpensesAndPeriodsByDate.put(kperiod.getStart(), new HashSet<>());
                allExpensesAndPeriodsByDate.get(kperiod.getStart()).add((double) -1);

                if(!allExpensesAndPeriodsByDate.containsKey(kperiod.getEnd()))
                    allExpensesAndPeriodsByDate.put(kperiod.getEnd(), new HashSet<>());
                allExpensesAndPeriodsByDate.get(kperiod.getEnd()).add((double) -2);
            }
            for(ExpenseModel expense: expenses){
                String msg = validateExpense(expense.getAmount(), expense.getDate(), allExpensesAndPeriodsByDate);
                if(msg == ""){
                    if(!allExpensesAndPeriodsByDate.containsKey(expense.getDate()))
                        allExpensesAndPeriodsByDate.put(expense.getDate(), new HashSet<>());
                    allExpensesAndPeriodsByDate.get(expense.getDate()).add(expense.getAmount());
                }
                else{
                    TransactionModel transaction = new TransactionModel(expense.getAmount(), expense.getDate());
                    InvalidTransaction invalidTransaction = new InvalidTransaction();
                    invalidTransaction.setAmount(transaction.getAmount());
                    invalidTransaction.setDate(transaction.getDate());
                    invalidTransaction.setCeiling(transaction.getCeiling());
                    invalidTransaction.setRemanent((transaction.getRemanent()));
                    invalidTransaction.setMessage(msg);
                    response.getInvalid().add(invalidTransaction);
                }
            }

            int activeKPeriods = 0;
            for(Map.Entry<LocalDateTime, Set<Double>> entry
                    : allExpensesAndPeriodsByDate.entrySet()){
                LocalDateTime date = entry.getKey();
                Set<Double> valueList = entry.getValue();
                for(Double amt : valueList){
                            if (amt == (double) -1) {
                                activeKPeriods++;
                            }
                            else if(amt == (double) -2){
                                activeKPeriods--;
                            }
                            else{
                                TransactionModel transaction = new TransactionModel(amt, date);
                                if(activeKPeriods > 0){
                                    ValidTransaction validTransaction = new ValidTransaction(
                                            transaction.getAmount(),
                                            transaction.getDate(),
                                            transaction.getCeiling(),
                                            transaction.getRemanent(),
                                            true
                                    );
                                    response.getValid().add(validTransaction);
                                }
                                else{
                                    InvalidTransaction invalidTransaction = new InvalidTransaction();
                                    invalidTransaction.setAmount(transaction.getAmount());
                                    invalidTransaction.setDate(transaction.getDate());
                                    invalidTransaction.setCeiling(transaction.getCeiling());
                                    invalidTransaction.setRemanent((transaction.getRemanent()));
                                    invalidTransaction.setMessage("Transaction not in k periods");
                                    response.getInvalid().add(invalidTransaction);
                                }
                            }
                        }
            }
            return response;
        }
        catch (Exception e){
            throw e;
        }
    }
}





