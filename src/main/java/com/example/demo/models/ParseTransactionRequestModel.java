package com.example.demo.models;

import lombok.Data;
import tools.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.util.List;

@Data
public class ParseTransactionRequestModel implements Serializable {
    protected List<ExpenseModel> transactions;
}
