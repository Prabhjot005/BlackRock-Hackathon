package com.example.demo.services;

import com.example.demo.common.models.ResponseDTO;
import com.example.demo.common.utils.ResponseUtil;
import com.example.demo.models.ExpenseModel;
import com.example.demo.models.TransactionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServiceImp implements TransactionService{
    private static final int STATUS_200 = 200;
    private static final String SUCCESSFULLY = "Success";

    @Override
    public ResponseEntity<ResponseDTO> parseTransactions(List<ExpenseModel> expenses, String apiTitle){
        List<TransactionModel> transactions = new ArrayList<>();
        for(ExpenseModel expense: expenses){
            transactions.add(new TransactionModel(expense.getAmount(), expense.getDate()));
        }
        return ResponseUtil.sendResponse(
                transactions,
                Timestamp.valueOf(LocalDateTime.now()),
                HttpStatus.OK,
                200,
                SUCCESSFULLY,
                apiTitle
                );
    }
}
