import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

public class ProcessedRequest {
    final String user;
    final Map<String, String> attributes;
    final String payload;
    final int priority;
    final LocalDateTime submittedAt;
    final LocalDateTime processedAt;

    private ProcessedRequest(String user, Map<String, String> attributes, String payload, int priority, LocalDateTime submittedAt, LocalDateTime processedAt) {
        this.user = user;
        this.attributes = attributes;
        this.payload = payload;
        this.priority = priority;
        this.submittedAt = submittedAt;
        this.processedAt = processedAt;
    }

    static ProcessedRequest from(ClientRequest request) {
        return new ProcessedRequest(
                request.user,
                new LinkedHashMap<>(request.attributes),
                request.payload,
                request.priority,
                request.submittedAt,
                request.processedAt
        );
    }
}
