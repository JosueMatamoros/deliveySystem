package order;

import person.Client;
import person.Employee;
import person.aggregated.Address;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public class Orders {
    // Atributes
    private ArrayList<OrderProducts> order = new ArrayList<OrderProducts>();
    private int total;
    private Date date;
    private Time totalTime;
    private String tableNumber;
    private Time pickUpTime;
    private String state;
    private Employee employee;
    private Address address;
    private Client client;
    private int orderNumber;

    static int orderCount = 199;

    // Constructors
    public Orders(ArrayList<OrderProducts> order, String tableNumber, Employee employee ){
       // Constructor for orders in the restaurant
        this.order = order;
        this.tableNumber = tableNumber;
        this.employee = employee;
        this.state = "In the kitchen";
        this.orderNumber = ++orderCount;
        this.date = new Date(System.currentTimeMillis());
    }
    public Orders(ArrayList<OrderProducts> order, Time pickUpTime, Employee employee){
        // Constructor for orders to go
        this.order = order;
        this.pickUpTime = pickUpTime;
        this.employee = employee;
        this.state = "In the kitchen";
        this.orderNumber = ++orderCount;
        this.date = new Date(System.currentTimeMillis());
    }

    public Orders(ArrayList<OrderProducts> order, Address address, Employee employee, Client client){
        // Constructor for orders to deliver
        this.order = order;
        this.address = address;
        this.client = client;
        this.employee = employee;
        this.state = "In the kitchen";
        this.orderNumber = ++orderCount;
        this.date = new Date(System.currentTimeMillis());
    }
    // Getter
    public ArrayList<OrderProducts> getOrder(){
        return this.order;
    }
    public int getTotal(){
        return this.total;
    }
    public Date getDate(){
        return this.date;
    }
    public Time getTotalTime(){
        return this.totalTime;
    }
    public String getTableNumber(){
        return this.tableNumber;
    }
    public Time getPickUpTime(){
        return this.pickUpTime;
    }
    public String getState(){
        return this.state;
    }
    public Employee getEmployee(){
        return this.employee;
    }
    public Address getAddress(){
        return this.address;
    }
    public Client getClient(){
        return this.client;
    }
    public int getOrderNumber(){
        return this.orderNumber;
    }
    // Setters
    public void setState(String state){
        this.state = state;
    }
    // Methods
    public void appendOrder(OrderProducts order){
        this.order.add(order);
    }
    public void removeOrder(OrderProducts order){
        this.order.remove(order);
    }
}
