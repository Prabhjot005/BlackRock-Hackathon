package com.example.demo.models;

import lombok.Data;

import java.util.List;

@Data
public class TransactionValidatorResponse {
    List<TransactionModel> valid;
    List<InvalidTransaction> invalid;
}

