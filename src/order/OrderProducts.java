package order;

import menu.Product;

public class OrderProducts {
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
}
