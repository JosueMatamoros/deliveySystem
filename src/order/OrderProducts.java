package order;

import java.io.Serializable;

import menu.Product;

/**
 * The type Order products.
 */
public class OrderProducts implements Serializable {
    // Attributes
    private Product product;
    private int quantity;
    private double subtotal;
    private Boolean state;

    /**
     * Instantiates a new Order products.
     *
     * @param product  the product
     * @param quantity the quantity
     */
// Constructor
    public OrderProducts(Product product, int quantity){
        this.product = product;
        this.quantity = quantity;
        this.subtotal = (int) (product.getPrice() * quantity);
        this.state = false;
    }

    /**
     * Get product product.
     *
     * @return the product
     */
// Getters
    public Product getProduct(){
        return this.product;
    }

    /**
     * Get quantity int.
     *
     * @return the int
     */
    public int getQuantity(){
        return this.quantity;
    }

    /**
     * Get subtotal double.
     *
     * @return the double
     */
    public double getSubtotal(){
        return this.subtotal;
    }

    /**
     * Get state boolean.
     *
     * @return the boolean
     */
    public Boolean getState(){
        return this.state;
    }

    /**
     * Set state.
     *
     * @param state the state
     */
    public void setState(Boolean state){
        this.state = state;
    }

    /**
     * Set quantity.
     *
     * @param quantity the quantity
     */
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