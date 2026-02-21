package com.example.demo.models;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TransactionFilterRequest implements Serializable {
        private List<QPeriodModel> q;
        private List<PPeriodModel> p;
        private List<KPeriodModel> k;
        private Double wage;
        private List<ExpenseModel> transactions;
}
