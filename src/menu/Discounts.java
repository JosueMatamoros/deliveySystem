package menu;

import java.io.Serializable;

/**
 * The type Discounts.
 */
public class Discounts implements Serializable {
    // Attributes
    private String category;
    private int discount;

    /**
     * Instantiates a new Discounts.
     *
     * @param category the category
     * @param discount the discount
     */
// Constructor
    public Discounts(String category, int discount){
        this.category = category;
        this.discount = discount;
    }

    /**
     * Get category string.
     *
     * @return the string
     */
// Getters
    public String getCategory(){
        return this.category;
    }

    /**
     * Get discount int.
     *
     * @return the int
     */
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