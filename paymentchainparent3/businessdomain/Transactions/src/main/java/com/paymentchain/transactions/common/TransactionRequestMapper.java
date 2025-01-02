/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.paymentchain.transactions.common;

import com.paymentchain.transactions.dto.TransactionRequest;
import com.paymentchain.transactions.entities.Transaction;
import java.util.List;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

/**
 *
 * @author ayyoub
 */
@Mapper(componentModel = "spring") 
public interface TransactionRequestMapper {

    Transaction TransactionRequestToTransaction(TransactionRequest source);

    List<Transaction> TransactionRequestListToTransactionList(List<TransactionRequest> source);
    
    @InheritInverseConfiguration
    TransactionRequest TransactionToTransactionRequest(Transaction source);
    
    @InheritInverseConfiguration
    List<TransactionRequest> TransactionListToTransactionRequestList(List<Transaction> source);

}
