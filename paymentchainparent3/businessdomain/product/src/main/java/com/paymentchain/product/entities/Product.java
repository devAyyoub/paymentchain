
package com.paymentchain.product.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

/**
 *
 * @author ayyoub
 */
@Entity
@Data
public class Product {
   @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
   private long id;
   private String code;
   private String name;
   
}
