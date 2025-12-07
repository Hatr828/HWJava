import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class TaxDatabase {
    private final Map<String, PersonRecord> people = new LinkedHashMap<>();
    private static final Scanner SCANNER = new Scanner(System.in);

    public boolean addPerson(PersonRecord person) {
        if (people.containsKey(person.idCode())) {
            return false;
        }
        people.put(person.idCode(), person);
        return true;
    }

    public PersonRecord findById(String idCode) {
        return people.get(idCode);
    }

    public List<PersonRecord> findByCity(String city) {
        String needle = city.toLowerCase(Locale.ROOT);
        return people.values().stream()
                .filter(person -> person.city().toLowerCase(Locale.ROOT).equals(needle))
                .sorted(Comparator.comparing(PersonRecord::idCode))
                .toList();
    }

    public List<PersonRecord> findByFineType(String type) {
        String needle = type.toLowerCase(Locale.ROOT);
        List<PersonRecord> result = new ArrayList<>();
        for (PersonRecord person : people.values()) {
            boolean match = person.fines().stream().anyMatch(fine -> fine.type().toLowerCase(Locale.ROOT).equals(needle));
            if (match) {
                result.add(person);
            }
        }
        result.sort(Comparator.comparing(PersonRecord::idCode));
        return result;
    }

    public boolean addFine(String idCode, Fine fine) {
        PersonRecord person = people.get(idCode);
        if (person == null) {
            return false;
        }
        person.addFine(fine);
        return true;
    }

    public boolean deleteFine(String idCode, String fineId) {
        PersonRecord person = people.get(idCode);
        if (person == null) {
            return false;
        }
        return person.removeFine(fineId);
    }

    public boolean replacePerson(String idCode, PersonRecord newData) {
        if (!people.containsKey(idCode) || !newData.idCode().equals(idCode)) {
            return false;
        }
        people.put(idCode, newData);
        return true;
    }

    public List<PersonRecord> allPeople() {
        return people.values().stream()
                .sorted(Comparator.comparing(PersonRecord::idCode))
                .toList();
    }

      public  void printAll() {
        List<PersonRecord> people = allPeople();
        if (people.isEmpty()) {
            System.out.println("Database is empty.");
            return;
        }
        for (PersonRecord person : people) {
            printPerson(person, System.out);
            System.out.println();
        }
    }

    public  void printByCode() {
        String id = promptNonEmpty("Enter identification code: ");
        PersonRecord person = findById(id);
        if (person == null) {
            System.out.println("Record not found.");
            return;
        }
        printPerson(person, System.out);
    }

    public  void printByFineType() {
        String type = promptNonEmpty("Enter fine type: ");
        List<PersonRecord> people = findByFineType(type);
        if (people.isEmpty()) {
            System.out.println("No records found.");
            return;
        }
        for (PersonRecord person : people) {
            printPerson(person, System.out);
            System.out.println();
        }
    }

    public  void printByCity() {
        String city = promptNonEmpty("Enter city: ");
        List<PersonRecord> people = findByCity(city);
        if (people.isEmpty()) {
            System.out.println("No records found.");
            return;
        }
        for (PersonRecord person : people) {
            printPerson(person, System.out);
            System.out.println();
        }
    }

    public  void addPerson() {
        String id = promptNonEmpty("New identification code: ");
        if (findById(id) != null) {
            System.out.println("This code already exists. Please use another one.");
            return;
        }
        String name = promptNonEmpty("Full name: ");
        String city = promptNonEmpty("City: ");
        PersonRecord person = new PersonRecord(id, name, city);
        addFinesLoop(person);
        addPerson(person);
        System.out.println("Record added.");
    }

    public  void addFine() {
        String id = promptNonEmpty("Person code to add fine for : ");
        PersonRecord person = findById(id);
        if (person == null) {
            System.out.println("Record not found.");
            return;
        }
        Fine fine = readFineFromUser();
        addFine(id, fine);
        System.out.println("Fine added.");
    }

    public  void deleteFine() {
        String id = promptNonEmpty("Person code: ");
        PersonRecord person = findById(id);
        if (person == null) {
            System.out.println("Record not found.");
            return;
        }
        if (person.fines().isEmpty()) {
            System.out.println("The user has no fines.");
            return;
        }
        System.out.println("Available fines:");
        for (Fine fine : person.fines()) {
            System.out.printf("%s -> %s%n", fine.id(), fine);
        }
        String fineId = promptNonEmpty("Enter fine ID to delete: ");
        boolean removed = deleteFine(id, fineId);
        if (removed) {
            System.out.println("Fine deleted.");
        } else {
            System.out.println("Failed to delete fine.");
        }
    }

    public  void replacePerson() {
        String id = promptNonEmpty("Record code to replace: ");
        PersonRecord existing = findById(id);
        if (existing == null) {
            System.out.println("Record not found.");
            return;
        }
        String name = promptNonEmpty("New full name: ");
        String city = promptNonEmpty("New city: ");
        PersonRecord updated = new PersonRecord(id, name, city);
        addFinesLoop(updated);
        if (replacePerson(id, updated)) {
            System.out.println("Information updated.");
        } else {
            System.out.println("Failed to update record.");
        }
    }

    public  void addFinesLoop(PersonRecord person) {
        while (true) {
            System.out.print("Add fine? (y/n): ");
            String answer = SCANNER.nextLine().trim().toLowerCase(Locale.ROOT);
            if (answer.startsWith("y") || answer.startsWith("т")) {
                person.addFine(readFineFromUser());
            } else {
                break;
            }
        }
    }

    public  Fine readFineFromUser() {
        String type = promptNonEmpty("Fine type: ");
        double amount = promptAmount("Fine amount: ");
        System.out.print("Short note (can be left empty): ");
        String note = SCANNER.nextLine().trim();
        return new Fine(type, amount, note);
    }

    public  String promptNonEmpty(String message) {
        while (true) {
            System.out.print(message);
            String value = SCANNER.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }   
            System.out.println("Value cannot be empty.");
        }
    }

    public  double promptAmount(String message) {
        while (true) {
            System.out.print(message);
            String text = SCANNER.nextLine().trim();
            try {
                return Double.parseDouble(text.replace(',', '.'));
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a number.");
            }
        }
    }


    public void printPerson(PersonRecord person, PrintStream out) {
        out.printf("code: %s | name: %s | city: %s%n", person.idCode(), person.fullName(), person.city());
        if (person.fines().isEmpty()) {
            out.println("  fines: none");
        } else {
            out.println("  fines:");
            for (Fine fine : person.fines()) {
                out.printf("    %s -> %s%n", fine.id(), fine);
            }
        }
    }
}
