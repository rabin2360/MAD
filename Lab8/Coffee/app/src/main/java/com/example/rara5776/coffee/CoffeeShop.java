package com.example.rara5776.coffee;

/**
 * Created by rara5776 on 11/10/16.
 */
public class CoffeeShop {

    private String coffeShop;
    private String coffeeShopURL;

    private  void setCoffeShopInfo(Integer coffeeCrowd)
    {
        switch(coffeeCrowd)
        {
            case 0:
                coffeShop = "Ozo";
                coffeeShopURL = "https://www.ozocoffee.com";
                break;

            case 1:
                coffeShop = "Starbucks";
                coffeeShopURL = "https://www.starbucks.com";
                break;

            default:
                coffeShop = "Pekoe";
                coffeeShopURL = "https://www.pekoeiphouse.com";

        }
    }

    //one more thing
    public void setCoffeeShop(Integer coffeeCrowd)
    {
        setCoffeShopInfo(coffeeCrowd);
    }

    public void setCoffeeShopURL(Integer coffeeCrowd)
    {
        setCoffeShopInfo(coffeeCrowd);
    }

    public String getCoffeShop()
    {
        return coffeShop;
    }

    public String getCoffeeShopURL()
    {
        return coffeeShopURL;
    }



}
