package com.example.demo.common.utils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

public class ExpenseUtil {
    public static String validateExpense(Double amount, LocalDateTime date, TreeMap<LocalDateTime, Set<Double>> validatedTransactions){
        String message = "";
        if (amount < 0)
            message += "Negative Amounts are not allowed. ";
        if (validatedTransactions.containsKey(date) &&
                validatedTransactions.get(date).contains(amount))
            message += "Duplicate Transaction. ";
        return message;
    }
}
