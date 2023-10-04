package order;

import java.io.Serializable;

import menu.Product;

public class OrderProducts implements Serializable {
    // Attributes
    private Product product;
    private int quantity;
    private double subtotal;
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
    public double getSubtotal(){
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
        StringBuilder sb = new StringBuilder();
        sb.append("Product: ").append(this.product.getName()).append("\n");
        sb.append("  Description: ").append(this.product.getDescription()).append("\n");
        sb.append("  Category: ").append(this.product.getCategory()).append("\n");
        sb.append("  Price: $").append(this.product.getPrice()).append("\n");
        sb.append("  Quantity: ").append(this.quantity).append("\n");
        sb.append("  Subtotal: $").append(this.subtotal).append("\n");
        sb.append("  State: ").append(this.state).append("\n");
        return sb.toString();
    }
}
