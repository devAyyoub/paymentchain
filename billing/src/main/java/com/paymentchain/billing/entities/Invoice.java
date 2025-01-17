package com.paymentchain.billing.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;



/**
 *
 * @author ayyoub
 */
@Entity
@Data
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long customerId;
    private String number;
    private String detail;
    private double amount; 
}
