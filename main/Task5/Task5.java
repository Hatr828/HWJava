import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Task5 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter path to employee data file: ");
        Path dataPath = Path.of(scanner.nextLine().trim());

        List<Employee> employees = loadEmployees(dataPath);
        List<Employee> lastResult = new ArrayList<>(employees);

        boolean running = true;
        while (running) {
            printMenu();
            System.out.print("Choose option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> {
                    Employee emp = promptEmployee(scanner);
                    employees.add(emp);
                    System.out.println("Employee added.");
                }
                case "2" -> {
                    Employee emp = selectBySurname(scanner, employees, "edit");
                    if (emp != null) {
                        Employee updated = promptEmployee(scanner);
                        employees.set(employees.indexOf(emp), updated);
                        System.out.println("Employee updated.");
                    }
                }
                case "3" -> {
                    Employee emp = selectBySurname(scanner, employees, "delete");
                    if (emp != null) {
                        employees.remove(emp);
                        System.out.println("Employee deleted.");
                    }
                }
                case "4" -> {
                    System.out.print("Enter surname to search: ");
                    String surname = scanner.nextLine().trim();
                    List<Employee> found = employees.stream()
                            .filter(e -> e.getSurname().equals(surname))
                            .collect(Collectors.toList());
                    lastResult = new ArrayList<>(found);
                    printEmployees(found);
                }
                case "5" -> {
                    lastResult = new ArrayList<>(employees);
                    printEmployees(employees);
                }
                case "6" -> {
                    System.out.print("Enter starting letter: ");
                    String letterInput = scanner.nextLine().trim();
                    if (letterInput.isEmpty()) {
                        System.out.println("No letter provided.");
                        break;
                    }
                    char letter = letterInput.charAt(0);
                    List<Employee> filtered = employees.stream()
                            .filter(e -> !e.getSurname().isEmpty() && Character.toLowerCase(e.getSurname().charAt(0)) == Character.toLowerCase(letter))
                            .collect(Collectors.toList());
                    lastResult = new ArrayList<>(filtered);
                    printEmployees(filtered);
                }
                case "7" -> saveEmployees(dataPath, employees);
                case "8" -> {
                    if (lastResult.isEmpty()) {
                        System.out.println("Nothing to save. Run a search or list employees first.");
                        break;
                    }
                    System.out.print("Enter path to save report: ");
                    Path reportPath = Path.of(scanner.nextLine().trim());
                    saveReport(reportPath, lastResult);
                }
                case "9" -> {
                    saveEmployees(dataPath, employees);
                    running = false;
                }
                default -> System.out.println("Unknown option.");
            }
        }
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("1 - Add employee");
        System.out.println("2 - Edit employee by surname");
        System.out.println("3 - Delete employee by surname");
        System.out.println("4 - Search employee by surname");
        System.out.println("5 - Show all employees");
        System.out.println("6 - Show surnames starting with letter");
        System.out.println("7 - Save current list to data file");
        System.out.println("8 - Save last shown/search result to report file");
        System.out.println("9 - Save & Exit");
    }

    private static Employee promptEmployee(Scanner scanner) {
        System.out.print("Surname: ");
        String surname = scanner.nextLine().trim();
        System.out.print("Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Position: ");
        String position = scanner.nextLine().trim();
        System.out.print("Age: ");
        int age = readInt(scanner);
        return new Employee(surname, name, position, age);
    }

    private static Employee selectBySurname(Scanner scanner, List<Employee> employees, String action) {
        System.out.print("Enter surname to " + action + ": ");
        String surname = scanner.nextLine().trim();
        List<Employee> matched = employees.stream()
                .filter(e -> e.getSurname().equals(surname))
                .collect(Collectors.toList());
        if (matched.isEmpty()) {
            System.out.println("No employees found with that surname.");
            return null;
        }
        if (matched.size() == 1) {
            return matched.get(0);
        }
        System.out.println("Multiple employees found:");
        for (int i = 0; i < matched.size(); i++) {
            System.out.println((i + 1) + ". " + matched.get(i));
        }
        System.out.print("Choose number: ");
        int idx = readInt(scanner) - 1;
        if (idx < 0 || idx >= matched.size()) {
            System.out.println("Invalid selection.");
            return null;
        }
        return matched.get(idx);
    }

    private static int readInt(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Enter a valid integer: ");
            }
        }
    }

    private static void printEmployees(List<Employee> employees) {
        if (employees.isEmpty()) {
            System.out.println("No employees to show.");
            return;
        }
        System.out.println("Employees:");
        for (Employee e : employees) {
            System.out.println(" - " + e);
        }
    }

    private static List<Employee> loadEmployees(Path path) {
        if (!Files.exists(path)) {
            System.out.println("Data file not found, starting with empty list.");
            return new ArrayList<>();
        }
        try {
            List<String> lines = Files.readAllLines(path);
            List<Employee> employees = new ArrayList<>();
            for (String line : lines) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                Employee employee = Employee.fromLine(line);
                if (employee != null) {
                    employees.add(employee);
                }
            }
            System.out.println("Loaded " + employees.size() + " employees.");
            return employees;
        } catch (IOException e) {
            System.out.println("Failed to read data file: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private static void saveEmployees(Path path, List<Employee> employees) {
        List<String> lines = employees.stream()
                .map(Employee::toLine)
                .collect(Collectors.toList());
        try {
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            Files.write(path, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("Data saved to " + path);
        } catch (IOException e) {
            System.out.println("Failed to save data: " + e.getMessage());
        }
    }

    private static void saveReport(Path path, List<Employee> employees) {
        List<String> lines = employees.stream()
                .map(Employee::toString)
                .collect(Collectors.toList());
        try {
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            Files.write(path, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("Report saved to " + path);
        } catch (IOException e) {
            System.out.println("Failed to save report: " + e.getMessage());
        }
    }
}
