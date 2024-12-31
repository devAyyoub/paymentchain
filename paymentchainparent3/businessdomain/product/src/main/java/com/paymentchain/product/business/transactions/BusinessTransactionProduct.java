/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paymentchain.product.business.transactions;

import com.paymentchain.product.entities.Product;
import com.paymentchain.product.exception.BusinessRuleException;
import com.paymentchain.product.respository.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author ayyoub
 */
@Service
public class BusinessTransactionProduct {

    @Autowired
    ProductRepository productRepository;

    public Optional<Product> get(long id) throws BusinessRuleException {
        Optional<Product> findById = productRepository.findById(id);
        if (findById.isPresent()) {
            return findById;
        } else {
            BusinessRuleException businessRuleException = new BusinessRuleException("1024", "Error de validación, producto con id " + id + " no existe", HttpStatus.NOT_FOUND);
            throw businessRuleException;
        }
    }

    public List<Product> list() throws BusinessRuleException {
        List<Product> findAll = productRepository.findAll();
        if (findAll.isEmpty()) {
            BusinessRuleException businessRuleException = new BusinessRuleException("1023", "No hay productos registrados", HttpStatus.NOT_FOUND);
            throw businessRuleException;
        } else {
            return findAll;
        }
    }

    public ResponseEntity<?> put(long id, @RequestBody Product input) {
        Product find = productRepository.findById(id).get();
        if (find != null) {
            find.setCode(input.getCode());
            find.setName(input.getName());
        }
        Product save = productRepository.save(find);
        return ResponseEntity.ok(save);
    }

    public ResponseEntity<?> post(@RequestBody Product input) throws BusinessRuleException {
        if (input.getCode() == null || input.getCode().isBlank() || input.getName() == null || input.getName().isBlank()) {
            BusinessRuleException businessRuleException = new BusinessRuleException("1023", "Codigo y nombre deben ser rellenados", HttpStatus.BAD_REQUEST);
            throw businessRuleException;
        } else {
            Product save = productRepository.save(input);
            return ResponseEntity.ok(save);
        }

    }

    public Optional<Product> delete(@PathVariable(name = "id") long id) throws BusinessRuleException {
        Optional<Product> findById = productRepository.findById(id);
        if (findById.isPresent()) {
            productRepository.delete(findById.get());
            return findById;
        } else {
            BusinessRuleException businessRuleException = new BusinessRuleException("1024", "Error de validación, producto con id " + id + " no existe", HttpStatus.NOT_FOUND);
            throw businessRuleException;
        }
    }
}
