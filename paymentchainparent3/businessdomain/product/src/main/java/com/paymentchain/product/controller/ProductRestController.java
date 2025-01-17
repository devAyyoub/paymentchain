
package com.paymentchain.product.controller;

import com.paymentchain.product.business.transactions.BusinessTransactionProduct;
import com.paymentchain.product.dto.ProductRequest;
import com.paymentchain.product.dto.ProductResponse;
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
    public List<ProductResponse> list() throws BusinessRuleException {
        return bt.list();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> get(@PathVariable(name = "id") long id) throws BusinessRuleException {
        return bt.get(id);      
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> put(@PathVariable(name = "id") long id, @RequestBody ProductRequest input) {
        return bt.put(id, input);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> post(@RequestBody ProductRequest input) throws BusinessRuleException {
        return bt.post(input);
    }

    @DeleteMapping("/{id}")
    public ProductResponse delete(@PathVariable(name = "id") long id) throws BusinessRuleException {
        return bt.delete(id);
    }

}
