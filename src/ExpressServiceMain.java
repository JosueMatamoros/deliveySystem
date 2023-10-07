import menu.Discounts;
import menu.Product;
import order.OrderProducts;
import order.Orders;
import person.ExpressService;
import person.aggregated.Address;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The type Express service main.
 */
public class ExpressServiceMain {
    /**
     * The constant input.
     */
    public static Scanner input = new Scanner(System.in);
    public static ArrayList<Orders> order = new ArrayList<>();
    static ArrayList<ExpressService> expressServicesEntities = new ArrayList<>();

    public void addExpressService(ExpressService expressService) {
        expressServicesEntities.add(expressService);
    }

    /**
     * Show menu express.
     */
    public static void showMenuExpress() {
        int option;
        do {

            System.out.println("Welcome to Express Service System");
            System.out.println("1. Edit Express Service Drivers");
            System.out.println("2. Assign Express Service Driver");
            System.out.println("3. Show Express Service Drivers");
            System.out.println("4. Mark as delivered");
            System.out.println("5. Exit");

            System.out.print("Enter option: ");
            if(input.hasNextInt()) {
                option = input.nextInt();
            } else {
                option = 0;
                input.nextLine();
            }

            switch (option) {
                case 1:
                    boolean exitDrivers = false;
                    do try {
                        System.out.println("1. Add Express Service Driver");
                        System.out.println("2. Edit Express Service Driver");
                        System.out.println("3. Delete Express Service Driver");
                        System.out.println("4. Exit");
                        int optionEditDriver = input.nextInt();
                        switch (optionEditDriver) {
                            case 1:
                                System.out.println("You're in the option to add a new driver");
                                input.nextLine();
                                System.out.println("Enter the name of the driver: ");
                                String name = input.nextLine();
                                System.out.println("Enter the phone of the driver: ");
                                String phone = input.nextLine();
                                System.out.println("Enter the gender of the driver: ");
                                String gender = input.nextLine();
                                boolean exitAge = false;
                                byte age = 0;
                                do {
                                    System.out.println("Enter the age of the driver: ");
                                    if (input.hasNextByte()) {
                                        age = input.nextByte();
                                        if (age < 18) {
                                            System.out.println("The age must be greater than 18");
                                        } else {
                                            exitAge = true;
                                        }
                                    } else {
                                        exitAge = false;
                                        input.nextLine(); // Consume the newline character
                                    }
                                } while (!exitAge);

                                // Add an additional input.nextLine() here to consume the newline character
                                input.nextLine();

                                System.out.println("Enter the entity name of the driver: ");
                                String entityName = input.nextLine();
                                System.out.println("Enter the identification of the driver: ");
                                String identification = input.nextLine();
                                System.out.println("Enter the vehicle type of the driver: ");
                                String vehiculeType = input.nextLine();
                                System.out.println("Enter the vehicle plate of the driver: ");
                                String vehiculePlate = input.nextLine();
                                System.out.println("Enter the characteristics of the driver: ");
                                String caracteristics = input.nextLine();

                                expressServicesEntities.add(new ExpressService(name, phone, gender, age, entityName, identification, vehiculeType, vehiculePlate, caracteristics));
                                break;
                            case 2:
                                System.out.println("You're in the option to edit a driver");
                                System.out.println("Enter the name of the driver that do you want edit: ");
                                String nameEdit = input.next();
                                if (searchExpressService(nameEdit)) {
                                    System.out.println("The driver exist, please enter the new data\n");
                                    System.out.println("Enter the new name of the driver: ");
                                    String newName = input.nextLine();
                                    System.out.println("Enter the new phone of the driver: ");
                                    String newPhone = input.nextLine();
                                    System.out.println("Enter the new gender of the driver: ");
                                    String newGender = input.nextLine();
                                    boolean exitNewAge = false;
                                    byte newAge = 0;
                                    do {
                                        System.out.println("Enter the new age of the driver: ");
                                        if (input.hasNextByte()) {
                                            newAge = input.nextByte();
                                            if (newAge < 18) {
                                                System.out.println("The age must be greater than 18");
                                            } else {
                                                exitNewAge = true;
                                            }
                                        } else {
                                            exitNewAge = true;
                                            input.nextLine(); // Consume the newline character
                                        }
                                    } while (!exitNewAge);
                                    System.out.println("Enter the new entity name of the driver: ");
                                    String newEntityName = input.nextLine();
                                    System.out.println("Enter the new identification of the driver: ");
                                    String newIdentification = input.nextLine();
                                    System.out.println("Enter the new vehicle type of the driver: ");
                                    String newVehiculeType = input.nextLine();
                                    System.out.println("Enter the new vehicle plate of the driver: ");
                                    String newVehiculePlate = input.nextLine();
                                    System.out.println("Enter the new characteristics of the driver: ");
                                    String newCaracteristics = input.nextLine();

                                    for (ExpressService expressService : expressServicesEntities) {
                                        if (expressService.getFullName().equalsIgnoreCase(nameEdit)) {
                                            expressService.setFullName(newName);
                                            expressService.setPhone(newPhone);
                                            expressService.setGender(newGender);
                                            expressService.setAge(newAge);
                                            expressService.setEntityName(newEntityName);
                                            expressService.setIdentification(newIdentification);
                                            expressService.setVehiculeType(newVehiculeType);
                                            expressService.setVehiculePlate(newVehiculePlate);
                                            expressService.setCaracteristics(newCaracteristics);
                                        }
                                    }

                                } else {
                                    System.out.println("The driver doesn't exist, please try again !");
                                }
                                break;
                            case 3:
                                System.out.println("You're in the option to delete a driver");
                                System.out.println("Enter the name of the driver that do you want delete: ");
                                String nameDelete = input.next();
                                if (searchExpressService(nameDelete)) {
                                    for (ExpressService expressService : expressServicesEntities) {
                                        if (expressService.getFullName().equalsIgnoreCase(nameDelete)) {
                                            expressServicesEntities.remove(expressService);
                                        }
                                    }
                                } else {
                                    System.out.println("The driver doesn't exist, please try again !");
                                }
                                break;
                            case 4:
                                System.out.println("You're going to exit of the edit driver option");
                                exitDrivers = true;
                                break;
                            default:
                                System.out.println("Invalid option, please try again !");
                                break;
                        }
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    } while (!exitDrivers);
                    break;
                case 2:
                    System.out.println("You're in the option to assign a driver");
                    System.out.println("The orders that are ready to pick up are: ");

                    // List only orders that are "Ready to pick up"
                    for (Orders orders : order) {
                        if (orders.getState().equalsIgnoreCase("Ready to pick up")) {
                            System.out.print(orders);
                        }
                    }

                    input.nextLine(); // Consume the newline character

                    System.out.println("Choose the number of the order that you can pick up: ");
                    int numberOrder = input.nextInt();
                    input.nextLine(); // Consume the newline character

                    // Find the selected order
                    Orders selectedOrder = null;
                    for (Orders orders : order) {
                        if (orders.getOrderNumber() == numberOrder) {
                            selectedOrder = orders;
                            break;
                        }
                    }

                    if (selectedOrder != null) {
                        for (ExpressService expressService : expressServicesEntities) {
                            if (expressService.getState()){
                                System.out.println(expressService.getFullName() + " -> " + " Driver " + " --> " + " Not available");
                            } else {
                                System.out.println(expressService.getFullName() + " -> " + " Driver " + " --> " + " Available");
                            }
                        }
                        System.out.println("Enter the name of the driver that you want to assign: ");
                        String nameAssign = input.nextLine();
                        boolean driverFound = false;

                        for (ExpressService expressService : expressServicesEntities) {
                            if (expressService.getFullName().equalsIgnoreCase(nameAssign) && !expressService.getState()) {
                                expressService.setState(true);
                                selectedOrder.setState("Assigned to driver");
                                System.out.println("The driver was assigned successfully ! Her is :" + expressService.getFullName() + " with the vehicle plate: " + expressService.getVehiculePlate() + " and the vehicle type: " + expressService.getVehiculeType());
                                driverFound = true;
                                break;
                            }
                        }

                        if (!driverFound) {
                            System.out.println("The driver doesn't exist, please try again !");
                        }
                    } else {
                        System.out.println("The order doesn't exist, please try again !");
                    }

                    break;
                case 3:
                    System.out.println("You're in the option to show the drivers");
                    for (ExpressService expressService : expressServicesEntities) {
                        if (expressService.getState()){
                            System.out.println(expressService.getFullName() + " -> " + " Driver " + " --> " + " Not available");
                        } else {
                            System.out.println(expressService.getFullName() + " -> " + " Driver " + " --> " + " Available");
                        }
                    }
                    break;
                case 4:
                    System.out.println("You're in the option to mark as delivered");
                    boolean exitMark = false;
                    int optionMark;
                    do {
                        System.out.println("The orders that are assigned to a driver are: ");
                        for (Orders orders : order) {
                            if (orders.getState().equalsIgnoreCase("Assigned to driver")) {
                                System.out.println("1. Finish the order");
                                System.out.println("2. Exit");
                                if(input.hasNextInt()) {
                                    optionMark = input.nextInt();
                                } else {
                                    optionMark = 0;
                                    input.nextLine();
                                }
                                switch (optionMark) {
                                    case 1:
                                        System.out.println("Enter the number of the order that you want to mark as delivered: ");
                                        int numberOrderMark = input.nextInt();
                                        input.nextLine(); // Consume the newline character

                                        // Find the selected order
                                        Orders selectedOrderMark = null;
                                        for (Orders orders1 : order) {
                                            if (orders.getOrderNumber() == numberOrderMark) {
                                                selectedOrderMark = orders;
                                                break;
                                            }
                                        }

                                        if (selectedOrderMark != null) {
                                            selectedOrderMark.setState("Ready");
                                            System.out.println("The order was marked as delivered successfully !");
                                            expressServicesEntities.get(0).setState(false);
                                            exitMark = true;
                                        } else {
                                            System.out.println("The order doesn't exist, please try again !");
                                        }
                                        break;
                                    case 2:
                                        System.out.println("You're going to exit of the mark as delivered option");
                                        exitMark = true;
                                        break;
                                    default:
                                        System.out.println("Invalid option, please try again !");
                                        break;
                                }
                            }
                        }

                    } while (!exitMark);

                case 5:
                default:
                    System.out.println("Invalid option, please try again !");
                    break;
            }
        } while (option != 5) ;
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {

        Product[] productos = {new Product("Coca-Cola", "Carbonated cola drink", 2.0, "Beverages", 0),
                new Product("Cheeseburger", "Classic cheeseburger with Angus beef", 12.5, "Fast Food", 20),
                new Product("Margherita Pizza", "Pizza with tomato sauce, mozzarella, and basil", 16.5, "Fast Food", 15),
                new Product("Grilled Chicken", "Grilled chicken breast with vegetables", 20.0, "Main Course", 12),
                new Product("Fruit Salad", "Fresh mixed fruit salad", 3.5, "Kids' Menu", 5),
                new Product("Hot Dog", "Hot dog with various toppings", 6.5, "Fast Food", 25),
                new Product("Spaghetti Bolognese", "Spaghetti with meat sauce", 12.5, "Main Course", 18),
                new Product("Milk", "Glass of fresh milk", 1.5, "Beverages", 0),
                new Product("Quesadilla", "Cheese quesadilla with guacamole", 14.0, "Fast Food", 20),
                new Product("Chicken Nuggets", "Chicken nuggets with honey mustard sauce", 12.5, "Kids' Menu", 15),
                new Product("Orange Juice", "Freshly squeezed orange juice", 5.0, "Beverages", 0),
                new Product("Tomato Soup", "Tomato soup with croutons", 9.0, "Main Course", 22),
                new Product("Apple Pie", "Classic apple pie", 9.0, "Desserts", 18),
                new Product("French Fries", "French fries with ketchup", 7.0, "Fast Food", 28),
                new Product("Lasagna", "Layered pasta with meat and cheese", 16.0, "Main Course", 20),
                new Product("Chocolate Ice Cream", "Chocolate-flavored ice cream", 4.0, "Desserts", 25),
                new Product("Chocolate Milkshake", "Chocolate milkshake with whipped cream", 15.0, "Beverages", 18),
                new Product("Vanilla Pudding", "Vanilla pudding with cookies", 6.5, "Desserts", 30),
                new Product("Apple Crumble", "Warm apple crumble with vanilla ice cream", 18.0, "Desserts", 12),
                new Product("Mineral Water", "Bottle of mineral water", 1.0, "Beverages", 0),
        };

        Discounts[] discounts = {new Discounts("Beverages", 10),
                new Discounts("Fast Food", 20),
                new Discounts("Main Course", 15),
                new Discounts("Kids' Menu", 5),
                new Discounts("Desserts", 10),
        };

        ArrayList<OrderProducts> burnOrders = new ArrayList<>();
        ArrayList<OrderProducts> burnOrdersToPickUp = new ArrayList<>();
        ArrayList<OrderProducts> burnOrdersToDeliver = new ArrayList<>();

        OrderProducts order1 = new OrderProducts(productos[0], 1);
        OrderProducts order2 = new OrderProducts(productos[5], 1);
        OrderProducts order3 = new OrderProducts(productos[10], 1);
        OrderProducts order4 = new OrderProducts(productos[15], 1);
        OrderProducts order5 = new OrderProducts(productos[1], 1);
        OrderProducts order6 = new OrderProducts(productos[6], 2);

        // Order of the third client to deliver
        burnOrdersToDeliver.add(order5);

        order.add(new Orders(burnOrdersToDeliver, new Address("Alajuela", "San Carlos", "San Ramon", "El burrito", "In front of the church") , null));

        expressServicesEntities.add(new ExpressService("Luis Ernesto", "1232322", "Male", (byte) 18, "Uber eats", "20916252", "Motorcycle", "1652gh", "Black motorcycle"));
        for (Orders order: order ) {
            order.setState("Ready to pick up");
        }
        showMenuExpress();
    }

    public static boolean searchExpressService(String name) {
        for (ExpressService expressService : expressServicesEntities) {
            if (expressService.getFullName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
}
