import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import menu.Menu;
import menu.Product;
import order.OrderProducts;

// ESTA ES LA APP QUE DEBE MANDAR LAS COSAS AL SERVER
public class Cliente {
    public static Scanner input = new Scanner(System.in);
    public static void mandarInformacion() throws IOException, InputMismatchException {
        String servidorIP = "127.0.0.1";
        int puerto = 12345;

        try {
            Socket clienteSocket = new Socket(servidorIP, puerto);
            System.out.println("Conectado al servidor en " + servidorIP + ":" + puerto);

            // Configurar flujos de entrada y salida
            BufferedReader entrada = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
            PrintWriter salida = new PrintWriter(clienteSocket.getOutputStream(), true);

            ObjectOutputStream salidaObjeto = new ObjectOutputStream(clienteSocket.getOutputStream());

            // Mostramos el menu ya creado
            Menu menu = new Menu();
            menu.appendProduct(new Product("Producto 1", "Este es el producto 1", 10.0, "Almuerzos", 10));
            menu.appendProduct(new Product("Producto 2", "Este es el producto 2", 20.0, "Almuerzos", 20));
            menu.appendProduct(new Product("Producto 3", "Este es el producto 3", 30.0, "Almuerzos", 30));

            // Enviar el primer string (identificador del pedido) al servidor
            ArrayList<OrderProducts> seleccionados = new ArrayList<>();; // GUARDAR LO ELEGIDO POR EL CLIENTE;

            System.out.println("Menú:");
            int i = 1; // Variable para efectos visuales
            for (Product product : menu.getProducts()) {
                System.out.println(i + ". " + product.toString());
                i++;
            }

            while (true) {
                System.out.print("Seleccione un platillo (ingrese el número 0 para terminar): ");
                int opcion = input.nextInt();

                if (opcion == 0) {
                    break; // El cliente ha terminado de seleccionar

                } else if (opcion >= 1 && opcion <= menu.getProducts().size()) { // Tamaño del menú de los platillos
                    Product productoElegido = menu.getProducts().get(opcion - 1);
                    System.out.println("¿Cuántos quiere?");
                    int cantidad = input.nextInt();

                    if (cantidad == 0) {
                        System.out.println("No se puede pedir 0 platillos");
                        continue;
                    } else {
                        // Crea un objeto OrderProducts basado en el Product y la cantidad seleccionados
                        OrderProducts platilloElegido = new OrderProducts(productoElegido, cantidad);
                        seleccionados.add(platilloElegido); // Agrega la opción a la lista de los platillos
                        System.out.println("Ha seleccionado: " + platilloElegido.getProduct().getName() + " - $" + platilloElegido.getProduct().getPrice());
                    }
                } else {
                    System.out.println("Opción no válida.");
                }
            }

            // Enviar la lista de productos seleccionados al servidor
            salidaObjeto.writeObject(seleccionados);

            try {
                String confirmacion;
                while ((confirmacion = entrada.readLine()) != null) {
                    System.out.println("Servidor: " + confirmacion);

                    if (confirmacion.contains("Opcion:")) {
                        input.nextLine();
                        System.out.print("Cliente: ");
                        int respuestaCliente = input.nextInt();
                        salida.println(Integer.toString(respuestaCliente));
                    }

                    if (confirmacion.contains("(HH:mm:ss):")){
                        System.out.print("Cliente: ");
                        input.nextLine();
                        String horaRecogidaInput = input.nextLine();
                        salida.println(horaRecogidaInput);
                    }

                    if (confirmacion.contains("express")) {
                        System.out.println("Servidor: " + entrada.readLine());
                        System.out.println("Cliente: ");
                        String frecuentClient = input.nextLine();
                        salida.println(frecuentClient);
                        String fullName = input.nextLine();
                        salida.println(fullName);
                        String cellPhoneNumber = input.nextLine();
                        salida.println(cellPhoneNumber);
                        break;
                    }
                }
            } catch (SocketException se) {
                // Maneja la excepción de Socket cerrado (cliente desconectado)
                System.out.println("Cliente desconectado.");
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Cerrar la conexión con el servidor
            clienteSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InputMismatchException ime) {
            System.out.println("Error ! Debe ingresar un numero entero !");;
        }
    }
    public static void main(String[] args) {

        try {
            System.out.println("PLEASE PRESS ENTER");
            input.nextLine(); // LIMPIAR BUFFER PARA NO ENTRAR EN UN CICLO INFINITO, PRESIONE ENTER Y CONTINUA
            mandarInformacion();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InputMismatchException ime) {
            System.out.println("Error: Debe ingresar un número entero");
        }

    }
}

