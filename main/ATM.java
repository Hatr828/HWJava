package main;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class ATM {

    public static final int[] DENOMINATIONS = {500, 200, 100, 50, 20, 10, 5, 2, 1};

    private final Map<Integer, Integer> banknotes = new TreeMap<>(Collections.reverseOrder());
    private final int minWithdrawal;
    private final int maxNotesOut;

    public ATM(int minWithdrawal, int maxNotesOut) {
        this.minWithdrawal = minWithdrawal;
        this.maxNotesOut = maxNotesOut;
        for (int denomination : DENOMINATIONS) {
            banknotes.put(denomination, 0);
        }
    }

    public synchronized void loadCash(Map<Integer, Integer> bundle) {
        validateBundle(bundle);
        bundle.forEach((denom, count) -> banknotes.merge(denom, count, Integer::sum));
    }

    public synchronized void deposit(Map<Integer, Integer> bundle) {
        loadCash(bundle);
    }

    public synchronized Map<Integer, Integer> withdraw(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (amount < minWithdrawal) {
            throw new IllegalArgumentException("Amount is below minimal allowed: " + minWithdrawal);
        }
        if (amount > totalCash()) {
            throw new IllegalArgumentException("Not enough funds in ATM");
        }

        Map<Integer, Integer> plan = calculateDispensePlan(amount);
        int notesToDispense = plan.values().stream().mapToInt(Integer::intValue).sum();
        if (notesToDispense > maxNotesOut) {
            throw new IllegalArgumentException("Requested amount requires " + notesToDispense + " banknotes, exceeds limit " + maxNotesOut);
        }

        plan.forEach((denom, count) -> banknotes.merge(denom, -count, Integer::sum));
        return plan;
    }

    public synchronized int totalCash() {
        int sum = 0;
        for (Map.Entry<Integer, Integer> entry : banknotes.entrySet()) {
            sum += entry.getKey() * entry.getValue();
        }
        return sum;
    }

    public Map<Integer, Integer> snapshot() {
        return Collections.unmodifiableMap(new LinkedHashMap<>(banknotes));
    }

    private Map<Integer, Integer> calculateDispensePlan(int amount) {
        Map<Integer, Integer> plan = new LinkedHashMap<>();
        int remaining = amount;

        for (int denomination : DENOMINATIONS) {
            int available = banknotes.getOrDefault(denomination, 0);
            if (available <= 0) {
                continue;
            }
            int needed = Math.min(remaining / denomination, available);
            if (needed > 0) {
                plan.put(denomination, needed);
                remaining -= denomination * needed;
            }
            if (remaining == 0) {
                break;
            }
        }

        if (remaining != 0) {
            throw new IllegalArgumentException("Cannot dispense requested amount");
        }
        return plan;
    }

    private void validateBundle(Map<Integer, Integer> bundle)  {
        for (Map.Entry<Integer, Integer> entry : bundle.entrySet()) {
            int denom = entry.getKey();
            int count = entry.getValue();
            if (!banknotes.containsKey(denom)) {
                throw new IllegalArgumentException("Unsupported denom: " + denom);
            }
            if (count < 0) {
                throw new IllegalArgumentException("Negative banknote count " + denom);
            }
        }
    }
}
