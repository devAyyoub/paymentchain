
package com.paymentchain.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 *
 * @author ayyoub
 */
@Schema(name = "ProductResponse", description = "Model represent a product on database")
@Data
public class ProductResponse {

    @Schema(name = "productId", required = true, example = "3", defaultValue = "8", description = "Unique Id of product")
    private long productId;
    
    @Schema(name = "code", required = true, example = "3", defaultValue = "8", description = "Product's code")
    private String code;
    
    @Schema(name = "name", required = true, example = "product", defaultValue = "product", description = "Product's name")
    private String name;

}
