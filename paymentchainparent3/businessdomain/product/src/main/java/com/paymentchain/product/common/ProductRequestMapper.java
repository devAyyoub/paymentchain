
package com.paymentchain.product.common;

import com.paymentchain.product.dto.ProductRequest;
import com.paymentchain.product.entities.Product;
import java.util.List;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

/**
 *
 * @author ayyoub
 */
@Mapper(componentModel = "spring") 
public interface ProductRequestMapper {

    Product ProductRequestToProduct(ProductRequest source);

    List<Product> ProductRequestListToProductList(List<ProductRequest> source);
    
    @InheritInverseConfiguration
    ProductRequest ProductToProductRequest(Product source);
    
    @InheritInverseConfiguration
    List<ProductRequest> ProductListToProductRequestList(List<Product> source);

}
