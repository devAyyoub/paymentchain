/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paymentchain.product.business.transactions;

import com.paymentchain.product.common.ProductRequestMapper;
import com.paymentchain.product.common.ProductResponseMapper;
import com.paymentchain.product.dto.ProductRequest;
import com.paymentchain.product.dto.ProductResponse;
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
    
    @Autowired
    ProductRequestMapper prm;
    
    @Autowired
    ProductResponseMapper prsm;

    public ResponseEntity<ProductResponse> get(long id) throws BusinessRuleException {
        Optional<Product> findById = productRepository.findById(id);
        if (findById.isPresent()) {
            ProductResponse response = prsm.ProductToProductResponse(findById.get());
            return ResponseEntity.ok(response);
        } else {
            BusinessRuleException businessRuleException = new BusinessRuleException("1024", "Error de validación, producto con id " + id + " no existe", HttpStatus.NOT_FOUND);
            throw businessRuleException;
        }
    }

    public List<ProductResponse> list() throws BusinessRuleException {
        List<Product> findAll = productRepository.findAll();
        if (findAll.isEmpty()) {
            BusinessRuleException businessRuleException = new BusinessRuleException("1023", "No hay productos registrados", HttpStatus.NOT_FOUND);
            throw businessRuleException;
        } else {
            List<ProductResponse> response = prsm.ProductListToProductResponseList(findAll);
            return response;
        }
    }

    public ResponseEntity<ProductResponse> put(long id, @RequestBody ProductRequest input) {
        Product ProductRequestToProduct = prm.ProductRequestToProduct(input);
        Product find = productRepository.findById(id).get();
        if (find != null) {
            find.setCode(input.getCode());
            find.setName(input.getName());
        }
        Product save = productRepository.save(find);
        ProductResponse ProductToProductResponse = prsm.ProductToProductResponse(save);
        return ResponseEntity.ok(ProductToProductResponse);
    }

    public ResponseEntity<ProductResponse> post(@RequestBody ProductRequest input) throws BusinessRuleException {
        if (input.getCode() == null || input.getCode().isBlank() || input.getName() == null || input.getName().isBlank()) {
            BusinessRuleException businessRuleException = new BusinessRuleException("1023", "Codigo y nombre deben ser rellenados", HttpStatus.BAD_REQUEST);
            throw businessRuleException;
        } else {
            Product productRequestToProduct = prm.ProductRequestToProduct(input);
            Product save = productRepository.save(productRequestToProduct);
            ProductResponse productToProductResponse = prsm.ProductToProductResponse(save);
            return ResponseEntity.ok(productToProductResponse);
        }

    }

    public ProductResponse delete(@PathVariable(name = "id") long id) throws BusinessRuleException {
        Optional<Product> findById = productRepository.findById(id);
        if (findById.isPresent()) {
            productRepository.delete(findById.get());
            ProductResponse productToProductResponse = prsm.ProductToProductResponse(findById.get());
            return productToProductResponse;
        } else {
            BusinessRuleException businessRuleException = new BusinessRuleException("1024", "Error de validación, producto con id " + id + " no existe", HttpStatus.NOT_FOUND);
            throw businessRuleException;
        }
    }
}
