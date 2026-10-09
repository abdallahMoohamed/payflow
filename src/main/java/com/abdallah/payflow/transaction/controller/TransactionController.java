package com.abdallah.payflow.transaction.controller;

import com.abdallah.payflow.transaction.dto.DepositRequest;
import com.abdallah.payflow.transaction.dto.TransactionResponse;
import com.abdallah.payflow.transaction.dto.TransferRequest;
import com.abdallah.payflow.transaction.dto.WithdrawRequest;
import com.abdallah.payflow.transaction.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/deposit")
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse deposit(@Valid @RequestBody DepositRequest request) {
        return transactionService.deposit(request);
    }

    @PostMapping("/withdraw")
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse withdraw(@Valid @RequestBody WithdrawRequest request) {
        return transactionService.withdraw(request);
    }

    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse transfer(@Valid @RequestBody TransferRequest request) {
        return transactionService.transfer(request);
    }
}
