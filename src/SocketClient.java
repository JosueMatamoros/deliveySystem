import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import menu.Discounts;
import menu.Menu;
import menu.Product;
import order.OrderProducts;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * The type Socket client.
 */
// THIS IS THE APP THAT SHOULD SEND DATA TO THE SERVER
public class SocketClient {
    /**
     * The constant input.
     */
    public static Scanner input = new Scanner(System.in);

    /**
     * Is valid time boolean.
     *
     * @param timeString the time string
     * @return the boolean
     */
    public static boolean isValidTime(String timeString) {

        String regex = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(timeString);

        return matcher.matches();
    }

    /**
     * Send information.
     *
     * @throws IOException            the io exception
     * @throws InputMismatchException the input mismatch exception
     * @throws ClassNotFoundException the class not found exception
     */
    public static void sendInformation() throws IOException, InputMismatchException, ClassNotFoundException {
        String serverIP = "127.0.0.1";
        int port = 12345;

        do try {
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

            boolean finished = false;
            do {
                try {
                    System.out.println("Select a product by number (or enter 0 to finish):");
                    int option = input.nextInt();

                    if (option == 0) {
                        finished = true;
                    } else if (option >= 1 && option <= menu.getProducts().size()) {
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
                        input.nextLine();
                    }
                } catch (Exception e) {
                    System.out.println("Invalid option. Please select a valid product number.");
                    input.nextLine();
                }
            } while (!finished);

            // Send the list of selected products to the server
            objectOutput.writeObject(selectedProducts);

            // Receive the order identifier from the server
            finished = false;
            do {
                try {
                    String confirmation;
                    while ((confirmation = inputReader.readLine()) != null) {
                        System.out.println("Server: " + confirmation);

                        if (confirmation.contains("Option:")) {
                            int clientResponse;
                            while (true) {
                                if (input.hasNextInt()) {
                                    System.out.print("Client: ");
                                    // If the input is an integer (int)
                                    clientResponse = input.nextInt();
                                    if (clientResponse >= 1 && clientResponse <= 3) {
                                        outputWriter.println(clientResponse);
                                        break; // Valid option, exit the loop
                                    } else {
                                        System.out.println("Invalid option. Please select a valid option (1-3).");
                                    }
                                } else {
                                    // If the input is not an integer (int)
                                    String invalidInput = input.next();
                                    System.out.println("Invalid input. Please enter a number (int).");
                                }
                            }
                        }

                        if (confirmation.contains("(HH:mm:ss):")) {
                            System.out.print("Client: ");
                            input.nextLine();
                            String pickupTimeInput = input.nextLine();
                            while (!isValidTime(pickupTimeInput)) {
                                System.out.println("Invalid time format. Please enter a valid time (HH:mm:ss):");
                                System.out.print("Client: ");
                                pickupTimeInput = input.nextLine();
                            }
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
                    System.out.println("Client disconnected.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } while (!finished);

            // Close the connection with the server
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InputMismatchException ime) {
            System.out.println("Error! You must enter an integer!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } while (true);
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws ClassNotFoundException the class not found exception
     * @throws ConnectException       the connect exception
     */
    public static void main(String[] args) throws ClassNotFoundException, ConnectException {

        while (true) try {
            System.out.println("Press enter to connect to the server.");
            String userInput = input.nextLine(); // CLEAR BUFFER TO AVOID ENTERING AN INFINITE LOOP, PRESS ENTER AND CONTINUE
            while(true) {
                if(userInput.isEmpty()){
                    break;
                }
                System.out.println("PLEASE PRESS ENTER");
                userInput = input.nextLine();
            }

            sendInformation();
        } catch (ConnectException e) {
            System.out.println("Connection refused. Please try again later.");
        } catch (IOException e) {
            System.err.println("Error de E/S: " + e.getMessage());
        }

    }
}
