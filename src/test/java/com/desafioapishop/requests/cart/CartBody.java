package com.desafioapishop.requests.cart;

import com.desafioapishop.requests.product.ProductCart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartBody {

    private List<ProductCart> products = new ArrayList<>();
    private String userId;
}
