import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import menu.Menu;
import menu.Product;
import order.OrderProducts;

// THIS IS THE APP THAT SHOULD SEND DATA TO THE SERVER
public class SocketClient {
    public static Scanner input = new Scanner(System.in);
    public static void sendInformation() throws IOException, InputMismatchException {
        String serverIP = "127.0.0.1";
        int port = 12345;

        try {
            Socket clientSocket = new Socket(serverIP, port);
            System.out.println("Connected to the server at " + serverIP + ":" + port);

            // Set up input and output streams
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter outputWriter = new PrintWriter(clientSocket.getOutputStream(), true);

            ObjectOutputStream objectOutput = new ObjectOutputStream(clientSocket.getOutputStream());

            // Display the previously created menu
            Menu menu = new Menu();
            menu.appendProduct(new Product("Product 1", "This is product 1", 10.0, "Lunch", 10));
            menu.appendProduct(new Product("Product 2", "This is product 2", 20.0, "Lunch", 20));
            menu.appendProduct(new Product("Product 3", "This is product 3", 30.0, "Lunch", 30));

            // Send the first string (order identifier) to the server
            ArrayList<OrderProducts> selectedProducts = new ArrayList<>(); // STORE WHAT THE CLIENT SELECTS;

            System.out.println("Menu:");
            int i = 1; // Variable for visual effects
            for (Product product : menu.getProducts()) {
                System.out.println(i + ". " + product.toString());
                i++;
            }

            while (true) {
                System.out.print("Select a dish (enter 0 to finish): ");
                int choice = input.nextInt();

                if (choice == 0) {
                    break; // The client has finished selecting

                } else if (choice >= 1 && choice <= menu.getProducts().size()) { // Size of the menu of dishes
                    Product selectedProduct = menu.getProducts().get(choice - 1);
                    System.out.println("How many do you want?");
                    int quantity = input.nextInt();

                    if (quantity == 0) {
                        System.out.println("You cannot order 0 dishes");
                        continue;
                    } else {
                        // Create an OrderProducts object based on the selected Product and quantity
                        OrderProducts selectedDish = new OrderProducts(selectedProduct, quantity);
                        selectedProducts.add(selectedDish); // Add the choice to the list of dishes
                        System.out.println("You have selected: " + selectedDish.getProduct().getName() + " - $" + selectedDish.getProduct().getPrice());
                    }
                } else {
                    System.out.println("Invalid option.");
                }
            }

            // Send the list of selected products to the server
            objectOutput.writeObject(selectedProducts);

            try {
                String confirmation;
                while ((confirmation = inputReader.readLine()) != null) {
                    System.out.println("Server: " + confirmation);

                    if (confirmation.contains("Option:")) {
                        input.nextLine();
                        System.out.print("Client: ");
                        int clientResponse = input.nextInt();
                        outputWriter.println(Integer.toString(clientResponse));
                    }

                    if (confirmation.contains("(HH:mm:ss):")){
                        System.out.print("Client: ");
                        input.nextLine();
                        String pickupTimeInput = input.nextLine();
                        outputWriter.println(pickupTimeInput);
                    }

                    if (confirmation.contains("express")) {
                        System.out.println("Server: " + inputReader.readLine());
                        System.out.println("Client: ");
                        input.nextLine();
                        String frequentClient = input.nextLine();
                        outputWriter.println(frequentClient);
                    }

                    if (confirmation.contains("Enter your full name:")) {
                        String clientResponse = input.nextLine();
                        outputWriter.println(clientResponse);
                    }

                    if (confirmation.contains("Enter your phone number:")) {
                        String clientResponse = input.nextLine();
                        outputWriter.println(clientResponse);
                    }

                    if (confirmation.contains("Enter your gender:")) {
                        String clientResponse = input.nextLine();
                        outputWriter.println(clientResponse);
                    }

                    if (confirmation.contains("Enter your age:")) {
                        String clientResponse = input.nextLine();
                        outputWriter.println(clientResponse);
                    }

                    if (confirmation.contains("Enter your province:")) {
                        String clientResponse = input.nextLine();
                        outputWriter.println(clientResponse);
                    }

                    if (confirmation.contains("Enter your canton:")) {
                        String clientResponse = input.nextLine();
                        outputWriter.println(clientResponse);
                    }

                    if (confirmation.contains("Enter your district:")) {
                        String clientResponse = input.nextLine();
                        outputWriter.println(clientResponse);
                    }

                    if (confirmation.contains("Enter your exact address:")) {
                        String clientResponse = input.nextLine();
                        outputWriter.println(clientResponse);
                    }

                    if (confirmation.contains("Description:")) {
                        String clientResponse = input.nextLine();
                        outputWriter.println(clientResponse);
                    }

                }
            } catch (SocketException se) {
                // Handle the closed socket exception (client disconnected)
                System.out.println("Client disconnected.");
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Close the connection with the server
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InputMismatchException ime) {
            System.out.println("Error! You must enter an integer!");
        }
    }
    public static void main(String[] args) {

        try {
            System.out.println("PLEASE PRESS ENTER");
            input.nextLine(); // CLEAR BUFFER TO AVOID ENTERING AN INFINITE LOOP, PRESS ENTER AND CONTINUE
            sendInformation();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InputMismatchException ime) {
            System.out.println("Error: You must enter an integer");
        }

    }
}
