package com.paymentchain.product.common;

import com.paymentchain.product.dto.ProductResponse;
import com.paymentchain.product.entities.Product;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-12T21:26:12+0100",
    comments = "version: 1.5.3.Final, compiler: Eclipse JDT (IDE) 3.41.0.z20250115-2156, environment: Java 21.0.5 (Eclipse Adoptium)"
)
@Component
public class ProductResponseMapperImpl implements ProductResponseMapper {

    @Override
    public ProductResponse ProductToProductResponse(Product source) {
        if ( source == null ) {
            return null;
        }

        ProductResponse productResponse = new ProductResponse();

        productResponse.setProductId( source.getId() );
        productResponse.setCode( source.getCode() );
        productResponse.setName( source.getName() );

        return productResponse;
    }

    @Override
    public List<ProductResponse> ProductListToProductResponseList(List<Product> source) {
        if ( source == null ) {
            return null;
        }

        List<ProductResponse> list = new ArrayList<ProductResponse>( source.size() );
        for ( Product product : source ) {
            list.add( ProductToProductResponse( product ) );
        }

        return list;
    }

    @Override
    public Product ProductResponseToProduct(ProductResponse srr) {
        if ( srr == null ) {
            return null;
        }

        Product product = new Product();

        product.setId( srr.getProductId() );
        product.setCode( srr.getCode() );
        product.setName( srr.getName() );

        return product;
    }

    @Override
    public List<Product> ProductResponseToProductList(List<ProductResponse> source) {
        if ( source == null ) {
            return null;
        }

        List<Product> list = new ArrayList<Product>( source.size() );
        for ( ProductResponse productResponse : source ) {
            list.add( ProductResponseToProduct( productResponse ) );
        }

        return list;
    }
}
