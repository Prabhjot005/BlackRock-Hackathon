package com.example.demo.services;


import com.example.demo.common.models.ResponseDTO;
import com.example.demo.models.ExpenseModel;
import com.example.demo.models.TransactionModel;
import com.example.demo.models.TransactionValidatorResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TransactionService {

    ResponseEntity<ResponseDTO> parseTransactions(List<ExpenseModel> expenses, String apiTitle) throws Exception;

    TransactionValidatorResponse validateTransactions(Double wage, List<TransactionModel> transactions, String endPoint) throws  Exception;
}
