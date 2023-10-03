package order;

import person.Client;
import person.Employee;
import person.aggregated.Address;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;

public class Orders {
    // Atributes
    private ArrayList<OrderProducts> order = new ArrayList<OrderProducts>();
    private int total;
    private Date date;
    private Time totalTime;
    private String tableNumber;
    private LocalTime pickUpTime;
    private String state;
    private Employee employee;
    private Address address;
    private Client client;
    private int orderNumber;

    static int orderCount = 199;

    // Constructors
    public Orders(ArrayList<OrderProducts> order){
       // Constructor for orders in the restaurant
        this.order = order;
        this.state = "In the kitchen";
        this.orderNumber = ++orderCount;
        this.date = new Date(System.currentTimeMillis());
    }
    public Orders(ArrayList<OrderProducts> order, LocalTime pickUpTime){
        // Constructor for orders to go
        this.order = order;
        this.pickUpTime = pickUpTime;
        this.state = "In the kitchen";
        this.orderNumber = ++orderCount;
        this.date = new Date(System.currentTimeMillis());
    }

    public Orders(ArrayList<OrderProducts> order, Address address, Client client){
        // Constructor for orders to deliver
        this.order = order;
        this.address = address;
        this.client = client;
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
    public LocalTime getPickUpTime(){
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

    @Override
    public String toString() {
        return "Orders{" +
                "order=" + order +
                ", total=" + total +
                ", date=" + date +
                ", totalTime=" + totalTime +
                ", tableNumber='" + tableNumber + '\'' +
                ", pickUpTime=" + pickUpTime +
                ", state='" + state + '\'' +
                ", employee=" + employee +
                ", address=" + address +
                ", client=" + client +
                ", orderNumber=" + orderNumber +
                '}';
    }
}
