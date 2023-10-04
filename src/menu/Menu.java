package menu;

import java.io.Serializable;
import java.util.ArrayList;

public class Menu implements Serializable {
    // Atributes
    private ArrayList<Product> products = new ArrayList<Product>();
    private ArrayList<Discounts> discounts = new ArrayList<Discounts>();

    // Constructor
    public Menu(){

    }

    // Getters
    public ArrayList<Product> getProducts(){
        return this.products;
    }
    public ArrayList<Discounts> getDiscounts(){
        return this.discounts;
    }

    // Methods
    public void appendProduct(Product product){
        this.products.add(product);
    }
    public void removeProduct(Product product){
        this.products.remove(product);
    }
    public void addDiscount(Discounts discount){
        this.discounts.add(discount);
    }
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
