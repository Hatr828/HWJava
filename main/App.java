import java.util.Map;
import java.util.Scanner;

public class App {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        RequestQueue();

        TaxDatabase db = new TaxDatabase();
        seedData(db);
        runMenu(db);
    }

    public static void RequestQueue() {
        RequestQueue queue = new RequestQueue();

        queue.enqueue("alice", Map.of("role", "admin", "region", "eu"), "qwe", 10);
        queue.enqueue("bob", Map.of("role", "analyst", "region", "us"), "www", 5);
        queue.enqueue("charlie", Map.of("role", "viewer", "region", "apac"), "qw", 3);
        queue.enqueue("diana", Map.of("role", "admin", "region", "us"), "we", 8);

        queue.processNext();
        queue.processNext();

        queue.close();
    }

    private static void runMenu(TaxDatabase db) {
        boolean running = true;
        while (running) {
            printMenu();
            String choice = SCANNER.nextLine().trim();
            switch (choice) {
                case "1" -> db.printAll();
                case "2" -> db.printByCode();
                case "3" -> db.printByFineType();
                case "4" -> db.printByCity();
                case "5" -> db.addPerson();
                case "6" -> db.addFine();
                case "7" -> db.deleteFine();
                case "8" -> db.replacePerson();
                case "9" -> running = false;
                default -> System.out.println("unknown.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("""
Choose an action:
1 - Print the entire database
2 - Print by identification code
3 - Print by fine type
4 - Print by city
5 - Add a new person
6 - Add a fine for an existing person
7 - Delete a fine
8 - Replace person information
9 - Exit
""");
        System.out.print("Your choice: ");
    }
    private static void seedData(TaxDatabase db) {
        PersonRecord first = new PersonRecord("1234567890", "Ivan Petrenko", "Kyiv");
        first.addFine(new Fine("Parking", 510, "Stopped in a prohibited area"));
        first.addFine(new Fine("Speeding", 680, "70 km/h in a 50 zone"));

        PersonRecord second = new PersonRecord("0987654321", "Maria Kovalenko", "Lviv");
        second.addFine(new Fine("Tax evasion", 1200, "Property tax"));

        PersonRecord third = new PersonRecord("5551113332", "Oleg Sidorenko", "Odesa");
        third.addFine(new Fine("Parking", 340, "Payment overdue"));

        db.addPerson(first);
        db.addPerson(second);
        db.addPerson(third);
    }
}
