package com.desafioapishop.requests.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductCart {

    private String productId;
    private String quantity;

    public ProductCart(){
        productId = "1";
        quantity = "2";
    }
}
