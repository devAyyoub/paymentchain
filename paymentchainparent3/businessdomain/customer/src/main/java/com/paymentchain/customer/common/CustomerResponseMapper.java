
package com.paymentchain.customer.common;

import com.paymentchain.customer.dto.CustomerResponse;
import com.paymentchain.customer.entities.Customer;
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
public interface CustomerResponseMapper {

    @Mappings({
        @Mapping(source = "id", target = "customerId")})
    CustomerResponse CustomerToCustomerResponse(Customer source);

    List<CustomerResponse> CustomerListToCustomerResponseList(List<Customer> source);

    @InheritInverseConfiguration
    Customer CustomerResponseToCustomer(CustomerResponse srr);

    @InheritInverseConfiguration
    List<Customer> CustomerResponseToCustomerList(List<CustomerResponse > source);
}
