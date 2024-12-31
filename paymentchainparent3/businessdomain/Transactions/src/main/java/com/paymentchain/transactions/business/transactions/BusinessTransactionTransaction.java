/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paymentchain.transactions.business.transactions;

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

    @GetMapping()
    public List<Transaction> list() {
        return transactionRepository.findAll();
    }

    public Optional<Transaction> get(long id) throws BusinessRuleException {
        //return transactionRepository.findById(id).map(x -> ResponseEntity.ok(x)).orElse(ResponseEntity.notFound().build());
        Optional<Transaction> findById = transactionRepository.findById(id);
        if(findById.isPresent()){
            return findById;
        } else {
            throw new BusinessRuleException("1025", "Error validacion, transacción con id " + id + " no existe", HttpStatus.PRECONDITION_FAILED);
        }
    }

    public List<Transaction> get( String ibanAccount) throws BusinessRuleException {
        List<Transaction> findByIbanAccount = transactionRepository.findByIbanAccount(ibanAccount);
        if (findByIbanAccount.isEmpty()) {
            throw new BusinessRuleException("1023", "Iban y canal deben ser rellenados", HttpStatus.BAD_REQUEST);
        } else {
            return findByIbanAccount;
        }
    }

    public ResponseEntity<?> put(long id,  Transaction input) {
        Transaction find = transactionRepository.findById(id).get();
        if (find != null) {
            find.setAmount(input.getAmount());
            find.setChannel(input.getChannel());
            find.setDate(input.getDate());
            find.setDescription(input.getDescription());
            find.setFee(input.getFee());
            find.setIbanAccount(input.getIbanAccount());
            find.setReference(input.getReference());
            find.setStatus(input.getStatus());
        }
        Transaction save = transactionRepository.save(find);
        return ResponseEntity.ok(save);
    }

    public ResponseEntity<?> post(Transaction input) throws BusinessRuleException {
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
        return ResponseEntity.ok(savedTransaction);
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
