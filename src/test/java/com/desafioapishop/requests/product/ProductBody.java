package com.desafioapishop.requests.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductBody {

    private String name;
    private Float price;
    private String description;
    private Integer quantity;
}
