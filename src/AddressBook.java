import java.io.*;
import java.util.*;

public class AddressBook {
    private HashMap<String, String> contacts = new HashMap<>();
    private final String FILENAME = "contacts.csv";

    public void load() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                contacts.put(parts[0], parts[1]);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo: " + e.getMessage());
        }
    }

    public void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
            for (Map.Entry<String, String> entry : contacts.entrySet()) {
                bw.write(entry.getKey() + "," + entry.getValue());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo: " + e.getMessage());
        }
    }

    public void list() {
        if (contacts.isEmpty()) {
            System.out.println("No hay contactos en la agenda.");
        } else {
            System.out.println("Contactos:");
            for (Map.Entry<String, String> entry : contacts.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        }
    }

    public void create(String number, String name) {
        contacts.put(number, name);
        System.out.println("Contacto agregado: " + number + " - " + name);
    }

    public void delete(String number) {
        if (contacts.remove(number) != null) {
            System.out.println("Contacto eliminado: " + number);
        } else {
            System.out.println("Contacto no encontrado.");
        }
    }

    public static void main(String[] args) {
        AddressBook book = new AddressBook();
        book.load();

        Scanner scanner = new Scanner(System.in);
        int option = 0;

        do {
            System.out.println("\nMenú:");
            System.out.println("1. Listar contactos");
            System.out.println("2. Agregar contacto");
            System.out.println("3. Eliminar contacto");
            System.out.println("4. Salir");
            System.out.print("Opción: ");

            try {
                option = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opción inválida.");
                continue;
            }

            switch (option) {
                case 1:
                    book.list();
                    break;
                case 2:
                    System.out.print("Número: ");
                    String number = scanner.nextLine();
                    System.out.print("Nombre: ");
                    String name = scanner.nextLine();
                    book.create(number, name);
                    break;
                case 3:
                    System.out.print("Número a eliminar: ");
                    number = scanner.nextLine();
                    book.delete(number);
                    break;
                case 4:
                    book.save();
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (option != 4);
    }
}