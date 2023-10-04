import menu.Discounts;
import menu.Menu;
import menu.Product;
import order.OrderProducts;
import order.Orders;
import person.Client;
import person.Employee;
import person.aggregated.Address;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;


public class SocketMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // CREATING THE RESTAURANT SERVER, IS "MAIN" SERVER;
        int port = 12345;
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server waiting for connections on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected since " + clientSocket.getInetAddress());

                // Set up input and output streams
                ObjectInputStream objectInput = new ObjectInputStream(clientSocket.getInputStream());
                ObjectOutputStream objectOutPut = new ObjectOutputStream(clientSocket.getOutputStream());
                BufferedReader inputBuffer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter confirmation = new PrintWriter(clientSocket.getOutputStream(), true);

                // Employee
                ArrayList<Employee> employees = new ArrayList<>();

                employees.add( new Employee("Luis Cubillo", "+506 8585 2107", "Male", (byte) 21, "Chef", 14, false));
                employees.add( new Employee("Ram Ortiz", "+506 6477 0084", "Male", (byte) 21, "Chef", 18, false)) ;
                employees.add( new Employee("Andres Esquivel", "+506 8547 7746", "Male", (byte) 21, "Chef", 14, false));
                employees.add( new Employee("Hector Caravaca", "+506 8333 9684", "Male", (byte) 21, "Chef", 30, false));

                // Product
                Product [] productos = {new Product("Coca-Cola", "Carbonated cola drink", 2.0, "Beverages", 50),
                        new Product("Cheeseburger", "Classic cheeseburger with Angus beef", 12.5, "Fast Food", 20),
                        new Product("Margherita Pizza", "Pizza with tomato sauce, mozzarella, and basil", 16.5, "Fast Food", 15),
                        new Product("Grilled Chicken", "Grilled chicken breast with vegetables", 20.0, "Main Course", 12),
                        new Product("Fruit Salad", "Fresh mixed fruit salad", 3.5, "Kids' Menu", 30),
                        new Product("Hot Dog", "Hot dog with various toppings", 10.5, "Fast Food", 25),
                        new Product("Spaghetti Bolognese", "Spaghetti with meat sauce", 12.5, "Main Course", 18),
                        new Product("Milk", "Glass of fresh milk", 1.5, "Beverages", 40),
                        new Product("Quesadilla", "Cheese quesadilla with guacamole", 14.0, "Fast Food", 20),
                        new Product("Chicken Nuggets", "Chicken nuggets with honey mustard sauce", 12.5, "Kids' Menu", 15),
                        new Product("Orange Juice", "Freshly squeezed orange juice", 5.0, "Beverages", 35),
                        new Product("Tomato Soup", "Tomato soup with croutons", 9.0, "Main Course", 22),
                        new Product("Apple Pie", "Classic apple pie", 9.0, "Desserts", 18),
                        new Product("French Fries", "French fries with ketchup", 7.0, "Fast Food", 28),
                        new Product("Lasagna", "Layered pasta with meat and cheese", 16.0, "Main Course", 20),
                        new Product("Chocolate Ice Cream", "Chocolate-flavored ice cream", 4.0, "Desserts", 25),
                        new Product("Chocolate Milkshake", "Chocolate milkshake with whipped cream", 15.0, "Beverages", 18),
                        new Product("Vanilla Pudding", "Vanilla pudding with cookies", 6.5, "Desserts", 30),
                        new Product("Apple Crumble", "Warm apple crumble with vanilla ice cream", 18.0, "Desserts", 12),
                        new Product("Mineral Water", "Bottle of mineral water", 1.0, "Beverages", 50),
                    };
                    // Discounts
                    Discounts [] discounts = {new Discounts("Beverages", 10),
                        new Discounts("Fast Food", 20),
                        new Discounts("Main Course", 15),
                        new Discounts("Kids' Menu", 5),
                        new Discounts("Desserts", 10),
                    };

                    // Send the menu to the client
                    Menu menu = new Menu();
                    for (Product product : productos) {
                        menu.appendProduct(product);
                    }
                    for (Discounts discount : discounts) {
                        menu.addDiscount(discount);
                    }

                    objectOutPut.writeObject(menu);

                    ArrayList<OrderProducts> selectedProducts = (ArrayList<OrderProducts>) objectInput.readObject();

                    for (OrderProducts product : selectedProducts) {
                        System.out.println("The client has selected: " + product);
                    }

                    confirmation.println("Received successfully!");

                    boolean finish = false;
                    do {
                        // Send a message to the client
                        confirmation.println("1. Dine-in 2. Takeout 3. Express Service 4. Exit Option: ");

                        // Read the client's response
                        String clientResponse = inputBuffer.readLine();
                        ArrayList<Orders> orders = new ArrayList<>();

                        if (clientResponse != null && !clientResponse.isEmpty()) {
                            try {
                                int option = Integer.parseInt(clientResponse);
                                switch (option) {
                                    case 1:
                                        confirmation.println("The client chose to dine-in\n");
                                        Orders newOrder = new Orders(selectedProducts);
                                        orders.add(newOrder);
                                        confirmation.println("List of orders:");
                                        for (Orders order : orders) {
                                            confirmation.println(order.toString());
                                        }
                                        confirmation.println(newOrder.toString());
                                        finish = true;
                                        break;
                                    case 2:
                                        confirmation.println("The client chose takeout");
                                        confirmation.println("What time will you pick up your order (HH:mm:ss): ");
                                        try {
                                            String pickupTimeInput = inputBuffer.readLine();

                                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                                            LocalTime pickupTime = LocalTime.parse(pickupTimeInput, formatter);
                                            Orders newOrderPickup = new Orders(selectedProducts, pickupTime);

                                            orders.add(newOrderPickup);

                                            // THIS SHOULD NOT BE SHOWN ON THE CLIENT SIDE, IT SHOULD GO TO THE RESTAURANT;
                                            confirmation.println("List of orders:");
                                            for (Orders order : orders) {
                                                confirmation.println(order.toString());
                                                System.out.println(order.toString());
                                            }

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
                                        confirmation.println("The client chose express service");
                                        confirmation.println("Are you a frequent customer? (y/n) ");
                                        String frequentCustomerResponse = inputBuffer.readLine();

                                        if (frequentCustomerResponse.equalsIgnoreCase("y")) {
                                            confirmation.println("The client is a frequent customer");
                                            confirmation.println("Enter your full name: ");
                                            String fullName = inputBuffer.readLine();
                                            confirmation.println("Enter your phone number: ");
                                            String phoneNumber = inputBuffer.readLine();
                                            confirmation.println("Enter your gender: ");
                                            String gender = inputBuffer.readLine();
                                            confirmation.println("Enter your age: ");
                                            Byte age = Byte.parseByte(inputBuffer.readLine());

                                            Client client = new Client(fullName, phoneNumber, gender, age, null);

                                            System.out.println("Full name client is: " + client.getFullName());

                                            Orders newOrderExpress = new Orders(selectedProducts, null, client);

                                            orders.add(newOrderExpress);

                                            confirmation.println("List of orders:");
                                            for (Orders order : orders) {
                                                confirmation.println(order.toString());
                                            }
                                            finish = true;
                                        } else if (frequentCustomerResponse.equalsIgnoreCase("n")) {
                                            confirmation.println("The client is not a frequent customer");

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
                                            confirmation.println("List of orders:");
                                            for (Orders order : orders) {
                                                confirmation.println(order.toString());
                                            }
                                            finish = true;
                                        } else {
                                            confirmation.println("The client sent an invalid response.");
                                            finish = false;
                                        }

                                        break;
                                    case 4:
                                        confirmation.println("The client chose to exit");
                                        break;
                                    default:
                                        confirmation.println("The client chose an invalid option");
                                        break;
                                }
                            } catch (NumberFormatException e) {
                                // Handle the error if 'clientResponse' is not a valid number
                                confirmation.println("The client sent an invalid option.");
                            }
                        } else {
                            // Handle the case where the response is empty or null
                            confirmation.println("The client sent an empty or null response.");
                        }

                    } while (finish == false);

                    // Address
                    person.aggregated.Address address = new person.aggregated.Address("Alajuela", "Penas Blancas", "San Ramon", "Chachagua", "In front of the church");
                    person.aggregated.Address address1 = new person.aggregated.Address("Alajuela", "Penas Blancas", "San Ramon", "Calle Pechuga", "Close to the school");
                    person.aggregated.Address address2 = new person.aggregated.Address("Alajuela", "San Carlos", "San Ramon", "El burrito", "Next to the park");

                    // First Client
                    ArrayList<person.aggregated.Address> addresses = new ArrayList<>();
                    addresses.add(address);
                    addresses.add(address1);
                    // Second Client
                    ArrayList<person.aggregated.Address> addresses1 = new ArrayList<>();
                    addresses1.add(address2);

                    // Clients
                    ArrayList<Client> clients = new ArrayList<>();
                    clients.add(new Client("Kenny Rofrigues","+506 7296 8552", "Male", (byte)21, addresses));
                    clients.add(new Client("Asdrubal Ulate", "+506 8850 9804","Male", (byte)21, addresses1));

                int opcion = 0;
                do {
                    // Administration System
                    System.out.println("Welcome to the Administration System");
                    System.out.println("1. Eddit Menu");
                    System.out.println("2. Eddit Clients");
                    System.out.println("3. Eddit Employees");
                    System.out.println("4. Orderes Administration");
                    System.out.println("5. Exit");
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
                                                ArrayList<person.aggregated.Address> clientAdresses = new ArrayList<>();
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
                                }while (opcion != 3);
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
                                                    System.out.print(" -> ");
                                                    System.out.print(employee.getSalary());
                                                    System.out.println(" ");
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
                                // Implementar administracion de ordenes
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
            }
        } catch (Exception e) {
            System.out.println("Warning");
        }

    }
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

}