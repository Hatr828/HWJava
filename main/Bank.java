package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Bank {

    private final List<ATM> atms;

    public Bank(List<ATM> atms) {
        if (atms == null || atms.isEmpty()) {
            throw new IllegalArgumentException("ATM list must not be empty");
        }
        this.atms = new ArrayList<>(atms);
    }

    public static Bank initializeNetwork(int atmCount, Map<Integer, Integer> initialCash, int minWithdrawal, int maxNotesOut) {
        if (atmCount <= 0) {
            throw new IllegalArgumentException("ATM count must be positive");
        }
        List<ATM> created = new ArrayList<>(atmCount);
        for (int i = 0; i < atmCount; i++) {
            ATM atm = new ATM(minWithdrawal, maxNotesOut);
            atm.loadCash(initialCash);
            created.add(atm);
        }
        return new Bank(created);
    }

    public List<ATM> getAtms() {
        return Collections.unmodifiableList(atms);
    }

    public int totalCashInNetwork() {
        int sum = 0;
        for (ATM atm : atms) {
            sum += atm.totalCash();
        }
        return sum;
    }
}
