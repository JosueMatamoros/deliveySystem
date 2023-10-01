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

public class Servidor {
    public static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        int puerto = 12345;
        ServerSocket servidorSocket = null;

        try {
            servidorSocket = new ServerSocket(puerto);
            System.out.println("Servidor esperando conexiones en el puerto " + puerto);

            while (true) {
                Socket clienteSocket = servidorSocket.accept();
                System.out.println("Cliente conectado desde " + clienteSocket.getInetAddress());

                // Configurar flujos de entrada y salida
                ObjectInputStream entradaObjeto = new ObjectInputStream(clienteSocket.getInputStream());
                BufferedReader entrada = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
                PrintWriter confirmacion = new PrintWriter(clienteSocket.getOutputStream(), true);

                // Recibir la lista de productos seleccionados desde el cliente
                ArrayList<OrderProducts> productosSeleccionados = (ArrayList<OrderProducts>) entradaObjeto.readObject();

                // Procesar los productos seleccionados
                System.out.println("Productos seleccionados por el cliente:");

                for (OrderProducts order : productosSeleccionados) {
                    Product producto = order.getProduct();
                    int cantidad = order.getQuantity();

                    System.out.println("Nombre: " + producto.getName() + " - Precio: " + producto.getPrice() + " - Cantidad: " + cantidad);

                }

                confirmacion.println("Recibido con exito !");

                while (true) {
                    // Enviar un mensaje al cliente
                    confirmacion.println("1. Comer acá 2. Para llevar 3. Servicio Express 4. Irse Opcion: ");

                    // Leer la respuesta del cliente
                    String respuestaCliente = entrada.readLine();
                    ArrayList<Orders> orders = new ArrayList<>();

                    if (respuestaCliente != null && !respuestaCliente.isEmpty()) {
                        try {
                            int opcion = Integer.parseInt(respuestaCliente);
                            switch (opcion) {
                                case 1:
                                    confirmacion.println("El cliente eligió comer acá\n");
                                    Orders newOrder = new Orders(productosSeleccionados);
                                    orders.add(newOrder);
                                    confirmacion.println("Lista de pedidos:");
                                    for (Orders order : orders) {
                                        confirmacion.println(order.toString());
                                    }
                                    confirmacion.println(newOrder.toString());
                                    break;
                                case 2:
                                    confirmacion.println("El cliente eligió para llevar");
                                    confirmacion.println("A que hora pasa a recoger su pedido (HH:mm:ss): ");
                                    try {
                                        String horaRecogidaInput = entrada.readLine();

                                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                                        LocalTime horaRecogida = LocalTime.parse(horaRecogidaInput, formatter);
                                        Orders newOrderPickUp = new Orders(productosSeleccionados, horaRecogida);

                                        orders.add(newOrderPickUp);

                                        // ESTO NO DEBERIA DE MOSTRARSE EN LA PARTE DE CLIENTE, DEBERIA IR A RESTAURANTE;
                                        confirmacion.println("Lista de pedidos:");
                                        for (Orders order : orders) {
                                            confirmacion.println(order.toString());
                                        }

                                    } catch (DateTimeParseException e) {
                                        // Manejo de la excepción si el formato de hora es incorrecto
                                        confirmacion.println("Error: El formato de la hora es incorrecto. Debe ser HH:mm:ss.");
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } catch (Exception e) {
                                        // Manejo de cualquier otra excepción no especificada
                                        e.printStackTrace(); // Puedes personalizar cómo manejar este error
                                    }
                                    break;
                                case 3:
                                    confirmacion.println("El cliente eligió servicio express");
                                    confirmacion.println("Es cliente frecuente? (y,n) ");
                                    String respuestaClienteFrecuente = entrada.readLine();
                                    if (respuestaClienteFrecuente.equalsIgnoreCase("y")) {
                                        confirmacion.println("El cliente es frecuente");
                                        confirmacion.println("Digite su nombre completo: "); String nombreCompleto = entrada.readLine();
                                        confirmacion.println("Digite su número de teléfono: "); String telefono = entrada.readLine();
                                        confirmacion.println("Digite su género: "); String genero = entrada.readLine();
                                        confirmacion.println("Digite su edad: "); Byte edad = Byte.parseByte(entrada.readLine());

                                        Client client = new Client(nombreCompleto, telefono, genero, edad, null);

                                        Orders newOrderExpress = new Orders(productosSeleccionados, null, client);

                                        orders.add(newOrderExpress);

                                        confirmacion.println("Lista de pedidos:");
                                        for (Orders order : orders) {
                                            confirmacion.println(order.toString());
                                        }
                                    } else if (respuestaClienteFrecuente.equalsIgnoreCase("n")) {
                                        confirmacion.println("El cliente no es frecuente");

                                        confirmacion.println("El cliente es frecuente");
                                        confirmacion.println("Digite su nombre completo: "); String nombreCompleto = entrada.readLine();
                                        confirmacion.println("Digite su número de teléfono: "); String telefono = entrada.readLine();
                                        confirmacion.println("Digite su género: "); String genero = entrada.readLine();
                                        confirmacion.println("Digite su edad: "); Byte edad = Byte.parseByte(entrada.readLine());

                                        confirmacion.println("Ahora vamos a rellenar tu direccion: ");
                                        confirmacion.println("Digite su provincia: "); String provincia = entrada.readLine();
                                        confirmacion.println("Digite su canton: "); String canton = entrada.readLine();
                                        confirmacion.println("Digite su distrito: "); String distrito = entrada.readLine();
                                        confirmacion.println("Digite su direccion exacta: "); String exactAddress = entrada.readLine();
                                        confirmacion.println("Descripcion: "); String descriptionAddress = entrada.readLine();

                                        Address address = new Address(provincia, canton, distrito, exactAddress, descriptionAddress);
                                        ArrayList <Address> addresses = new ArrayList<>();
                                        addresses.add(address);

                                        Client client = new Client(nombreCompleto, telefono, genero, edad, addresses);
                                        Orders newOrderExpress = new Orders(productosSeleccionados, null, client);
                                        orders.add(newOrderExpress);
                                        confirmacion.println("Lista de pedidos:");
                                        for (Orders order : orders) {
                                            confirmacion.println(order.toString());
                                        }
                                    } else {
                                        confirmacion.println("El cliente envió una respuesta no válida.");
                                    }

                                    break;
                                case 4:
                                    confirmacion.println("El cliente eligio irse ");
                                    break;
                                default:
                                    confirmacion.println("El cliente eligió una opción no válida");
                                    break;
                            }
                        } catch (NumberFormatException e) {
                            // Maneja el error si 'respuestaCliente' no es un número válido
                            confirmacion.println("El cliente envió una opción no válida.");
                        }
                    } else {
                        // Maneja el caso en el que la respuesta esté vacía o sea nula
                        confirmacion.println("El cliente envió una respuesta vacía o nula.");
                    }

                    clienteSocket.close();
                }
            }
        } catch (IOException e) {
            System.out.println("El cliente ha perdido la conexión");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
        } finally {
            try {
                if (servidorSocket != null) {
                    servidorSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
