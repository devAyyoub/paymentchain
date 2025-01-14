
package com.paymentchain.customer.exception;

import com.paymentchain.customer.common.StandarizedApiExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *
 * @author ayyoub
 */
@RestControllerAdvice
public class ApiExceptionHandler {
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUnknownException(Exception ex) {
        StandarizedApiExceptionResponse standarizedApiExceptionResponse = new StandarizedApiExceptionResponse("TECNICO", "Input Ouput error", "1024", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(standarizedApiExceptionResponse);
    }
    
    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<?> handleBussinesException(BusinessRuleException ex) {
        StandarizedApiExceptionResponse standarizedApiExceptionResponse = new StandarizedApiExceptionResponse("BUSSINES", "Error de validación", ex.getCode(), ex.getMessage());
        return ResponseEntity.status(ex.getHttpStatus()).body(standarizedApiExceptionResponse);
    }
}
