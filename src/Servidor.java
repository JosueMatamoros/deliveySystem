import java.io.*;
import java.net.*;
import java.util.ArrayList;

import menu.Product;

// ESTE ES EL RESTAURANTE

public class Servidor {
    public static void main(String[] args) {
        int puerto = 12345;

        try {
            ServerSocket servidorSocket = new ServerSocket(puerto);
            System.out.println("Servidor esperando conexiones en el puerto " + puerto);

            while (true) {
                Socket clienteSocket = servidorSocket.accept();
                System.out.println("Cliente conectado desde " + clienteSocket.getInetAddress());

                // Configurar flujos de entrada y salida
                ObjectInputStream entradaObjeto = new ObjectInputStream(clienteSocket.getInputStream());

                // Recibir la lista de productos seleccionados desde el cliente
                ArrayList<Product> productosSeleccionados = (ArrayList<Product>) entradaObjeto.readObject(); //PUEDE HABER UN ERROR POR ACA

                // Procesar los productos seleccionados
                System.out.println("Productos seleccionados por el cliente:");
                for (Product producto : productosSeleccionados) {
                    System.out.println("Nombre: " + producto.getName() + ", Precio: " + producto.getPrice());
                    // Realiza cualquier otra lógica de procesamiento que necesites aquí
                }

                // Enviar una confirmación al cliente
                PrintWriter confirmacion = new PrintWriter(clienteSocket.getOutputStream(), true);
                confirmacion.println("Pedido recibido con éxito");

                // Cerrar la conexión con el cliente
                clienteSocket.close();

                // Cerrar la conexion con el servidor
                servidorSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
