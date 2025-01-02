/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.paymentchain.product.common;

import com.paymentchain.product.dto.ProductRequest;
import com.paymentchain.product.entities.Product;
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
public interface ProductRequestMapper {

    Product InvoiceRequestToInvoice(ProductRequest source);

    List<Product> InvoiceRequestListToInvoiceList(List<ProductRequest> source);
    
    @InheritInverseConfiguration
    ProductRequest InvoiceToInvoiceRequest(Product source);
    
    @InheritInverseConfiguration
    List<ProductRequest> InvoiceListToInvoiceRequestList(List<Product> source);

}
