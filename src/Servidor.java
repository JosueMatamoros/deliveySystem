import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

import menu.Product;

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
                ArrayList<Product> productosSeleccionados = (ArrayList<Product>) entradaObjeto.readObject();

                // Procesar los productos seleccionados
                System.out.println("Productos seleccionados por el cliente:");
                for (Product producto : productosSeleccionados) {
                    System.out.println("Nombre: " + producto.getName() + " - Precio: " + producto.getPrice() + " - cantidad: " + producto.getCantidad());
                }

                while (true) {
                    // Enviar un mensaje al cliente
                    confirmacion.println("1. Comer acá? 2. Para llevar 3. Servicio Express 4. No Opcion: ");

                    // Leer la respuesta del cliente
                    String respuestaCliente = entrada.readLine();

                    if (respuestaCliente != null && !respuestaCliente.isEmpty()) {
                        try {
                            int opcion = Integer.parseInt(respuestaCliente);
                            switch (opcion) {
                                case 1:
                                    confirmacion.println("El cliente eligió comer acá");
                                    break;
                                case 2:
                                    confirmacion.println("El cliente eligió para llevar");
                                    break;
                                case 3:
                                    confirmacion.println("El cliente eligió servicio express");
                                    break;
                                case 4:
                                    confirmacion.println("El cliente eligió no");
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
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
