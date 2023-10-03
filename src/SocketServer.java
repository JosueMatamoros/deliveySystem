import java.io.*;
import java.net.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

import menu.Product;
import order.OrderProducts;
import order.Orders;
import person.Client;
import person.aggregated.Address;

public class SocketServer {
    public static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
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
                BufferedReader inputBuffer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter confirmation = new PrintWriter(clientSocket.getOutputStream(), true);

                // Receive the list of selected products from the client
                ArrayList<OrderProducts> selectedProducts = (ArrayList<OrderProducts>) objectInput.readObject();

                // Process the selected products
                System.out.println("Products selected by the client:");

                for (OrderProducts order : selectedProducts) {
                    Product product = order.getProduct();
                    int quantity = order.getQuantity();

                    System.out.println("Name: " + product.getName() + " - Price: " + product.getPrice() + " - Quantity: " + quantity);
                }

                confirmation.println("Received successfully!");

                while (true) {
                    // Send a message to the client
                    confirmation.println("1. Dine-in 2. Takeaway 3. Express Service 4. Leave Option: ");

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
                                    confirmation.println("Order list:");
                                    for (Orders order : orders) {
                                        confirmation.println(order.toString());
                                    }
                                    confirmation.println(newOrder.toString());
                                    break;
                                case 2:
                                    confirmation.println("The client chose takeaway");
                                    confirmation.println("What time will you pick up your order (HH:mm:ss): ");
                                    try {
                                        String pickupTimeInput = inputBuffer.readLine();

                                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                                        LocalTime pickupTime = LocalTime.parse(pickupTimeInput, formatter);
                                        Orders newOrderPickUp = new Orders(selectedProducts, pickupTime);

                                        orders.add(newOrderPickUp);

                                        // THIS SHOULD NOT BE SHOWN IN THE CLIENT PART, IT SHOULD GO TO THE RESTAURANT;
                                        confirmation.println("Order list:");
                                        for (Orders order : orders) {
                                            confirmation.println(order.toString());
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
                                    break;
                                case 3:
                                    confirmation.println("The client chose express service");
                                    confirmation.println("Are you a frequent customer? (y,n) ");
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

                                        Orders newOrderExpress = new Orders(selectedProducts, null, client);

                                        orders.add(newOrderExpress);

                                        confirmation.println("Order list:");
                                        for (Orders order : orders) {
                                            confirmation.println(order.toString());
                                        }
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

                                        confirmation.println("Now let's fill out your address: ");
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
                                        ArrayList <Address> addresses = new ArrayList<>();
                                        addresses.add(address);

                                        Client client = new Client(fullName, phoneNumber, gender, age, addresses);
                                        Orders newOrderExpress = new Orders(selectedProducts, null, client);
                                        orders.add(newOrderExpress);
                                        confirmation.println("Order list:");
                                        for (Orders order : orders) {
                                            confirmation.println(order.toString());
                                        }
                                    } else {
                                        confirmation.println("The client sent an invalid response.");
                                    }

                                    break;
                                case 4:
                                    confirmation.println("The client chose to leave ");
                                    break;
                                default:
                                    confirmation.println("The client chose an invalid option");
                                    break;
                            }
                        } catch (NumberFormatException e) {
                            // Handle the error if 'clientResponse' is not a valid number
                            confirmation.println("The client sent an invalid option.");
                        } catch (SocketException sc) {
                            // Handle the closed socket exception (client disconnected)
                            System.out.println("Client disconnected.");
                        }
                    } else {
                        // Handle the case where the response is empty or null
                        confirmation.println("The client sent an empty or null response.");
                    }

                    clientSocket.close();
                }
            }
        } catch (IOException e) {
            System.out.println("The client has lost the connection");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
