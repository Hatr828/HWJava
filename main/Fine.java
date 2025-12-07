import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicLong;

public class Fine {
    private static final AtomicLong COUNTER = new AtomicLong(1);

    private final String id;
    private final String type;
    private final double amount;
    private final String description;
    private final LocalDate issuedOn;

    public Fine(String type, double amount, String description) {
        this.id = "F-" + COUNTER.getAndIncrement();
        this.type = type;
        this.amount = amount;
        this.description = description == null ? "" : description;
        this.issuedOn = LocalDate.now();
    }

    public String id() {
        return id;
    }

    public String type() {
        return type;
    }

    public double amount() {
        return amount;
    }

    public String description() {
        return description;
    }

    public LocalDate issuedOn() {
        return issuedOn;
    }

    @Override
    public String toString() {
        return "%s | сума: %.2f | дата: %s | примітка: %s".formatted(type, amount, issuedOn, description);
    }
}
