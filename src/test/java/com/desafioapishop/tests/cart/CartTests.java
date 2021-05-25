package com.desafioapishop.tests.cart;

import com.desafioapishop.bases.TestBase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@Execution(ExecutionMode.CONCURRENT)
public class CartTests extends TestBase {

    @Test
    public void successfulRegisterCart(){
        //assert product.quantity final = product.quantity initial - quantity
        //assert with regex the creationDate
    }

    @Test
    public void shouldNotRegisterCartForUserWithAlreadyExistingCart(){

    }



    @Test
    public void forbiddenRegisterCart(){

    }

    @Test//parametrized: product = 0, product > product.quantity, product.quantity < 0
    public void shouldNotRegisterCartWithInvalidProductQuantity(){

    }

    @Test
    public void listOneCart(){

    }

    @Test
    public void listCartWithFilters(){

    }

    @Test
    public void forbiddenFinishBuy(){

    }

    @Test
    public void forbiddenCancelBuy(){

    }
}
