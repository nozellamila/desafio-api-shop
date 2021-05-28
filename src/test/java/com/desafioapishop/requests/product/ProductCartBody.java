package com.desafioapishop.requests.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductCartBody {

    private String productId;
    private String quantity;

    public ProductCartBody(){
        productId = "1";
        quantity = "2";
    }
}
