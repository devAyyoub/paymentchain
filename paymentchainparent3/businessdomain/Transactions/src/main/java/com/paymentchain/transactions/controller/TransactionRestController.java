
package com.paymentchain.transactions.controller;

import com.paymentchain.transactions.business.transactions.BusinessTransactionTransaction;
import com.paymentchain.transactions.dto.TransactionRequest;
import com.paymentchain.transactions.dto.TransactionResponse;
import com.paymentchain.transactions.entities.Transaction;
import com.paymentchain.transactions.exception.BusinessRuleException;
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
    public TransactionResponse get(@PathVariable(name = "id") long id) throws BusinessRuleException {
         return bt.get(id);
    }
    
    @GetMapping("/customer/transactions")
    public List<TransactionResponse> get(@RequestParam(name = "ibanAccount") String ibanAccount) throws BusinessRuleException {
      return bt.get(ibanAccount);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponse> put(@PathVariable(name = "id") long id, @RequestBody TransactionRequest input) {
        return bt.put(id, input);
    }
    
    @PostMapping
    public ResponseEntity<TransactionResponse> post(@RequestBody TransactionRequest input) throws BusinessRuleException {
        return bt.post(input);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") long id) throws BusinessRuleException {
        return bt.delete(id);
    }
    
}
