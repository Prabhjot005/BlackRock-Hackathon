package com.example.demo.services;


import com.example.demo.common.models.ResponseDTO;
import com.example.demo.models.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TransactionService {

    ResponseEntity<ResponseDTO> parseTransactions(List<ExpenseModel> expenses, String apiTitle) throws Exception;

    TransactionValidatorResponse validateTransactions(Double wage, List<TransactionModel> transactions, String endPoint) throws  Exception;

    TransactionFilterResponse filterTransactions(Double wage, List<ExpenseModel> transactions, List<QPeriodModel> q, List<PPeriodModel> p, List<KPeriodModel> k, String endPoint) throws Exception;
}
