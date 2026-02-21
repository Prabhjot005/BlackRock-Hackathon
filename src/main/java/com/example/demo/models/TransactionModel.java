package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static com.example.demo.common.Constants.transactionRoundingOffset;

@Data
@NoArgsConstructor
public class TransactionModel {

    private Double amount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;
    private Double ceiling;
    private Double remanent;

    public TransactionModel(Double amount, LocalDateTime date){
        this.amount = amount;
        this.date = date;
        this.ceiling = transactionRoundingOffset + this.amount -
                (this.amount%transactionRoundingOffset);
        this.remanent = ceiling - amount;

    }

}