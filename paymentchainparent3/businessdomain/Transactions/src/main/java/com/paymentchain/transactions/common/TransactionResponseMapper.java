/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.paymentchain.transactions.common;

import com.paymentchain.transactions.dto.TransactionResponse;
import com.paymentchain.transactions.entities.Transaction;
import java.util.List;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 *
 * @author ayyoub
 */
@Mapper(componentModel = "spring")
public interface TransactionResponseMapper {

    @Mappings({
        @Mapping(source = "id", target = "transactionId")})
    TransactionResponse TransactionToTransactionResponse(Transaction source);

    List<TransactionResponse> TransactionListToTransactionResponseList(List<Transaction> source);

    @InheritInverseConfiguration
    Transaction TransactionResponseToTransaction(TransactionResponse srr);

    @InheritInverseConfiguration
    List<Transaction> TransactionResponseToTransactionList(List<TransactionResponse > source);
}
