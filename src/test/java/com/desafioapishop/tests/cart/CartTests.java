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
    public void shouldNotRegisterCartWithNonExistingProduct(){

    }


    @Test//parametrized
    public void shouldNotRegisterCartWithInvalidParameters(){
        //wrong quantity, invalid quantity, empty quantity, null quantity, same for product
    }

    @Test
    public void forbiddenRegisterCart(){

    }

    @Test
    public void listOneCart(){
        //assert with regex of creationDate
    }

    @Test//parametrized
    public void listCartWithFilters(){

    }

    @Test
    public void successfulFinishBuy(){

    }

    @Test
    public void shouldNotFinishBuyForUserWithNoCart(){

    }

    @Test
    public void forbiddenFinishBuy(){

    }

    @Test
    public void successfulCancelBuy(){
        //assert product quantity returns to its original
    }

    @Test
    public void shouldNotCancelBuyForUserWithNoCart(){

    }

    @Test
    public void forbiddenCancelBuy(){

    }
}
