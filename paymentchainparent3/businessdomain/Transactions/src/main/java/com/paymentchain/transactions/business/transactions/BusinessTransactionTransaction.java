/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paymentchain.transactions.business.transactions;

import com.paymentchain.transactions.common.TransactionRequestMapper;
import com.paymentchain.transactions.common.TransactionResponseMapper;
import com.paymentchain.transactions.dto.TransactionRequest;
import com.paymentchain.transactions.dto.TransactionResponse;
import com.paymentchain.transactions.entities.Transaction;
import com.paymentchain.transactions.exception.BusinessRuleException;
import com.paymentchain.transactions.respository.TransactionRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author ayyoub
 */
@Service
public class BusinessTransactionTransaction {

    @Autowired
    TransactionRepository transactionRepository;
    
    @Autowired
    TransactionRequestMapper trm;
    
    @Autowired
    TransactionResponseMapper trsm;

    @GetMapping()
    public List<Transaction> list() {
        return transactionRepository.findAll();
    }

    public TransactionResponse get(long id) throws BusinessRuleException {
        //return transactionRepository.findById(id).map(x -> ResponseEntity.ok(x)).orElse(ResponseEntity.notFound().build());
        Optional<Transaction> findById = transactionRepository.findById(id);
        if(findById.isPresent()){
            TransactionResponse response = trsm.TransactionToTransactionResponse(findById.get());
            return response;
        } else {
            throw new BusinessRuleException("1025", "Error validacion, transacción con id " + id + " no existe", HttpStatus.PRECONDITION_FAILED);
        }
    }

    public List<TransactionResponse> get( String ibanAccount) throws BusinessRuleException {
        List<Transaction> findByIbanAccount = transactionRepository.findByIbanAccount(ibanAccount);
        if (findByIbanAccount.isEmpty()) {
            throw new BusinessRuleException("1023", "Iban y canal deben ser rellenados", HttpStatus.BAD_REQUEST);
        } else {
            List<TransactionResponse> response = trsm.TransactionListToTransactionResponseList(findByIbanAccount);
            return response;
        }
    }

    public ResponseEntity<TransactionResponse> put(long id,  TransactionRequest input) {
        Transaction transactionReqToTransation = trm.TransactionRequestToTransaction(input);
        Transaction find = transactionRepository.findById(id).get();
        if (find != null) {
            find.setAmount(transactionReqToTransation.getAmount());
            find.setChannel(transactionReqToTransation.getChannel());
            find.setDate(transactionReqToTransation.getDate());
            find.setDescription(transactionReqToTransation.getDescription());
            find.setFee(transactionReqToTransation.getFee());
            find.setIbanAccount(transactionReqToTransation.getIbanAccount());
            find.setReference(transactionReqToTransation.getReference());
            find.setStatus(transactionReqToTransation.getStatus());
        }
        Transaction save = transactionRepository.save(find);
        TransactionResponse transactionToTransactionResp = trsm.TransactionToTransactionResponse(save);
        return ResponseEntity.ok(transactionToTransactionResp);
    }

    public ResponseEntity<TransactionResponse> post(TransactionRequest inputRequest) throws BusinessRuleException {
        Transaction input = trm.TransactionRequestToTransaction(inputRequest);
        if (input.getIbanAccount() == null || input.getIbanAccount().isBlank()
                || input.getChannel() == null || input.getChannel().isBlank()) {
            throw new BusinessRuleException("1023", "Iban y canal deben ser rellenados", HttpStatus.BAD_REQUEST);
        }

        // Obtener todas las transacciones previas asociadas al IBAN
        List<Transaction> previousTransactions = transactionRepository.findByIbanAccount(input.getIbanAccount());

        // Calcular el saldo acumulado actual
        double currentBalance = previousTransactions.stream()
                .mapToDouble(t -> {
                    if (t.getDescription().equalsIgnoreCase("ABONO")) {
                        return Math.abs(t.getAmount()); // Asegurarse de que los abonos sean positivos
                    } else if (t.getDescription().equalsIgnoreCase("RETIRO")) {
                        return -Math.abs(t.getAmount()); // Asegurarse de que los retiros sean negativos
                    }
                    return 0; // Ignorar cualquier transacción inválida
                })
                .sum();

        double fee = 0.0;
        double transactionImpact;

        if (input.getAmount() < 0) {
            // Transacción es un retiro, calcular comisión
            fee = Math.abs(input.getAmount()) * 0.0098; // 0.98% de comisión
            transactionImpact = input.getAmount() - fee; // Retiro más la comisión

            // Validar que el saldo no quede negativo
            if (currentBalance + transactionImpact < 0) {
                throw new BusinessRuleException("1024", "Saldo insuficiente para realizar el retiro", HttpStatus.BAD_REQUEST);
            }

            input.setDescription("RETIRO");
            input.setFee(fee);
            input.setAmount(transactionImpact); // Guardar el monto ajustado
        } else {
            // Transacción es un abono
            transactionImpact = input.getAmount();
            input.setDescription("ABONO");
        }

        // Verificar si la transacción genera un saldo negativo
        double newBalance = currentBalance + transactionImpact;

        // Actualizar el balance después de la transacción
        input.setBalanceAfterTransaction(newBalance);

        // Guardar la transacción
        Transaction savedTransaction = transactionRepository.save(input);
        TransactionResponse transactionToTransactionResponse = trsm.TransactionToTransactionResponse(savedTransaction);
        return ResponseEntity.ok(transactionToTransactionResponse);
    }

    public ResponseEntity<?> delete(long id) throws BusinessRuleException {
        Optional<Transaction> findById = transactionRepository.findById(id);
        if (findById.isPresent()) {
            transactionRepository.delete(findById.get());
        } else {
            throw new BusinessRuleException("1025", "Error validacion, transacción con id " + id + " no existe", HttpStatus.PRECONDITION_FAILED);
        }
        return ResponseEntity.ok().build();
    }
}
