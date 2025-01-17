package com.paymentchain.customer.common;

import com.paymentchain.customer.dto.CustomerRequest;
import com.paymentchain.customer.entities.Customer;
import java.util.List;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

/**
 *
 * @author ayyoub
 */
@Mapper(componentModel = "spring") 
public interface CustomerRequestMapper {
      
    Customer CustomerRequestToCustomer(CustomerRequest source);

    List<Customer> CustomerRequestListToCustomerList(List<CustomerRequest> source);
    
    @InheritInverseConfiguration
    CustomerRequest CustomerToCustomerRequest(Customer source);
    
    @InheritInverseConfiguration
    List<CustomerRequest> CustomerListToCustomerRequestList(List<Customer> source);

}
