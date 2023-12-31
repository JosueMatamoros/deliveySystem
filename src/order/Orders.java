package order;

import person.Client;
import person.Employee;
import person.ExpressService;
import person.aggregated.Address;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * The type Orders.
 */
public class Orders implements Serializable {
    // Atributes
    private ArrayList<OrderProducts> order = new ArrayList<OrderProducts>();
    private int total;
    private Date date;
    private int totalTime;
    private int tableNumber;
    private LocalTime pickUpTime;
    private String state;
    private Employee employee;
    private Address address;
    private Client client;
    private int orderNumber;
    private ExpressService expressService;

    /**
     * The Order count.
     */
    static int orderCount = 199;

    /**
     * Instantiates a new Orders.
     *
     * @param order the order
     */
// Constructors
    public Orders(ArrayList<OrderProducts> order){
        // Constructor for orders in the restaurant
        this.order = order;
        this.state = "New order";
        this.orderNumber = ++orderCount;
        this.date = new Date(System.currentTimeMillis());
        this.total = 0;
    }

    /**
     * Instantiates a new Orders.
     *
     * @param order      the order
     * @param pickUpTime the pick up time
     */
    public Orders(ArrayList<OrderProducts> order, LocalTime pickUpTime){
        // Constructor for orders to go
        this.order = order;
        this.pickUpTime = pickUpTime;
        this.state = "New order";
        this.orderNumber = ++orderCount;
        this.date = new Date(System.currentTimeMillis());
    }

    /**
     * Instantiates a new Orders.
     *
     * @param order   the order
     * @param address the address
     * @param client  the client
     */
    public Orders(ArrayList<OrderProducts> order, Address address, Client client){
        // Constructor for orders to deliver
        this.order = order;
        this.address = address;
        this.client = client;
        this.state = "New order";
        this.orderNumber = ++orderCount;
        this.date = new Date(System.currentTimeMillis());
    }

    /**
     * Get order array list.
     *
     * @return the array list
     */
// Getter
    public ArrayList<OrderProducts> getOrder(){
        return this.order;
    }

    /**
     * Get total int.
     *
     * @return the int
     */
    public int getTotal(){
        return this.total;
    }

    /**
     * Get date date.
     *
     * @return the date
     */
    public Date getDate(){
        return this.date;
    }

    /**
     * Get total time int.
     *
     * @return the int
     */
    public int getTotalTime(){
        return this.totalTime;
    }

    /**
     * Get table number int.
     *
     * @return the int
     */
    public int getTableNumber(){
        return this.tableNumber;
    }

    /**
     * Get pick up time local time.
     *
     * @return the local time
     */
    public LocalTime getPickUpTime(){
        return this.pickUpTime;
    }

    /**
     * Get state string.
     *
     * @return the string
     */
    public String getState(){
        return this.state;
    }

    /**
     * Get employee employee.
     *
     * @return the employee
     */
    public Employee getEmployee(){
        return this.employee;
    }

    /**
     * Get address address.
     *
     * @return the address
     */
    public Address getAddress(){
        return this.address;
    }

    public ExpressService getExpressService(){
        return this.expressService;
    }

    /**
     * Get client client.
     *
     * @return the client
     */
    public Client getClient(){
        return this.client;
    }

    /**
     * Get order number int.
     *
     * @return the int
     */
    public int getOrderNumber(){
        return this.orderNumber;
    }

    /**
     * Set state.
     *
     * @param state the state
     */
// Setters
    public void setState(String state){
        this.state = state;
    }

    /**
     * Set table number.
     *
     * @param tableNumber the table number
     */
    public void setTableNumber(int tableNumber){
        this.tableNumber = tableNumber;
    }

    /**
     * Set employee.
     *
     * @param employee the employee
     */
    public void setEmployee(Employee employee){
        this.employee = employee;
    }

    /**
     * Set total.
     *
     * @param total the total
     */
    public void setTotal(int total){
        this.total = total;
    }
    public void setExpressService(ExpressService expressService){
        this.expressService = expressService;
    }

    /**
     * Set preparation time.
     *
     * @param totalTime the total time
     */
    public void setPreparationTime(int totalTime){
        this.totalTime = totalTime;
    }

    /**
     * Append order.
     *
     * @param order the order
     */
// Methods
    public void appendOrder(OrderProducts order){
        this.order.add(order);
    }

    /**
     * Remove order.
     *
     * @param order the order
     */
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

    /**
     * To string restaurant string.
     *
     * @return the string
     */
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
        sb.append("  Employee: ").append(employee).append("\n");
        sb.append("\n");
        sb.append("  Order Items:\n");
        for (OrderProducts item : order) {
            sb.append(item).append("\n");
        }

        return sb.toString();
    }

    /**
     * To string pick up string.
     *
     * @return the string
     */
    public String toStringPickUp(){

        StringBuilder sb = new StringBuilder();
        sb.append("Order Information:\n");
        sb.append("\n");
        sb.append("  Order Number: ").append(orderNumber).append("\n");
        sb.append("  Date: ").append(date).append("\n");
        sb.append("  Pick-up Time: ").append(pickUpTime).append("\n");
        sb.append("  State: ").append(state).append("\n");
        sb.append("  Total: $").append(total).append("\n");
        sb.append("  Total Time: ").append(totalTime).append(" minutes\n");
        sb.append("  Employee: ").append(employee).append("\n");
        sb.append("\n");
        sb.append("  Order Items:\n");
        for (OrderProducts item : order) {
            sb.append(item).append("\n");
        }

        return sb.toString();
    }

    /**
     * To string deliver string.
     *
     * @return the string
     */
    public String toStringDeliver(){

        StringBuilder sb = new StringBuilder();
        sb.append("Order Information:\n");
        sb.append("\n");
        sb.append("  Order Number: ").append(orderNumber).append("\n");
        sb.append("  Date: ").append(date).append("\n");
        if (address != null) {
            sb.append("  Address: ").append(address).append("\n");
        }
        else {
            sb.append("  Address: ").append(client.getAddresses()).append("\n");
        }
        sb.append("  State: ").append(state).append("\n");
        sb.append("  Total: $").append(total).append("\n");
        sb.append("  Total Time: ").append(totalTime).append(" minutes\n");
        sb.append("  Employee: ").append(employee).append("\n");
        sb.append("\n");
        sb.append("  Order Items:\n");
        for (OrderProducts item : order) {
            sb.append(item).append("\n");
        }

        return sb.toString();
    }


    /**
     * To string client string.
     *
     * @return the string
     */
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