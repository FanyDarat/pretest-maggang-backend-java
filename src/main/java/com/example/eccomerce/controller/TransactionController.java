package com.example.eccomerce.controller;

import com.example.eccomerce.model.Transaction;
import com.example.eccomerce.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public List<Transaction> getAll() {
        return transactionService.getAll();
    }

    @GetMapping("/{id}")
    public Transaction getById(@PathVariable Long id) {
        return transactionService.getById(id);
    }

    @PostMapping("/checkout")
    public Transaction checkout() {
        return transactionService.createTransaction();
    }

    @PutMapping("/{id}")
    public Transaction updateStatus(@PathVariable Long id, @RequestBody Map<String,String> body) {
        return transactionService.updateStatus(id, body.get("status"));
    }
}
