package menu;

import java.io.Serializable;
import java.util.Date;

public class Discounts implements Serializable {
    // Attributes
    private String category;
    private int discount;

    // Constructor
    public Discounts(String category, int discount){
        this.category = category;
        this.discount = discount;
    }
    // Getters
    public String getCategory(){
        return this.category;
    }
    public int getDiscount(){
        return this.discount;
    }
}
