package menu;

import java.io.Serializable;


/**
 * The type Product.
 */
public class Product implements Serializable {
    // Attributes
    private String name;
    private String description;
    private double price;
    private String category;
    private double preparationTime;
    private int quantityPerDay;

    /**
     * Instantiates a new Product.
     *
     * @param name            the name
     * @param description     the description
     * @param price           the price
     * @param category        the category
     * @param preparationTime the preparation time
     */
// Constructor
    public Product(String name, String description, double price, String category, double preparationTime){
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.preparationTime = preparationTime;
    }

    /**
     * Get name string.
     *
     * @return the string
     */
// Getters
    public String getName(){
        return this.name;
    }

    /**
     * Get description string.
     *
     * @return the string
     */
    public String getDescription(){
        return this.description;
    }

    /**
     * Get price double.
     *
     * @return the double
     */
    public double getPrice(){
        return this.price;
    }

    /**
     * Get category string.
     *
     * @return the string
     */
    public String getCategory(){
        return this.category;
    }

    /**
     * Get quantity per day int.
     *
     * @return the int
     */
    public int getQuantityPerDay(){
        return this.quantityPerDay;
    }

    /**
     * Get preparation time double.
     *
     * @return the double
     */
    public double getPreparationTime(){
        return this.preparationTime;
    }

    /**
     * Set name.
     *
     * @param name the name
     */
// Setters
    public void setName(String name){
        this.name = name;
    }

    /**
     * Set description.
     *
     * @param description the description
     */
    public void setDescription(String description){
        this.description = description;
    }

    /**
     * Set price.
     *
     * @param price the price
     */
    public void setPrice(double price){
        this.price = price;
    }

    /**
     * Set quantity per day.
     *
     * @param quantityPerDay the quantity per day
     */
    public void setQuantityPerDay(int quantityPerDay){
        this.quantityPerDay += quantityPerDay;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Product Information:\n");
        sb.append("  Name: ").append(name).append("\n");
        sb.append("  Description: ").append(description).append("\n");
        sb.append("  Price: $").append(price).append("\n");
        sb.append("  Category: ").append(category).append("\n");
        sb.append("  Preparation Time: ").append(preparationTime).append(" minutes\n");
        return sb.toString();
    }

}