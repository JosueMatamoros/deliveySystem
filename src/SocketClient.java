import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import menu.Discounts;
import menu.Menu;
import menu.Product;
import order.OrderProducts;

// THIS IS THE APP THAT SHOULD SEND DATA TO THE SERVER
public class SocketClient {
    public static Scanner input = new Scanner(System.in);
    public static void sendInformation() throws IOException, InputMismatchException, ClassNotFoundException {
        String serverIP = "127.0.0.1";
        int port = 12345;

        do {
            try {
                Socket clientSocket = new Socket(serverIP, port);
                System.out.println("Connected to the server at " + serverIP + ":" + port);

                // Set up input and output streams
                BufferedReader inputReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter outputWriter = new PrintWriter(clientSocket.getOutputStream(), true);

                ObjectOutputStream objectOutput = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream objectInput = new ObjectInputStream(clientSocket.getInputStream());

                // Receive the menu from the server
                Menu menu = (Menu) objectInput.readObject();

                // Show products
                System.out.println("Products:");
                int i = 0;
                for (Product product : menu.getProducts()) {
                    System.out.println((++i) + ". " + product);
                }

                // SHOW DISCOUNTS
                System.out.println("Discounts:");
                for (Discounts discount : menu.getDiscounts()) {
                    System.out.println(discount);
                }

                // Send the first string (order identifier) to the server
                ArrayList<OrderProducts> selectedProducts = new ArrayList<>(); // STORE WHAT THE CLIENT SELECTS;

                while (true) {
                    System.out.println("Select a product by number (or enter 0 to finish):");
                    int option = input.nextInt();

                    if (option == 0) {
                        break; // The user has finished selecting products
                    }

                    if (option >= 1 && option <= menu.getProducts().size()) {
                        Product selectedProduct = menu.getProducts().get(option - 1);

                        System.out.println("Desired quantity:");
                        int quantity = input.nextInt();
                        if (quantity <= 0) {
                            System.out.println("The quantity of selected products cannot be 0 or negative!");
                        } else {
                            // Add the product and quantity to selectedProducts
                            selectedProducts.add(new OrderProducts(selectedProduct, quantity));
                        }
                    } else {
                        System.out.println("Invalid option. Please select a valid product number.");
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
                            outputWriter.println(clientResponse);
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
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } while (true);
    }
    public static void main(String[] args) throws ClassNotFoundException {

        do {
            try {
                System.out.println("PLEASE PRESS ENTER");
                input.nextLine(); // CLEAR BUFFER TO AVOID ENTERING AN INFINITE LOOP, PRESS ENTER AND CONTINUE
                sendInformation();
            } catch (ConnectException e) {
                System.out.println("No se pudo conectar al servidor. Verifica la configuración del servidor.");
            } catch (IOException e) {
                System.err.println("Error de E/S: " + e.getMessage());
            }
        } while (true);


    }
}
