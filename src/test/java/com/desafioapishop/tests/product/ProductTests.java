package com.desafioapishop.tests.product;

import com.desafioapishop.bases.TestBase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@Execution(ExecutionMode.CONCURRENT)
public class ProductTests extends TestBase {

    @Test
    public void successfulRegisterProduct(){

    }

    @Test
    public void shouldNotRegisterDuplicatedName(){

    }

    @Test
    public void invalidParametersRegisterProduct(){

    }

    @Test
    public void shouldNotRegisterProductForNonAdminUser(){

    }

    @Test
    public void forbiddenRegisterProduct(){

    }

    @Test
    public void successfulUpdateProduct(){

    }

    @Test
    public void shouldNotUpdateWithDuplicatedName(){

    }

    @Test
    public void invalidParametersUpdateProduct(){

    }

    @Test
    public void shouldNotUpdateProductForNonAdminUser(){

    }

    @Test
    public void forbiddenUpdateProduct(){

    }

    @Test
    public void listOneProduct(){
        //assert with regex of creationDate
    }

    @Test//parametrized
    public void listProductWithFilters(){

    }

    @Test//parametrized
    public void shouldNotFindProductToList(){

    }

    @Test
    public void forbiddenListProduct(){

    }

    @Test
    public void excludeProduct(){

    }

    @Test
    public void shouldNotExcludeNonExistentProduct(){

    }

    @Test
    public void shouldNotExcludeProductForNonAdminUser(){

    }

    @Test
    public void shouldNotExcludeProductBelongingToACart(){

    }

    @Test
    public void forbiddenExcludeProduct(){

    }
}
