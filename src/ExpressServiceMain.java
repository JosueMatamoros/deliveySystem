import order.Orders;
import person.ExpressService;

import java.util.ArrayList;
import java.util.Scanner;

public class ExpressServiceMain {
    public static Scanner input = new Scanner(System.in);

    public static void showMenuExpress() {
        System.out.println("1. Show orders");
        System.out.println("2. Assigment express service");
        System.out.println("3. Change order state");
        System.out.println("4. Exit");
    }

    public static void main(String[] args) {
        ArrayList<ExpressService> expressServicesEntities = new ArrayList<>();

        expressServicesEntities.add(new ExpressService("Enrnesto", "89574632", "M", (byte) 47, "Domicilios.com", "209670983", "Moto", "ABC123", "Moto color black"));
        expressServicesEntities.add(new ExpressService("Juan", "123456789", "M", (byte) 47, "Uber eats", "209670983", "Moto", "ABC123", "Moto color black"));
        expressServicesEntities.add(new ExpressService("Pedro", "123456789", "M", (byte) 47, "Glovo", "209670983", "Moto", "ABC123", "Moto color black"));

    }
}
