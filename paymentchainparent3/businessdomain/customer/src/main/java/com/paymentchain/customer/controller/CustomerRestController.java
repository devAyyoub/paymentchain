package com.paymentchain.customer.controller;

import com.paymentchain.customer.business.transactions.BusinessTransactionCustomer;
import com.paymentchain.customer.dto.CustomerRequest;
import com.paymentchain.customer.dto.CustomerResponse;
import com.paymentchain.customer.exception.BusinessRuleException;
import com.paymentchain.customer.respository.CustomerRepository;
import java.net.UnknownHostException;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
@RequestMapping("/customer/V1")
public class CustomerRestController {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BusinessTransactionCustomer bt;

    @Autowired
    private Environment env;

    @GetMapping("/check")
    public String check() {
        return "Your property value is: " + env.getProperty("custom.activeprofileName");
    }

    @GetMapping()
    public ResponseEntity<List<CustomerResponse>> list() {
        return bt.list();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> get(@PathVariable(name = "id") long id) {
        return bt.get(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable(name = "id") long id, @RequestBody CustomerRequest input) {
        return bt.put(id, input);
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> post(@RequestBody CustomerRequest input) throws BusinessRuleException, UnknownHostException {
        return bt.post(input);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") long id) {
        return bt.delete(id);
    }

    @GetMapping("/full")
    public CustomerResponse getByCode(@RequestParam(name = "code") String code) throws BusinessRuleException {
        CustomerResponse customer = bt.get(code);
        return customer;
    }

}
