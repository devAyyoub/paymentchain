package com.paymentchain.product.common;

import com.paymentchain.product.dto.ProductRequest;
import com.paymentchain.product.entities.Product;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-15T20:26:08+0100",
    comments = "version: 1.5.3.Final, compiler: Eclipse JDT (IDE) 3.41.0.v20241217-1506, environment: Java 17.0.13 (Eclipse Adoptium)"
)
@Component
public class ProductRequestMapperImpl implements ProductRequestMapper {

    @Override
    public Product ProductRequestToProduct(ProductRequest source) {
        if ( source == null ) {
            return null;
        }

        Product product = new Product();

        product.setCode( source.getCode() );
        product.setName( source.getName() );

        return product;
    }

    @Override
    public List<Product> ProductRequestListToProductList(List<ProductRequest> source) {
        if ( source == null ) {
            return null;
        }

        List<Product> list = new ArrayList<Product>( source.size() );
        for ( ProductRequest productRequest : source ) {
            list.add( ProductRequestToProduct( productRequest ) );
        }

        return list;
    }

    @Override
    public ProductRequest ProductToProductRequest(Product source) {
        if ( source == null ) {
            return null;
        }

        ProductRequest productRequest = new ProductRequest();

        productRequest.setCode( source.getCode() );
        productRequest.setName( source.getName() );

        return productRequest;
    }

    @Override
    public List<ProductRequest> ProductListToProductRequestList(List<Product> source) {
        if ( source == null ) {
            return null;
        }

        List<ProductRequest> list = new ArrayList<ProductRequest>( source.size() );
        for ( Product product : source ) {
            list.add( ProductToProductRequest( product ) );
        }

        return list;
    }
}
