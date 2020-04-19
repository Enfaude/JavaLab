package pwr.java;

import java.util.HashMap;
import java.util.List;

public class Cache {
    HashMap<Long, List> data = new HashMap<>();
    int allCallsCount = 0;
    int allCallsSinceLastReportCount = 0;
    int failedCallsCount = 0;
    int failedCallsSinceLastReportCount = 0;

    public synchronized void addNewResult(long seed, List resultList) {
        if(!isSeedAlreadyUsed(seed)) {
            data.put(seed, resultList);
            incrementCallsCounter();
            System.out.println("Data with seed " + seed + " have been successfully added to cache");
        } else {
            System.out.println("Data with seed " + seed + " have not been added to cache because there already is an entry with the same seed");
            incrementFailedCallsCounter();
        }
    }

    public synchronized Boolean isSeedAlreadyUsed(long seed) {
        incrementCallsCounter();
        boolean isSeedUsed = data.containsKey(seed);
        if(!isSeedUsed) {
            incrementFailedCallsCounter();
        }
        return isSeedUsed;
    }

    public synchronized void incrementCallsCounter() {
        allCallsCount++;
        allCallsSinceLastReportCount++;
    }

    public synchronized void incrementFailedCallsCounter() {
        failedCallsCount++;
        failedCallsSinceLastReportCount++;
    }

    public synchronized void nullLastReportCounters() {
        allCallsSinceLastReportCount = 0;
        failedCallsSinceLastReportCount = 0;
    }

    public int getAllCallsCount() {
        return allCallsCount;
    }

    public int getAllCallsSinceLastReportCount() {
        return allCallsSinceLastReportCount;
    }

    public int getFailedCallsCount() {
        return failedCallsCount;
    }

    public int getFailedCallsSinceLastReportCount() {
        return failedCallsSinceLastReportCount;
    }
}
