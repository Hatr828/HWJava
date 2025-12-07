import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class RequestQueue {
    private final PriorityQueue<ClientRequest> queue;
    private final List<ProcessedRequest> stats;

    RequestQueue() {
        this.queue = new PriorityQueue<>(ClientRequest::compareByPriority);
        this.stats = new ArrayList<>();
    }

    void enqueue(String user, Map<String, String> attributes, String payload, int priority) {
        queue.offer(new ClientRequest(user, attributes, payload, priority));
    }

    void processNext() {
        ClientRequest next = queue.poll();
        if (next == null) {
            return;
        }
        next.markProcessed();
        stats.add(ProcessedRequest.from(next));
        System.out.printf("enqueue done: %s: %s%n", next.user, next.payload);
    }

    void close() {
        while (!queue.isEmpty()) {
            processNext();
        }
        printStats();
    }

    private void printStats() {
        System.out.println("\nStats:");
        for (ProcessedRequest record : stats) {
            System.out.printf("\nuser: " + record.user + "\natt: " + record.attributes + "\nat: " + record.submittedAt + "\nat: " + record.processedAt + "\nreocrd: " + record.payload + "\npr: " + record.priority + "\n");
        }
    }
}
