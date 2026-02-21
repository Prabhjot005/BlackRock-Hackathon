package com.example.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionValidatorRequest implements Serializable {

    private Double wage;
    private List<TransactionModel> transactions;
}
