/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paymentchain.product.controller;

import com.paymentchain.product.business.transactions.BusinessTransactionProduct;
import com.paymentchain.product.entities.Product;
import com.paymentchain.product.exception.BusinessRuleException;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import java.util.Optional;

/**
 *
 * @author ayyoub
 */
@RestController
@RequestMapping("/product")
public class ProductRestController {

    @Autowired
    BusinessTransactionProduct bt;

    @GetMapping()
    public List<Product> list() throws BusinessRuleException {
        return bt.list();
    }

    @GetMapping("/{id}")
    public Optional<Product> get(@PathVariable(name = "id") long id) throws BusinessRuleException {
        return bt.get(id);      
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable(name = "id") long id, @RequestBody Product input) {
        return bt.put(id, input);
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody Product input) throws BusinessRuleException {
        return bt.post(input);
    }

    @DeleteMapping("/{id}")
    public Optional<Product> delete(@PathVariable(name = "id") long id) throws BusinessRuleException {
        return bt.delete(id);
    }

}
