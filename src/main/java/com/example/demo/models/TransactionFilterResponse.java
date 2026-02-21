package com.example.demo.models;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TransactionFilterResponse implements Serializable {
    List<ValidTransaction> valid;
    List<InvalidTransaction> invalid;
}
