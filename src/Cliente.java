import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
import menu.Menu;
import menu.Product;

// ESTA ES LA APP QUE DEBE MANDAR LAS COSAS AL SERVER
public class Cliente {
    public static Scanner input = new Scanner(System.in);
    public static void main(String[] args) {
        String servidorIP = "127.0.0.1";
        int puerto = 12345;

        try {
            Socket clienteSocket = new Socket(servidorIP, puerto);
            System.out.println("Conectado al servidor en " + servidorIP + ":" + puerto);

            // Configurar flujos de entrada y salida
            BufferedReader entrada = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
            ObjectOutputStream salidaObjeto = new ObjectOutputStream(clienteSocket.getOutputStream());

            // Mostramos el menu ya creado
            Menu menu = new Menu();
            menu.appendProduct(new Product("Producto 1", "Este es el producto 1", 10.0, "Almuerzos", 10));
            menu.appendProduct(new Product("Producto 2", "Este es el producto 2", 20.0, "Almuerzos", 20));
            menu.appendProduct(new Product("Producto 3", "Este es el producto 3", 30.0, "Almuerzos", 30));

            // Enviar el primer string (identificador del pedido) al servidor
            ArrayList<Product> seleccionados = new ArrayList<>(); // GUARDAR LO ELEGIDO POR EL CLIENTE;

            System.out.println("Menú:");
            int i = 1; // Variable para efectos visuales
            for (Product product : menu.getProducts()) {
                System.out.println(i + ". Name:" + product.getName() + ", category: " + product.getCategory() + " - $" + product.getPrice());
                i++;
            }

            while (true) {
                System.out.print("Seleccione un platillo (ingrese el número 0 para terminar): ");
                int opcion = input.nextInt();

                if (opcion == 0) {
                    break; // El cliente ha terminado de seleccionar

                } else if (opcion >= 1 && opcion <= menu.getProducts().size()) { // Tamaño del menu de los platillos

                    Product platilloElegido = menu.getProducts().get(opcion - 1);
                    seleccionados.add(platilloElegido); // Se agrega la opcion a la lista de los platillos
                    System.out.println("Ha seleccionado: " + platilloElegido.getName() + " - $" + platilloElegido.getPrice());
                } else {
                    System.out.println("Opción no válida.");
                }
            }

            // Enviar la lista de productos seleccionados al servidor
            salidaObjeto.writeObject(seleccionados);

            String confirmacion = entrada.readLine();
            System.out.println("Servidor: " + confirmacion);

            // Cerrar la conexión con el servidor
            clienteSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

