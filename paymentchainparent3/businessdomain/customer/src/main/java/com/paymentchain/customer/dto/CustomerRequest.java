/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paymentchain.customer.dto;

import com.paymentchain.customer.entities.CustomerProduct;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;

/**
 *
 * @author ayyoub
 */
@Schema(name = "CustomerRequest", description = "Model represent a customer on database")
@Data
public class CustomerRequest {

    @Schema(name = "code", required = true, example = "01", defaultValue = "0", description = "Unique code of customer")
    private String code;
    @Schema(name = "name", required = true, example = "jhon", defaultValue = "jhon", description = "customer's name")
    private String name;
    @Schema(name = "phone", required = true, example = "+34 656 656 656", defaultValue = "+34 656 656 656", description = "customer's phone number")
    private String phone;
    @Schema(name = "iban", required = true, example = "ES12123412341234", defaultValue = "ES12 1234 1234 1234", description = "customer's iban")
    private String iban;
    @Schema(name = "surname", required = true, example = "doe", defaultValue = "doe", description = "customer's surname")
    private String surname;
    @Schema(name = "address", required = true, example = "C/example", defaultValue = "C/example", description = "customer's address")
    private String address;
    private List<CustomerProduct> products;
    private List<?> transactions;
}
