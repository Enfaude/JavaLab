package pwr.java;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;

public class Cache {
    HashMap<Long, SoftReference<ArrayList<IElement>>> data = new HashMap<>();
    int allCallsCount = 0;
    int allCallsSinceLastReportCount = 0;
    int failedCallsCount = 0;
    int failedCallsSinceLastReportCount = 0;

    public synchronized void addNewResult(long seed, SoftReference<ArrayList<IElement>> resultList) {
        boolean isSeedUsed = isSeedAlreadyUsed(seed);
        if (!isSeedUsed) {
            data.put(seed, resultList);
            incrementCallsCounter();
//            System.out.println("Data with seed " + seed + " have been successfully added to cache");
        } else if (data.get(seed).get() != null) {
//            System.out.println("Data with seed " + seed + " have not been added to cache because there already is a list with the same seed: ");
            SolverThread.printList(data.get(seed).get());
            incrementCallsCounter();
        } else if (data.get(seed).get() == null) {
            data.remove(seed);
//            System.out.println("Data with seed " + seed + " had already been added to cache, but have been cleared since then, so it will be readded");
            data.put(seed, resultList);
            incrementCallsCounter();
        }
    }

    public synchronized Boolean isSeedAlreadyUsed(long seed) {
        incrementCallsCounter();
        boolean isSeedUsed = data.containsKey(seed);
        if(isSeedUsed) {
            incrementFailedCallsCounter();
        }
        return isSeedUsed;
    }

    public synchronized void incrementCallsCounter() {
        allCallsCount++;
        allCallsSinceLastReportCount++;
    }

    public synchronized void incrementFailedCallsCounter() {
        incrementCallsCounter();
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
