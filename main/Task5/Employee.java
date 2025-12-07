public class Employee {
    private static final String DELIMITER = ";";
    private final String surname;
    private final String name;
    private final String position;
    private final int age;

    public Employee(String surname, String name, String position, int age) {
        this.surname = surname;
        this.name = name;
        this.position = position;
        this.age = age;
    }

    public String getSurname() {
        return surname;
    }

    public static Employee fromLine(String line) {
        String[] parts = line.split(DELIMITER);
        if (parts.length != 4) {
            System.out.println("Skipping malformed line: " + line);
            return null;
        }
        try {
            int age = Integer.parseInt(parts[3].trim());
            return new Employee(parts[0].trim(), parts[1].trim(), parts[2].trim(), age);
        } catch (NumberFormatException e) {
            System.out.println("Skipping line with bad age: " + line);
            return null;
        }
    }

    String toLine() {
        return String.join(DELIMITER, surname, name, position, String.valueOf(age));
    }

    @Override
    public String toString() {
        return surname + " " + name + ", " + position + ", age " + age;
    }
}
