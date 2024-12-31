/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paymentchain.transactions.controller;

import com.paymentchain.transactions.business.transactions.BusinessTransactionTransaction;
import com.paymentchain.transactions.entities.Transaction;
import com.paymentchain.transactions.exception.BusinessRuleException;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author ayyoub
 */
@RestController
@RequestMapping("/transaction")
public class TransactionRestController {
    
    @Autowired
    BusinessTransactionTransaction bt;
      
    @GetMapping()
    public List<Transaction> list() {
        return bt.list();
    }
    
    @GetMapping("/{id}")
    public Optional<Transaction> get(@PathVariable(name = "id") long id) throws BusinessRuleException {
         return bt.get(id);
    }
    
    @GetMapping("/customer/transactions")
    public List<Transaction> get(@RequestParam(name = "ibanAccount") String ibanAccount) throws BusinessRuleException {
      return bt.get(ibanAccount);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable(name = "id") long id, @RequestBody Transaction input) {
        return bt.put(id, input);
    }
    
    @PostMapping
    public ResponseEntity<?> post(@RequestBody Transaction input) throws BusinessRuleException {
        return bt.post(input);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") long id) throws BusinessRuleException {
        return bt.delete(id);
    }
    
}
