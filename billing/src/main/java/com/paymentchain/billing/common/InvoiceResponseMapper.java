package com.paymentchain.billing.common;

import com.paymentchain.billing.dto.InvoiceResponse;
import com.paymentchain.billing.entities.Invoice;
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
public interface InvoiceResponseMapper {

    @Mappings({
        @Mapping(source = "customerId", target = "customer"),
        @Mapping(source = "id", target = "invoiceId")})
    InvoiceResponse InvoiceToInvoiceResponse(Invoice source);

    List<InvoiceResponse> InvoiceListToInvoiceResponseList(List<Invoice> source);

    @InheritInverseConfiguration
    Invoice InvoiceResponseToInvoice(InvoiceResponse srr);

    @InheritInverseConfiguration
    List<Invoice> InvoiceResponseToInvoiceList(List<InvoiceResponse > source);
}
