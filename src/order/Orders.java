package order;

import menu.Discounts;
import menu.Menu;
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
    private int tableNumber;
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
        this.state = "New order";
        this.orderNumber = ++orderCount;
        this.date = new Date(System.currentTimeMillis());
        this.total = 0;
    }
    public Orders(ArrayList<OrderProducts> order, LocalTime pickUpTime){
        // Constructor for orders to go
        this.order = order;
        this.pickUpTime = pickUpTime;
        this.state = "New order";
        this.orderNumber = ++orderCount;
        this.date = new Date(System.currentTimeMillis());
    }

    public Orders(ArrayList<OrderProducts> order, Address address, Client client){
        // Constructor for orders to deliver
        this.order = order;
        this.address = address;
        this.client = client;
        this.state = "New order";
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
    public int getTableNumber(){
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
    public void setTableNumber(int tableNumber){
        this.tableNumber = tableNumber;
    }
    public void setEmployee(Employee employee){
        this.employee = employee;
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
        StringBuilder sb = new StringBuilder();
        sb.append("Order Information:\n");
        sb.append("  Order Number: ").append(orderNumber).append("\n");
        sb.append("  Table Number: ").append(tableNumber).append("\n");
        sb.append("  Date: ").append(date).append("\n");
        sb.append("  Pick-up Time: ").append(pickUpTime).append("\n");
        sb.append("  State: ").append(state).append("\n");
        sb.append("  Total: $").append(total).append("\n");
        sb.append("  Total Time: ").append(totalTime).append(" minutes\n");

        sb.append("  Employee: ").append(employee).append("\n");
        sb.append("  Client: ").append(client).append("\n");

        sb.append("  Address: ").append(address).append("\n");

        sb.append("  Order Items:\n");
        for (OrderProducts item : order) {
            sb.append("    - ").append(item).append("\n");
        }

        return sb.toString();
    }

    public String toStringRestaurant(){

        StringBuilder sb = new StringBuilder();
        sb.append("Order Information:\n");
        sb.append("\n");
        sb.append("  Order Number: ").append(orderNumber).append("\n");
        sb.append("  Table Number: ").append(tableNumber).append("\n");
        sb.append("  Date: ").append(date).append("\n");
        sb.append("  State: ").append(state).append("\n");
        sb.append("  Total: $").append(total).append("\n");
        sb.append("  Total Time: ").append(totalTime).append(" minutes\n");
        sb.append("\n");
        sb.append("  Employee: ").append(employee).append("\n");
        sb.append("\n");
        sb.append("  Order Items:\n");
        sb.append("\n");
        for (OrderProducts item : order) {
            sb.append(item).append("\n");
        }

        return sb.toString();
    }

    public String toStringClient() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order Information:\n");
        sb.append("  Order Number: ").append(orderNumber).append("\n");
        sb.append("  Table Number: ").append(tableNumber).append("\n");
        sb.append("  Date: ").append(date).append("\n");
        sb.append("  Total: $").append(total).append("\n");
        sb.append("  Total Time: ").append(totalTime).append(" minutes\n");

        sb.append("  Employee: ").append(employee).append("\n");

        sb.append("  Order Items:\n");
        for (OrderProducts item : order) {
            sb.append("    - ").append(item).append("\n");
        }

        return sb.toString();
    }


}
