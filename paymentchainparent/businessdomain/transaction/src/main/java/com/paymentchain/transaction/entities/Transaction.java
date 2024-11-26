/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paymentchain.transaction.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Data;

/**
 *
 * @author ayyoub
 */

@Data
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String reference;
    private String ibanAccount;
    private LocalDateTime date;
    private double amount;
    private double fee;
    private String status;
    private String channel;   
    private String description;
}
