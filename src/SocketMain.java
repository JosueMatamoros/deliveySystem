import menu.Discounts;
import menu.Menu;
import menu.Product;
import order.OrderProducts;
import order.Orders;
import person.Client;
import person.Employee;
import person.ExpressService;
import person.aggregated.Address;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;


/**
 * The type Socket main.
 */
public class SocketMain {
    /**
     * The constant table.
     */
    // Restaurant tables
    static int table = 0;
    /**
     * The constant orders.
     */
    static ArrayList<Orders> orders = new ArrayList<>();

    static ArrayList<Employee> employees = new ArrayList<>();

    static ArrayList<ExpressService> expressServicesEntities = new ArrayList<>();

    // METHODS ========================================================================================================
    /**
     * Search client boolean.
     *
     * @param clients the clients
     * @param name    the name
     * @return the boolean
     */
    public static boolean searchClient(ArrayList<Client> clients, String name) {
        for (Client client : clients) {
            if (client.getFullName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Input address address.
     *
     * @return the address
     */
    public static Address inputAddress() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Province: ");
        String province = scanner.nextLine();
        System.out.println("District: ");
        String district = scanner.nextLine();
        System.out.println("Canton: ");
        String canton = scanner.nextLine();
        System.out.println("Exact Address: ");
        String exactAddress = scanner.nextLine();
        System.out.println("Description: ");
        String description = scanner.nextLine();

        return new Address(province, district, canton, exactAddress, description);
    }

    public static boolean searchExpressService(String name) {
        for (ExpressService expressService : expressServicesEntities) {
            if (expressService.getFullName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // CREATING THE RESTAURANT SERVER, IS "MAIN" SERVER;
        int port = 12345;
        ServerSocket serverSocket = null;

        // Employee


        employees.add( new Employee("Luis Cubillo", "+506 8585 2107", "Male", (byte) 21, "Chef", 14, false));
        employees.add( new Employee("Ram Ortiz", "+506 6477 0084", "Male", (byte) 21, "Chef", 18, false)) ;
        employees.add( new Employee("Andres Esquivel", "+506 8547 7746", "Male", (byte) 21, "Chef", 14, false));
        employees.add( new Employee("Hector Caravaca", "+506 8333 9684", "Male", (byte) 21, "Chef", 30, false));

        // Product
        Product [] productos = {new Product("Coca-Cola", "Carbonated cola drink", 2.0, "Beverages", 0),
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
        // Discounts
        Discounts [] discounts = {new Discounts("Beverages", 10),
                new Discounts("Fast Food", 20),
                new Discounts("Main Course", 15),
                new Discounts("Kids' Menu", 5),
                new Discounts("Desserts", 10),
        };

        // Menu
        Menu menu = new Menu();
        for (Product product : productos) {
            menu.appendProduct(product);
        }
        for (Discounts discount : discounts) {
            menu.addDiscount(discount);
        }

        int optionMain;

        boolean finish = false;

        do {
            System.out.println("Choose an option");
            System.out.println("1. Clients");
            System.out.println("2. Restaurant System");
            System.out.println("3. Delivery System");
            System.out.println("4. Exit");
            if (scanner.hasNextInt()) {
                optionMain = scanner.nextInt();
            } else {
                optionMain = 0;
                scanner.nextLine();
            }

            switch (optionMain) {
                    case 1:
                        try {
                            serverSocket = new ServerSocket(port);
                            System.out.println("Server waiting for connections on port " + port);

                            do {
                                Socket clientSocket = serverSocket.accept();
                                System.out.println("Client connected since " + clientSocket.getInetAddress());

                                // Set up input and output streams
                                ObjectInputStream objectInput = new ObjectInputStream(clientSocket.getInputStream());
                                ObjectOutputStream objectOutPut = new ObjectOutputStream(clientSocket.getOutputStream());
                                BufferedReader inputBuffer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                                PrintWriter confirmation = new PrintWriter(clientSocket.getOutputStream(), true);

                                // Client
                                Address[] addressesList = {
                                        new Address("San Jose", "Escazu", "San Antonio", "789 Mountain View Road", "Mountain Cabin"),
                                        new Address("Alajuela", "Grecia", "Grecia", "567 Parkside Drive", "Rural Farmhouse"),
                                        new Address("Cartago", "La Union", "Tres Rios", "210 Riverside Avenue", "Suburban Home"),
                                        new Address("Cartago", "La Union", "Tres Rios", "210 Riverside Avenue", "Suburban Home")
                                };

                                // Create a list of addresses
                                ArrayList<Address> client1Addresses = new ArrayList<>();
                                client1Addresses.add(addressesList[0]);
                                client1Addresses.add(addressesList[1]);
                                ArrayList<Address> client2Addresses = new ArrayList<>();
                                client2Addresses.add(addressesList[2]);
                                client2Addresses.add(addressesList[3]);
                                ArrayList<Address> client3Addresses = new ArrayList<>();
                                client3Addresses.add(addressesList[2]);
                                client3Addresses.add(addressesList[0]);

                                // Create a client with the list of addresses
                                ArrayList<Client> clientArrayList = new ArrayList<>();
                                Client client1 = new Client("Juana Mora Perez", "83337675", "Masculino", (byte) 18, client1Addresses);
                                Client client2 = new Client("Maria Mora Perez", "83337675", "Masculino", (byte) 18, client2Addresses);
                                Client client3 = new Client("Juana Mora Perez", "83337675", "Masculino", (byte) 18, client3Addresses);
                                clientArrayList.add(client1);
                                clientArrayList.add(client2);
                                clientArrayList.add(client3);

                                objectOutPut.writeObject(menu);

                                // Receive the list of selected products from the client
                                ArrayList<OrderProducts> selectedProducts = (ArrayList<OrderProducts>) objectInput.readObject();

                                for (OrderProducts product : selectedProducts) {
                                    System.out.println("The client has selected: " + product);
                                }

                                confirmation.println("Received successfully!");

                                do {
                                    // Send a message to the client
                                    confirmation.println("1. Dine-in 2. Takeout 3. Express Service 4. Exit Option: ");

                                    // Read the client's response
                                    String clientResponse = inputBuffer.readLine();

                                    if (clientResponse != null && !clientResponse.isEmpty()) {
                                        try {
                                            int option = Integer.parseInt(clientResponse);
                                            switch (option) {
                                                case 1:
                                                    confirmation.println("Your order will be served at moment");
                                                    Orders newOrder = new Orders(selectedProducts);
                                                    orders.add(newOrder);
                                                    finish = true;
                                                    break;
                                                case 2:
                                                    confirmation.println("Your chose takeout");
                                                    confirmation.println("What time will you pick up your order (HH:mm:ss): ");

                                                    try {
                                                        String pickupTimeInput = inputBuffer.readLine();

                                                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                                                        LocalTime pickupTime = LocalTime.parse(pickupTimeInput, formatter);
                                                        Orders newOrderPickup = new Orders(selectedProducts, pickupTime);

                                                        orders.add(newOrderPickup);

                                                        // THIS SHOULD NOT BE SHOWN ON THE CLIENT SIDE, IT SHOULD GO TO THE RESTAURANT;
                                                        confirmation.println("Your order will be ready at: " + pickupTime + " and the order number is: " + newOrderPickup.getOrderNumber());

                                                    } catch (DateTimeParseException e) {
                                                        // Handle the exception if the time format is incorrect
                                                        confirmation.println("Error: The time format is incorrect. It should be HH:mm:ss.");
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    } catch (Exception e) {
                                                        // Handle any other unspecified exception
                                                        e.printStackTrace(); // You can customize how to handle this error
                                                    }

                                                    finish = true;
                                                    break;
                                                case 3:
                                                    confirmation.println("Your chose express service");
                                                    confirmation.println("Are you a frequent customer? (y/n) ");
                                                    String frequentCustomerResponse = inputBuffer.readLine();

                                                    if (frequentCustomerResponse.equalsIgnoreCase("y")) {
                                                        confirmation.println("Yes, you are a frequent customer");
                                                        confirmation.println("Enter your full name: ");
                                                        String fullName = inputBuffer.readLine();

                                                        Client clientOne = null;
                                                        if (searchClient(clientArrayList, fullName)) {
                                                            confirmation.println("You're in the costumer list");
                                                            for (Client client : clientArrayList) {
                                                                if (client.getFullName().equalsIgnoreCase(fullName)) {
                                                                    clientOne = new Client(client.getFullName(), client.getPhone(), client.getGender(), client.getAge(), client.getAddresses());
                                                                    Orders newOrderExpress = new Orders(selectedProducts, null, clientOne);
                                                                    orders.add(newOrderExpress);
                                                                    finish = true;
                                                                } else {
                                                                    continue;
                                                                }
                                                            }

                                                        } else {
                                                            finish = false;
                                                        }

                                                        if (clientOne != null) {
                                                            confirmation.println("You're a frequent dates: " + fullName + " " + "and your address are: " + clientOne.getAddresses());
                                                        } else {
                                                            confirmation.println("You're not a frequent customer");
                                                        }
                                                    } else if (frequentCustomerResponse.equalsIgnoreCase("n")) {
                                                        confirmation.println("You're not a frequent customer, please fill in the following information:");

                                                        confirmation.println("Enter your full name: ");
                                                        String fullName = inputBuffer.readLine();
                                                        confirmation.println("Enter your phone number: ");
                                                        String phoneNumber = inputBuffer.readLine();
                                                        confirmation.println("Enter your gender: ");
                                                        String gender = inputBuffer.readLine();
                                                        confirmation.println("Enter your age: ");
                                                        Byte age = Byte.parseByte(inputBuffer.readLine());

                                                        confirmation.println("Now, let's fill in your address: ");
                                                        confirmation.println("Enter your province: ");
                                                        String province = inputBuffer.readLine();
                                                        confirmation.println("Enter your canton: ");
                                                        String canton = inputBuffer.readLine();
                                                        confirmation.println("Enter your district: ");
                                                        String district = inputBuffer.readLine();
                                                        confirmation.println("Enter your exact address: ");
                                                        String exactAddress = inputBuffer.readLine();
                                                        confirmation.println("Description: ");
                                                        String descriptionAddress = inputBuffer.readLine();

                                                        Address address = new Address(province, canton, district, exactAddress, descriptionAddress);
                                                        ArrayList<Address> addresses = new ArrayList<>();
                                                        addresses.add(address);

                                                        Client client = new Client(fullName, phoneNumber, gender, age, addresses);
                                                        System.out.println("Full name client is: " + client.getFullName());
                                                        Orders newOrderExpress = new Orders(selectedProducts, null, client);
                                                        orders.add(newOrderExpress);
                                                        confirmation.println("Your order will be delivered to: " + address.getCompleteAddress() + " and the order number is: " + newOrderExpress.getOrderNumber());
                                                        finish = true;
                                                    } else {
                                                        confirmation.println("You sent an invalid response.");
                                                        finish = false;
                                                    }

                                                    break;
                                                case 4:
                                                    confirmation.println("You chose to exit");
                                                    break;
                                                default:
                                                    confirmation.println("You chose an invalid option");
                                                    break;
                                            }
                                        } catch (NumberFormatException e) {
                                            // Handle the error if 'clientResponse' is not a valid number
                                            confirmation.println("You sent an invalid option.");
                                        }
                                    } else {
                                        // Handle the case where the response is empty or null
                                        confirmation.println("The client sent an empty or null response.");
                                    }
                                } while (!finish);
                            } while (!finish);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                serverSocket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } break;
                    case 2:
                        // Add the orders to the list of orders
                        ArrayList<OrderProducts> burnOrders = new ArrayList<>();
                        ArrayList<OrderProducts> burnOrdersToPickUp = new ArrayList<>();
                        ArrayList<OrderProducts> burnOrdersToDeliver = new ArrayList<>();

                        OrderProducts order1 = new OrderProducts(productos[0], 1);
                        OrderProducts order2 = new OrderProducts(productos[5], 1);
                        OrderProducts order3 = new OrderProducts(productos[10], 1);
                        OrderProducts order4 = new OrderProducts(productos[15], 1);
                        OrderProducts order5 = new OrderProducts(productos[1], 1);
                        OrderProducts order6 = new OrderProducts(productos[6], 2);

                        // Order of the first client to eat in the restaurant
                        burnOrders.add(order1);
                        burnOrders.add(order2);
                        // Order of the second client to Pick Up
                        burnOrdersToPickUp.add(order3);
                        burnOrdersToPickUp.add(order4);
                        // Order of the third client to deliver
                        burnOrdersToDeliver.add(order5);
                        burnOrdersToDeliver.add(order6);

                        // Add the orders to the list of orders
                        orders.add(new Orders(burnOrders));
                        orders.add(new Orders(burnOrdersToPickUp, LocalTime.of(2, 0, 0)));
                        orders.add(new Orders(burnOrdersToDeliver, new Address("Alajuela", "San Carlos", "San Ramon", "El burrito", "In front of the church") , null));

                        // Address
                        Address address = new Address("Alajuela", "Penas Blancas", "San Ramon", "Chachagua", "In front of the church");
                        Address address1 = new Address("Alajuela", "Penas Blancas", "San Ramon", "Calle Pechuga", "Close to the school");
                        Address address2 = new Address("Alajuela", "San Carlos", "San Ramon", "El burrito", "Next to the park");

                        // First Client
                        ArrayList<Address> addresses = new ArrayList<>();
                        addresses.add(address);
                        addresses.add(address1);
                        // Second Client
                        ArrayList<Address> addresses1 = new ArrayList<>();
                        addresses1.add(address2);

                        // Clients
                        ArrayList<Client> clients = new ArrayList<>();
                        clients.add(new Client("Kenny Rofrigues","+506 7296 8552", "Male", (byte)21, addresses));
                        clients.add(new Client("Asdrubal Ulate", "+506 8850 9804","Male", (byte)21, addresses1));

                        int opcion = 0;
                        do {
                            // Administration System
                            System.out.println("Welcome to the Administration System");
                            System.out.println("1. Edit Menu");
                            System.out.println("2. Edit Clients");
                            System.out.println("3. Edit Employees");
                            System.out.println("4. Orders Administration");
                            System.out.println("5. Reports");
                            System.out.println("6. Exit");

                            try {
                                // Get the option
                                opcion = scanner.nextInt();

                                // Check if the option is valid
                                switch (opcion){
                                    case 1:
                                        do {
                                            System.out.println("1. Add Product");
                                            System.out.println("2. Remove Product");
                                            System.out.println("3. Add Discount");
                                            System.out.println("4. Remove Discount");
                                            System.out.println("5. Show Menu");
                                            System.out.println("6. Exit");

                                            try {
                                                opcion = scanner.nextInt();
                                                switch (opcion){
                                                case 1:
                                                    scanner.nextLine(); // Clean the buffer
                                                    System.out.println("Name: ");
                                                    String name = scanner.nextLine();
                                                    System.out.println("Description: ");
                                                    String description = scanner.nextLine();
                                                    System.out.println("Price: ");
                                                    double price = scanner.nextDouble();
                                                    scanner.nextLine(); // Clean the \n after the double
                                                    System.out.println("Category: ");
                                                    String category = scanner.nextLine();
                                                    System.out.println("Preparation Time: ");
                                                    double preparationTime = scanner.nextDouble();
                                                    Product product = new Product(name, description, price, category, preparationTime);
                                                    menu.appendProduct(product);
                                                    System.out.println("Product added");
                                                    break;
                                                case 2:
                                                    scanner.nextLine(); // Clean the buffer
                                                    System.out.println("Name: ");
                                                    String name1 = scanner.next();
                                                    // Search the product
                                                    for (Product product1 : menu.getProducts()) {
                                                        if (product1.getName().equals(name1)){
                                                            menu.removeProduct(product1);
                                                            System.out.println("Product removed");
                                                            break;
                                                        }
                                                    }

                                                    System.out.println("Product not found");
                                                    break;

                                                case 3:
                                                    System.out.println("Category: ");
                                                    String category1 = scanner.next();
                                                    System.out.println("Discount: ");
                                                    int discount = scanner.nextInt();
                                                    Discounts discounts1 = new Discounts(category1, discount);
                                                    menu.addDiscount(discounts1);
                                                    break;
                                                case 4:
                                                    System.out.println("Category: ");
                                                    String category2 = scanner.next();
                                                    // Search the discount
                                                    for (Discounts discounts2 : menu.getDiscounts()) {
                                                        if (discounts2.getCategory().equals(category2)){
                                                            menu.removeDiscount(discounts2);
                                                            System.out.println("Discount removed");
                                                            break;
                                                        }
                                                    }

                                                    System.out.println("Discount not found");
                                                    break;
                                                case 5:
                                                    System.out.println("Menu");
                                                    for (Product product1 : menu.getProducts()) {
                                                        System.out.print(product1.getName());
                                                        System.out.print(" ->  $");
                                                        System.out.print(product1.getPrice());
                                                        System.out.print(" -> ");
                                                        System.out.print(product1.getDescription());
                                                        System.out.println(" ");
                                                    }
                                                    break;
                                                case 6:
                                                    break;
                                                default:
                                                    System.out.println("Invalid option");
                                                    break;
                                                }
                                            } catch (Exception e) {
                                                System.out.println("Invalid option");
                                                scanner.nextLine();
                                            }
                                        }while (opcion != 6);
                                        break;
                                    case 2:
                                        do {
                                            System.out.println("1. Add Client");
                                            System.out.println("2. Remove Client");
                                            System.out.println("3. Edit Client");
                                            System.out.println("4. Exit");

                                            try {
                                                opcion = scanner.nextInt();
                                                switch (opcion){
                                                    case 1:
                                                        scanner.nextLine(); // Clean the buffer
                                                        System.out.println("Name: ");
                                                        String name = scanner.nextLine();
                                                        System.out.println("Phone: ");
                                                        String phone = scanner.nextLine();
                                                        System.out.println("Gender:");
                                                        String gender = scanner.nextLine();
                                                        System.out.println("Age: ");
                                                        byte age = scanner.nextByte();
                                                        scanner.nextLine(); // Clean the \n after the byte
                                                        System.out.println("No we have to add the address");
                                                        // Add the address
                                                        ArrayList<Address> clientAdresses = new ArrayList<>();
                                                        clientAdresses.add(inputAddress());
                                                        // Ask if the client has more than one address
                                                        System.out.println("Do you want to add another address? (y/n)");
                                                        String answer = scanner.nextLine();
                                                        if (answer.equals("y")){
                                                            clientAdresses.add(inputAddress());
                                                        }
                                                        // Create the client
                                                        clients.add(new  Client(name, phone, gender, age, clientAdresses));
                                                        System.out.println("Client added");
                                                        break;
                                                    case 2:
                                                        scanner.nextLine(); // Clean the buffer
                                                        System.out.println("Name: ");
                                                        String name1 = scanner.nextLine();
                                                        // Search the client
                                                        for (Client client : clients) {
                                                            if (client.getFullName().equals(name1)){
                                                                clients.remove(client);
                                                                System.out.println("Client removed");
                                                                break;
                                                            }
                                                        }

                                                        System.out.println("Client not found");
                                                        break;
                                                    case 3:
                                                        scanner.nextLine(); // Clean the buffer
                                                        System.out.println("Name: ");
                                                        String name2 = scanner.nextLine();
                                                        // Search the client
                                                        for (Client client: clients) {
                                                            if (client.getFullName().equals(name2)){
                                                                System.out.println("1. Add Address");
                                                                System.out.println("2. Remove Address");
                                                                System.out.println("3. Exit");
                                                                try {
                                                                    opcion = scanner.nextInt();
                                                                    switch (opcion){
                                                                        case 1:
                                                                            scanner.nextLine(); // Clean the buffer
                                                                            client.appendAddress(inputAddress());
                                                                            System.out.println("Address added");
                                                                            break;
                                                                        case 2:
                                                                            scanner.nextLine(); // Clean the buffer
                                                                            int cont = 0;
                                                                            for (Address address3 : client.getAddresses()) {
                                                                                System.out.println(++cont + ". " + address3.getCompleteAddress());
                                                                            }

                                                                            System.out.println("Select the address to remove");
                                                                            int address4 = scanner.nextInt();
                                                                            client.removeAddress(client.getAddresses().get(address4 - 1));
                                                                            break;
                                                                        case 3:
                                                                            break;
                                                                            default:
                                                                                System.out.println("Invalid option");
                                                                                break;
                                                                    }
                                                                } catch (Exception e) {
                                                                    System.out.println("Invalid option");
                                                                    scanner.nextLine();
                                                                }
                                                            }
                                                        }
                                                        break;
                                                    case 4:
                                                        break;
                                                    default:
                                                        System.out.println("Invalid option");
                                                        break;
                                                }
                                            }catch (Exception e) {
                                                System.out.println("Invalid option");
                                                scanner.nextLine();
                                            }
                                        }while (opcion != 4);
                                        break;

                                    case 3:
                                        do {
                                            System.out.println("1. Add Employee");
                                            System.out.println("2. Remove Employee");
                                            System.out.println("3. Edit Employee");
                                            System.out.println("4. Show Employees");
                                            System.out.println("5. Exit");

                                            try {
                                                opcion = scanner.nextInt();
                                                switch (opcion){
                                                    case 1:
                                                        scanner.nextLine(); // Clean the buffer
                                                        System.out.println("Name: ");
                                                        String name = scanner.nextLine();
                                                        System.out.println("Phone: ");
                                                        String phone = scanner.nextLine();
                                                        System.out.println("Gender:");
                                                        String gender = scanner.nextLine();
                                                        System.out.println("Age: ");
                                                        byte age = scanner.nextByte();
                                                        scanner.nextLine(); // Clean the \n after the byte
                                                        System.out.println("Job: ");
                                                        String job = scanner.nextLine();
                                                        System.out.println("Salary: ");
                                                        int salary = scanner.nextInt();
                                                        scanner.nextLine(); // Clean the \n after the int
                                                        employees.add(new Employee(name, phone, gender, age, job, salary, false));
                                                        System.out.println("Employee added");
                                                        break;
                                                    case 2:
                                                        scanner.nextLine(); // Clean the buffer
                                                        System.out.println("Name: ");
                                                        String name1 = scanner.nextLine();
                                                        // Search the employee
                                                        for (Employee employee : employees) {
                                                            if (employee.getFullName().equals(name1)){
                                                                employees.remove(employee);
                                                                System.out.println("Employee removed");
                                                                break;
                                                            }
                                                        }

                                                        System.out.println("Employee not found");
                                                        break;
                                                    case 3:
                                                        scanner.nextLine(); // Clean the buffer
                                                        System.out.println("Name: ");
                                                        String name2 = scanner.nextLine();
                                                        // Search the employee
                                                        for (Employee employee : employees) {
                                                            if (employee.getFullName().equals(name2)){
                                                                System.out.println("1. Change Job");
                                                                System.out.println("2. Change Salary");
                                                                System.out.println("3. Exit");
                                                                try {
                                                                    opcion = scanner.nextInt();
                                                                    switch (opcion){
                                                                        case 1:
                                                                            scanner.nextLine(); // Clean the buffer
                                                                            System.out.println("Job: ");
                                                                            String job1 = scanner.nextLine();
                                                                            employee.setJob(job1);
                                                                            System.out.println("Job changed");
                                                                            break;
                                                                        case 2:
                                                                            scanner.nextLine(); // Clean the buffer
                                                                            System.out.println("Salary: ");
                                                                            int salary1 = scanner.nextInt();
                                                                            employee.setSalary(salary1);
                                                                            System.out.println("Salary changed");
                                                                            break;
                                                                        case 3:
                                                                            break;
                                                                            default:
                                                                                System.out.println("Invalid option");
                                                                                break;
                                                                    }
                                                                } catch (Exception e) {
                                                                    System.out.println("Invalid option");
                                                                    scanner.nextLine();
                                                                    continue;
                                                                }
                                                                break;
                                                            }
                                                        }
                                                        break;
                                                    case 4:
                                                        System.out.println("Employees");
                                                        for (Employee employee : employees) {
                                                            System.out.print(employee.getFullName());
                                                            System.out.print(" -> ");
                                                            System.out.print(employee.getJob());
                                                            System.out.print(" -> $");
                                                            System.out.print(employee.getSalary());
                                                            System.out.println("/hr");
                                                        }
                                                        break;
                                                    case 5:
                                                        break;
                                                        default:
                                                            System.out.println("Invalid option");
                                                            break;

                                                }
                                            } catch (Exception e) {
                                                System.out.println("Invalid option");
                                                scanner.nextLine();
                                            }
                                        }while (opcion != 5);
                                        break;
                                    case 4:
                                        int orderOpction = 0;
                                        do {
                                            System.out.println("1. Show Orders");
                                            System.out.println("2. Start Order");
                                            System.out.println("3. Finish Order");
                                            System.out.println("4. mark pickup order");
                                            System.out.println("5. Exit");

                                            try {
                                                scanner.nextLine(); // Clean the buffer
                                                orderOpction = scanner.nextInt();
                                                switch (orderOpction){
                                                    case 1:
                                                        System.out.println("Orders");
                                                        for (Orders order : orders) {
                                                            if (order.getTotal() == 0){
                                                                double cont = 0;
                                                                int discontPercentage = 0;
                                                                int preparationTime = 0;
                                                                for (OrderProducts orderProducts : order.getOrder()) {
                                                                    for (Discounts discount : menu.getDiscounts()) {
                                                                        if (orderProducts.getProduct().getCategory().equals(discount.getCategory())){
                                                                            discontPercentage = discount.getDiscount();
                                                                            break;
                                                                        }
                                                                    }
                                                                    cont += orderProducts.getProduct().getPrice() * orderProducts.getQuantity() * (1 - discontPercentage / 100.0);
                                                                    preparationTime += (int) orderProducts.getProduct().getPreparationTime();
                                                                }
                                                                order.setTotal((int) cont);
                                                                order.setPreparationTime(preparationTime);
                                                            }
                                                            if (order.getAddress() != null || order.getClient() != null){
                                                                            // Order to deliver
                                                                System.out.println(order.toStringDeliver());
                                                            } else if (order.getPickUpTime() != null){
                                                                // Order to go
                                                                System.out.println(order.toStringPickUp());
                                                            } else {
                                                                // Order to eat in the restaurant
                                                                System.out.println(order.toStringRestaurant());
                                                            }
                                                        }
                                                        break;
                                                    case 2:
                                                        try {
                                                            System.out.println("Orders");
                                                            for (Orders order : orders) {
                                                                if (order.getState().equals("New order")){
                                                                    System.out.println(order.getOrderNumber());
                                                                    for (OrderProducts orderProducts : order.getOrder()) {
                                                                        System.out.println(orderProducts.toString());
                                                                    }
                                                                }

                                                            }
                                                            System.out.println("Select the order to start");
                                                            int orderNumber = scanner.nextInt();

                                                            // Check if the order is on another state
                                                            boolean orderAlreadyStarted = false;
                                                            for (Orders order : orders) {
                                                                if (!order.getState().equals("New order") && order.getOrderNumber() == orderNumber){
                                                                    System.out.println("Order already started");
                                                                    orderAlreadyStarted = true;
                                                                    break;
                                                                }
                                                            }
                                                            if (orderAlreadyStarted){
                                                                break;
                                                            }
                                                            scanner.nextLine(); // Clean the buffer

                                                            // Search the order
                                                            boolean orderFound = false;
                                                            for (Orders order : orders) {
                                                                if (order.getOrderNumber() == orderNumber){
                                                                    order.setTableNumber(++table);
                                                                    // Chose the employee
                                                                    System.out.println("Employees");
                                                                    int i = 0;
                                                                    for (Employee employee : employees) {
                                                                        System.out.print(++i + ". ");
                                                                        System.out.print(employee.getFullName());
                                                                        System.out.print(" -> ");
                                                                        System.out.print(employee.getJob());

                                                                        if (employee.getState()){
                                                                            System.out.print(" -> ");
                                                                            System.out.print("Not Available");
                                                                        }
                                                                        System.out.println(" ");
                                                                    }
                                                                    System.out.println("Select the employee");
                                                                    int employeeNumber = scanner.nextInt();
                                                                    if (employees.get(employeeNumber - 1).getState()){
                                                                        System.out.println("Employee not available");
                                                                        break;
                                                                    }
                                                                    // Mark the employee as not available
                                                                    employees.get(employeeNumber - 1).setState(true);

                                                                    // Add the employee to the order
                                                                    order.setEmployee(employees.get(employeeNumber - 1));
                                                                    order.setState("In the kitchen");
                                                                    System.out.println("Order started");
                                                                    orderFound = true;
                                                                    break;
                                                                }

                                                            }
                                                            if (!orderFound){
                                                                System.out.println("Order not found");
                                                            }
                                                        }catch (Exception e) {
                                                            System.out.println("Invalid option");
                                                            scanner.nextLine();
                                                        }
                                                        break;
                                                    case 3:
                                                        System.out.println("Orders");
                                                        for (Orders order : orders) {
                                                            if (order.getState().equals("In the kitchen")){
                                                                System.out.println(order.getOrderNumber());
                                                                for (OrderProducts orderProducts : order.getOrder()) {
                                                                    System.out.println(orderProducts.toString());
                                                                }
                                                            }
                                                        }
                                                        System.out.println("Select the order to finish");
                                                        int orderNumber1 = scanner.nextInt();

                                                        // Check if the order is on another state
                                                        boolean orderAlreadyStarted = false;
                                                        for (Orders order : orders) {
                                                            if (order.getOrderNumber() == orderNumber1 && !order.getState().equals("In the kitchen")){
                                                                System.out.println("Order already started");
                                                                orderAlreadyStarted = true;
                                                                break;
                                                            }
                                                        }
                                                        if (orderAlreadyStarted){
                                                            break;
                                                        }
                                                        scanner.nextLine(); // Clean the buffer
                                                        // Search the order
                                                        boolean orderFound1 = false;
                                                        for (Orders order : orders) {
                                                            if (order.getOrderNumber() == orderNumber1){
                                                                System.out.println("1. Mark the order as ready");
                                                                System.out.println("2. Mark one product as ready");
                                                                System.out.println("3. Exit");
                                                                orderFound1 = true;
                                                                int opcionInner = scanner.nextInt();

                                                                switch (opcionInner) {
                                                                    case 1:
                                                                        if (order.getAddress() != null || order.getClient() != null){
                                                                            // Order to deliver System.out.println(order.toStringDeliver());
                                                                            order.setState("Ready to deliver");
                                                                        } else if (order.getPickUpTime() != null){
                                                                            // Order to go
                                                                            order.setState("Ready to pick up");
                                                                        } else {
                                                                            // Order to eat in the restaurant
                                                                            order.setState("Ready");
                                                                        }

                                                                        // Mark all the products as ready
                                                                        for (OrderProducts orderProducts : order.getOrder()) {
                                                                            orderProducts.setState(true);
                                                                        }
                                                                        // Mark the employee as available
                                                                        order.getEmployee().setState(false);
                                                                        System.out.println("Order finished");
                                                                        break;
                                                                    case 2:
                                                                        System.out.println("Products");
                                                                        int i = 0;
                                                                        for (OrderProducts orderProducts : order.getOrder()) {
                                                                            if (orderProducts.getProduct().getPreparationTime() == 0){
                                                                                orderProducts.setState(true);
                                                                            }
                                                                            System.out.print(++i + ". ");
                                                                            System.out.print(orderProducts.getProduct().getName());
                                                                            System.out.print(" -> ");
                                                                            System.out.print(orderProducts.getProduct().getCategory());
                                                                            if (orderProducts.getState()){
                                                                                System.out.print(" -> ");
                                                                                System.out.print("Ready");
                                                                            }
                                                                            System.out.println(" ");
                                                                        }
                                                                        System.out.println("Select the product to mark as ready");
                                                                        try {
                                                                            int productNumber = scanner.nextInt();
                                                                            order.getOrder().get(productNumber - 1).setState(true);
                                                                            System.out.println("Product marked as ready");

                                                                            // Check if all the products are ready
                                                                            boolean allReady = true;
                                                                            for (OrderProducts orderProducts : order.getOrder()) {
                                                                                if (!orderProducts.getState()){
                                                                                    allReady = false;
                                                                                    break;
                                                                                }
                                                                            }
                                                                            if (allReady){
                                                                                if (order.getAddress() != null || order.getClient() != null){
                                                                                    // Order to deliver System.out.println(order.toStringDeliver());
                                                                                    order.setState("Ready to deliver");
                                                                                } else if (order.getPickUpTime() != null){
                                                                                    // Order to go
                                                                                    order.setState("Ready to pick up");
                                                                                } else {
                                                                                    // Order to eat in the restaurant
                                                                                    order.setState("Ready");
                                                                                }
                                                                                // Mark the employee as available
                                                                                order.getEmployee().setState(false);
                                                                                System.out.println("Order finished");
                                                                            }
                                                                        }catch (Exception e) {
                                                                            System.out.println("Invalid option");
                                                                            scanner.nextLine();
                                                                        }
                                                                        break;
                                                                    case 3:
                                                                        break;
                                                                        default:
                                                                            System.out.println("Invalid option");
                                                                            break;
                                                                }
                                                            }
                                                        }
                                                        if (!orderFound1){
                                                            System.out.println("Order not found");
                                                        }
                                                        break;
                                                    case 4:
                                                        System.out.println("Orders");
                                                        for (Orders order : orders) {
                                                            if (order.getState().equals("Ready to pick up")){
                                                                System.out.println(order.getOrderNumber());
                                                                for (OrderProducts orderProducts : order.getOrder()) {
                                                                    System.out.println(orderProducts.toString());
                                                                }
                                                            }
                                                        }
                                                        System.out.println("Select the order to mark as pickup");
                                                        int orderNumber2 = scanner.nextInt();
                                                        for (Orders order : orders) {
                                                            if (order.getOrderNumber() == orderNumber2) {
                                                                if (order.getState() != "Ready to pick up") {
                                                                    System.out.println("Order not ready to pick up");
                                                                    break;
                                                                }
                                                                // Mark the order as pickup
                                                                order.setState("Ready");
                                                                System.out.println("Order marked as Ready");
                                                            }
                                                        }
                                                        break;
                                                    case 5:
                                                        break;
                                                        default:
                                                            System.out.println("Invalid option");
                                                            break;
                                                }
                                            } catch (Exception e) {
                                                System.out.println("Invalid option");
                                                scanner.nextLine();
                                            }
                                        }while (orderOpction != 5);
                                        break;
                                        case 5:
                                            System.out.println("1. Total sales");
                                            System.out.println("2. Total sales by delivery");
                                            System.out.println("3. Completed order history");
                                            System.out.println("4. Exit");
                                            int opcion4 = scanner.nextInt();
                                            switch (opcion4) {
                                                case 1: // Total sales
                                                    double totalSales = 0;
                                                    int quantityOrders = 0;
                                                    for (Orders order : orders) {
                                                        if (order.getState().equalsIgnoreCase("Ready")) {
                                                            totalSales += order.getTotal();
                                                            for (OrderProducts orderProducts : order.getOrder()) {
                                                                quantityOrders = orderProducts.getQuantity();
                                                                for (Product product : menu.getProducts()) {
                                                                    if (orderProducts.getProduct().getName().equals(product.getName())) {
                                                                        product.setQuantityPerDay(quantityOrders);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                    System.out.println("Date:" + orders.get(2).getDate());
                                                    System.out.println("\n");
                                                    System.out.println("Total sales: " + totalSales);
                                                    System.out.println("Products sold: ");
                                                    // List of products
                                                    List<Product> productList = menu.getProducts();
                                                    // if quantityPerDay is 0, remove the product from the list
                                                    productList.removeIf(product -> product.getQuantityPerDay() == 0);
                                                    // Sort the list by quantityPerDay
                                                    Collections.sort(productList, Comparator.comparingInt(Product::getQuantityPerDay).reversed());

                                                    // Show the list
                                                    for (Product product : productList) {
                                                        System.out.println(product.getName() + " -> " + product.getQuantityPerDay());
                                                    }
                                                    break;
                                                case 2:
                                                    // Show all the orders by delivery
                                                    int contDelivery = 0;
                                                    for (Orders order : orders) {
                                                        if (order.getAddress() != null || order.getClient() != null){
                                                            ++contDelivery;
                                                        }
                                                    }
                                                    System.out.println("Total orders by delivery: " + contDelivery);
                                                    int Glovo = 0;
                                                    int UberEats = 0;
                                                    for (Orders order : orders) {
                                                        if (order.getAddress() != null || order.getClient() != null){
                                                            if (order.getExpressService().getEntityName().equals("Glovo")){
                                                                ++Glovo;
                                                            } else if (order.getExpressService().getEntityName().equals("Uber eats")){
                                                                ++UberEats;
                                                            }
                                                    }
                                                    // Show the total the order in order
                                                        if (Glovo > UberEats){
                                                            System.out.println("Glovo: " + Glovo);
                                                            System.out.println("Uber eats: " + UberEats);
                                                        } else {
                                                            System.out.println("Uber eats: " + UberEats);
                                                            System.out.println("Glovo: " + Glovo);
                                                        }
                                                    }
                                                    break;
                                                case 3:
                                                    System.out.println("Orders");
                                                    System.out.println("\n");
                                                    for (Orders order : orders) {
                                                        if (order.getState().equals("Ready")){
                                                            if (order.getClient() != null){
                                                                System.out.println(order.toStringDeliver());
                                                            } else if (order.getPickUpTime() != null){
                                                                System.out.println(order.toStringPickUp());
                                                            } else {
                                                                System.out.println(order.toStringRestaurant());
                                                            }
                                                        }
                                                    }
                                                    break;
                                                    case 4:
                                                        break;
                                                    default:
                                                        System.out.println("Invalid option");
                                                        break;

                                            }
                                            break;
                                        case 6:
                                            break;
                                            default:
                                                System.out.println("Invalid option");
                                                break;
                                }

                            } catch (Exception e) {
                                System.out.println("Invalid option");
                                scanner.nextLine();
                            }
                        }while (opcion != 6);
                        // EXPRESS SERVICE;
                        break;
            case 3:
                int option;
                Scanner input = new Scanner(System.in);

                expressServicesEntities.add(new ExpressService("Luis Ernesto", "1232322", "Male", (byte) 18, "Uber eats", "20916252", "Motorcycle", "1652gh", "Black motorcycle"));
                expressServicesEntities.add(new ExpressService("Antony Lopez", "2345675", "Male", (byte) 20, "Glovo", "209291892", "Car", "87628j", "Blue car"));

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
                            System.out.println("The orders that are ready to deliver are: ");

                            // List only orders that are "Ready to deliver
                            for (Orders orders : orders) {
                                if (orders.getState().equalsIgnoreCase("Ready to deliver")) {
                                    System.out.print(orders);
                                }
                            }

                            input.nextLine(); // Consume the newline character

                            System.out.println("Choose the number of the order :");
                            int numberOrder = input.nextInt();
                            input.nextLine(); // Consume the newline character

                            // Find the selected order
                            Orders selectedOrder = null;
                            for (Orders orders : orders) {
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
                                        selectedOrder.setExpressService(expressService);
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
                                boolean ordersAssignedToDriver = false;

                                System.out.println("The orders that are assigned to a driver are: ");
                                for (Orders orderAssigned : orders) {
                                    if (orderAssigned.getState().equalsIgnoreCase("Assigned to driver")) {
                                        ordersAssignedToDriver = true;
                                        System.out.println("1. Finish the order");
                                        System.out.println("2. Exit");
                                        if (input.hasNextInt()) {
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

                                                Orders selectedOrderMark = null;

                                                for (Orders orderFinish : orders) {
                                                    if (orderFinish.getOrderNumber() == numberOrderMark) {
                                                        selectedOrderMark = orderFinish;
                                                        break;
                                                    }
                                                }

                                                if (selectedOrderMark != null) {
                                                    selectedOrderMark.setState("Ready");
                                                    System.out.println("The order was marked as delivered successfully!");
                                                } else {
                                                    System.out.println("The order doesn't exist, please try again!");
                                                }
                                                break;

                                            case 2:
                                                System.out.println("You're going to exit the mark as delivered option");
                                                exitMark = true;
                                                break;

                                            default:
                                                System.out.println("Invalid option, please try again !");
                                                break;
                                        }
                                    }
                                }

                                if (!ordersAssignedToDriver) {
                                    System.out.println("There are no orders assigned to a driver");
                                    exitMark = true;
                                }

                            } while (!exitMark);
                            break;
                        case 5:
                            System.out.println("You're going to exit of the Express Service System");
                            break;
                        default:
                            System.out.println("Invalid option, please try again !");
                            break;
                    }
                } while (option != 5) ;
                break;
            case 4:
                System.out.println("Goodbye");
                break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        } while (optionMain != 4);
    }
}