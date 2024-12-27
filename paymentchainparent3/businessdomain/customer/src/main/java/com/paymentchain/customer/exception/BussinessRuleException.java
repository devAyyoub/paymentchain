/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paymentchain.customer.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

/**
 *
 * @author ayyoub
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class BussinessRuleException extends Exception{
    private long id;
    private String code;
    private HttpStatus httpStatus;
    
     public BussinessRuleException(long id, String code, String message,HttpStatus httpStatus) {
        super(message);
        this.id = id;
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public BussinessRuleException(String code, String message,HttpStatus httpStatus) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }
    
    public BussinessRuleException(String message, Throwable cause) {
        super(message, cause);
    }  
}
