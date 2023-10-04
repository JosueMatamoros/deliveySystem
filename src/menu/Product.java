package menu;

import java.io.Serializable;

import javax.swing.*;

public class Product implements Serializable {
    // Attributes
    private String name;
    private String description;
    private double price;
    private String category;
    private double preparationTime;

    // Constructor
    public Product(String name, String description, double price, String category, double preparationTime){
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.preparationTime = preparationTime;
    }
    // Getters
    public String getName(){
        return this.name;
    }
    public String getDescription(){
        return this.description;
    }
    public double getPrice(){
        return this.price;
    }
    public String getCategory(){
        return this.category;
    }

    public double getPreparationTime(){
        return this.preparationTime;
    }
    // Setters
    public void setName(String name){
        this.name = name;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public void setPrice(double price){
        this.price = price;
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

