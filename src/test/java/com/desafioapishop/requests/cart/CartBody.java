package com.desafioapishop.requests.cart;

import com.desafioapishop.requests.product.ProductCartBody;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CartBody {

    private List<ProductCartBody> products = new ArrayList<>();
    private String userId;

    public CartBody(List<ProductCartBody> products, String userId){
        this.products = products;
        this.userId = userId;
    }
}
