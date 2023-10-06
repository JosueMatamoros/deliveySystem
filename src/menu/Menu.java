package menu;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The type Menu.
 */
public class Menu implements Serializable {
    // Atributes
    private ArrayList<Product> products = new ArrayList<Product>();
    private ArrayList<Discounts> discounts = new ArrayList<Discounts>();

    /**
     * Instantiates a new Menu.
     */
// Constructor
    public Menu(){

    }

    /**
     * Get products array list.
     *
     * @return the array list
     */
// Getters
    public ArrayList<Product> getProducts(){
        return this.products;
    }

    /**
     * Get discounts array list.
     *
     * @return the array list
     */
    public ArrayList<Discounts> getDiscounts(){
        return this.discounts;
    }

    /**
     * Append product.
     *
     * @param product the product
     */
// Methods
    public void appendProduct(Product product){
        this.products.add(product);
    }

    /**
     * Remove product.
     *
     * @param product the product
     */
    public void removeProduct(Product product){
        this.products.remove(product);
    }

    /**
     * Add discount.
     *
     * @param discount the discount
     */
    public void addDiscount(Discounts discount){
        this.discounts.add(discount);
    }

    /**
     * Remove discount.
     *
     * @param discount the discount
     */
    public void removeDiscount(Discounts discount){
        this.discounts.remove(discount);
    }

    @Override
    public String toString() {
        StringBuilder menuText = new StringBuilder();
        menuText.append("Menu:\n");

        int i = 0;
        for (Product product : products) {
            menuText.append((i + 1) + ". ").append(product.getName()).append(" (").append(product.getCategory()).append("):\n");
            menuText.append("  Description: ").append(product.getDescription()).append("\n");
            menuText.append("  Price: $").append(product.getPrice()).append("\n");
            i++;
        }
        for (Discounts discount : discounts) {
            menuText.append("Discount: ").append(discount.getDiscount());
        }

        return menuText.toString();
    }
}
