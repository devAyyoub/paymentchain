/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package com.paymentchain.transaction.controller;

import com.paymentchain.transaction.entities.Transaction;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import com.paymentchain.transaction.repository.TransactionRepository;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author ayyoub
 */
@RestController
@RequestMapping("/transaction")
public class TransactionRestController {
    
    @Autowired
    TransactionRepository transactionRepository;
    
    @GetMapping()
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable long id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if (transaction.isPresent()) {
            return new ResponseEntity<>(transaction.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/customer/transactions")
    public List<Transaction> get(@RequestParam(name = "ibanAccount") String ibanAccount){
        return transactionRepository.findByIbanAccount(ibanAccount);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable long id, @RequestBody Transaction input) {
        Transaction find = transactionRepository.findById(id).get();
        if (find != null) {
            find.setAmount(input.getAmount());
            find.setChannel(input.getChannel());
            find.setDate(input.getDate());
            find.setDescription(input.getDescription());
            find.setFee(input.getFee());
            find.setIbanAccount(input.getIbanAccount());
            find.setReference(input.getReference());
            find.setStatus(input.getStatus());
        }
        Transaction save = transactionRepository.save(find);
        return ResponseEntity.ok(save);
    }
    
    @PostMapping
    public ResponseEntity<?> post(@RequestBody Transaction input) {
        Transaction save = transactionRepository.save(input);
        return ResponseEntity.ok(save);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
         transactionRepository.deleteById(id);
         return new ResponseEntity<>(HttpStatus.OK);
    }
    
}
