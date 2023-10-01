package menu;

import java.util.ArrayList;

public class Menu {
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

}
