package menu;

import java.io.Serializable;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Category: ").append(category).append("\n");
        sb.append("Discount: ").append(discount).append("%\n");
        return sb.toString();
    }
}
