package menu;

import java.util.Date;

public class Discounts {
    // Attributes
    private String category;
    private double discount;
    private Date startDate;
    private Date endDate;

    // Constructor
    public Discounts(String category, double discount, Date startDate, Date endDate){
        this.category = category;
        this.discount = discount;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
