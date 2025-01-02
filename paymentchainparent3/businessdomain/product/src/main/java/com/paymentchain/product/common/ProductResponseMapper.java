/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.paymentchain.product.common;

import com.paymentchain.product.dto.ProductResponse;
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
public interface ProductResponseMapper {

    @Mappings({
        @Mapping(source = "id", target = "productId")})
    ProductResponse ProductToProductResponse(Product source);

    List<ProductResponse> ProductListToProductResponseList(List<Product> source);

    @InheritInverseConfiguration
    Product ProductResponseToProduct(ProductResponse srr);

    @InheritInverseConfiguration
    List<Product> ProductResponseToProductList(List<ProductResponse > source);
}
