package com.paymentchain.billing.controller;


import com.paymentchain.billing.common.InvoiceRequestMapper;
import com.paymentchain.billing.common.InvoiceResponseMapper;
import com.paymentchain.billing.dto.InvoiceRequest;
import com.paymentchain.billing.dto.InvoiceResponse;
import com.paymentchain.billing.entities.Invoice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import com.paymentchain.billing.respository.InvoiceRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


import java.util.Optional;

/**
 *
 * @author ayyoub
 */
@Tag(name = "Billing API", description = "This APi serve all functionality for management Invoices")
@RestController
@RequestMapping("/billing")
public class InvoiceRestController {
    
    @Autowired
    InvoiceRepository billingRepository;
    
    @Autowired
    InvoiceRequestMapper irm;
    
    @Autowired
    InvoiceResponseMapper irsm;
    
    @Operation(description = "Return all invoices bundled into Response", summary ="Return 204 if no data found")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",description = "Success"),
    @ApiResponse(responseCode = "500", description = "Internal error")})
    
    @GetMapping()
    public List<InvoiceResponse> list() {
        List<Invoice> findAll = billingRepository.findAll();
         return irsm.InvoiceListToInvoiceResponseList(findAll);       
    } 
    
    @GetMapping("/{id}")
    public Invoice get(@PathVariable String id) {
        Optional<Invoice> findById = billingRepository.findById(Long.valueOf(id));
        return findById.get();
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable String id, @RequestBody InvoiceRequest input) {
       Invoice save = null; 
        Optional<Invoice> findById = billingRepository.findById(Long.valueOf(id));
        Invoice get = findById.get();
        if(get != null){   
               get.setAmount(input.getAmount());
               get.setDetail(input.getDetail());
               get.setCustomerId(input.getCustomer());
               get.setNumber(input.getNumber());
                    save = billingRepository.save(get);                      
        }
          InvoiceResponse InvoiceToInvoiceRespose = irsm.InvoiceToInvoiceResponse(save);
        return ResponseEntity.ok(InvoiceToInvoiceRespose);
    }
    
    @PostMapping
    public ResponseEntity<?> post(@RequestBody InvoiceRequest input) { 
        Invoice InvoiceRequestToInvoice = irm.InvoiceRequestToInvoice(input); 
        Invoice save = billingRepository.save(InvoiceRequestToInvoice);
        InvoiceResponse InvoiceToInvoiceResponse = irsm.InvoiceToInvoiceResponse(save);
        return ResponseEntity.ok(InvoiceToInvoiceResponse );
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        //Invoice save = null; 
        Optional<Invoice> findById = billingRepository.findById(Long.valueOf(id));
        Invoice get = findById.get();
        if(get != null){               
                  billingRepository.delete(get);  
        }
        return ResponseEntity.ok().build();
    }
}
