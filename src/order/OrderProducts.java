package order;

import java.io.Serializable;

import menu.Product;

public class OrderProducts implements Serializable {
    // Attributes
    private Product product;
    private int quantity;
    private int subtotal;
    private Boolean state;

    // Constructor
    public OrderProducts(Product product, int quantity){
        this.product = product;
        this.quantity = quantity;
        this.subtotal = (int) (product.getPrice() * quantity);
        this.state = false;
    }

    // Getters
    public Product getProduct(){
        return this.product;
    }
    public int getQuantity(){
        return this.quantity;
    }
    public int getSubtotal(){
        return this.subtotal;
    }
    public Boolean getState(){
        return this.state;
    }
    public void setState(Boolean state){
        this.state = state;
    }
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Selected Product: " + product.getName() +
               "\nDescription: " + product.getDescription() +
               "\nPrice: $" + product.getPrice() +
               "\nCategory: " + product.getCategory() +
               "\nQuantity: " + quantity +
               "\nSubtotal: $" + (product.getPrice() * quantity) +
               "\nState: " + (state ? "true" : "false") +
               "\n";
    }
}
