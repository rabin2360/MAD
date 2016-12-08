package com.example.rara5776.finalexam;

/**
 * Created by rara5776 on 12/8/16.
 */
public class IceCreamShops {

    private String iceCreamShop;

    public IceCreamShops()
    {
        iceCreamShop = "";
    }

    public String getShop(String iceCream)
    {
        switch (iceCream)
        {
            case "Death by Chocolate":
                return "Glacier";

            case "Salted Caramel":
                return "Fior di Latte";

            case "Cookies and Cream":
                return "Sweet Cow";

            default:
                return "";
        }
    }
}
