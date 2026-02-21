package com.example.demo.controllers;

import com.example.demo.common.models.ResponseDTO;
import com.example.demo.common.utils.ResponseUtil;
import com.example.demo.models.ExpenseModel;
import com.example.demo.models.ParseTransactionRequestModel;
import com.example.demo.services.TransactionService;
import jakarta.servlet.http.HttpServletMapping;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/blackrock/challenge")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/v1/transactions:parse")
    public ResponseEntity<ResponseDTO> parseTransactions(@RequestBody List<ExpenseModel> expenses) throws Exception {
        String endPoint = "/v1/transactions:parse";
        Timestamp landingTime = Timestamp.valueOf(LocalDateTime.now());
        // Authorize
        try {
            if (expenses != null) {
                return transactionService.parseTransactions(expenses, endPoint);
            } else {
                throw new BadRequestException("Transactions are null");
            }
        }
        catch (BadRequestException e){
            return ResponseUtil.sendErrorResponse("PARSETRANSACTION400", "Unable to parse expenses", landingTime, HttpStatus.BAD_REQUEST, 400);
        }
        catch (Exception e){
            return ResponseUtil.sendErrorResponse("PARSETRANSACTION500", "Exception Occured in parsing expenses", landingTime, HttpStatus.INTERNAL_SERVER_ERROR, 500);
        }
    }
}
