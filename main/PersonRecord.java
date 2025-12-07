import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PersonRecord {
    private final String idCode;
    private String fullName;
    private String city;
    private final List<Fine> fines;

    public PersonRecord(String idCode, String fullName, String city) {
        this.idCode = idCode;
        this.fullName = fullName;
        this.city = city;
        this.fines = new ArrayList<>();
    }

    public String idCode() {
        return idCode;
    }

    public String fullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String city() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Fine> fines() {
        return Collections.unmodifiableList(fines);
    }

    public void addFine(Fine fine) {
        fines.add(fine);
    }

    public boolean removeFine(String fineId) {
        return fines.removeIf(fine -> fine.id().equalsIgnoreCase(fineId));
    }

    public void replaceFines(List<Fine> newFines) {
        fines.clear();
        fines.addAll(newFines);
    }
}
