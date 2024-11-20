/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package com.paymentchain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.paymentchain.product.entities.Product;

/**
 *
 * @author ayyoub
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
    
}
