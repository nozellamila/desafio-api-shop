package com.desafioapishop.requests.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ProductCartBody {

    private String productId;
    private String quantity;

    public ProductCartBody(){
        productId = "1";
        quantity = "2";
    }

    public ProductCartBody(String productId, String quantity){
        this.productId = productId;
        this.quantity = quantity;
    }
}
