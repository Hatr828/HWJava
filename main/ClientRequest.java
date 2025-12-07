import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class ClientRequest {
    private static final AtomicLong COUNTER = new AtomicLong(0);

    final String user;
    final Map<String, String> attributes;
    final String payload;
    final int priority;
    final LocalDateTime submittedAt;
    final long order;
    LocalDateTime processedAt;

    ClientRequest(String user, Map<String, String> attributes, String payload, int priority) {
        this.user = user;
        this.attributes = new LinkedHashMap<>(attributes);
        this.payload = payload;
        this.priority = priority;
        this.submittedAt = LocalDateTime.now();
        this.order = COUNTER.getAndIncrement();
    }

    void markProcessed() {
        this.processedAt = LocalDateTime.now();
    }

    static int compareByPriority(ClientRequest left, ClientRequest right) {
        int priorityCompare = Integer.compare(right.priority, left.priority); 
        if (priorityCompare != 0) {
            return priorityCompare;
        }
        return Long.compare(left.order, right.order); 
    }
}
