package pwr.jmx;

import java.util.ArrayList;
import java.util.List;

import pwr.java.IntElement;
import pwr.java.PigeonholeSort;

public class Cache {
	static int allCallsCount = 0;
	static int allCallsSinceLastReportCount = 0;
	static int failedCallsCount = 0;
	static int failedCallsSinceLastReportCount = 0;
	static List<CacheEntry> cacheMemory = new ArrayList<>();
	public static PigeonholeSort ps = new PigeonholeSort();
	
	public synchronized static void trimCache() {
		while (cacheMemory.size() > Main.beanControl.getCacheSize()) {
			cacheMemory.remove(0);
		}
	}
	
	public synchronized static void makeRoomForNewEntry() {
		if (cacheMemory.size() >= Main.beanControl.getCacheSize()) {
			cacheMemory.remove(0);
			trimCache();
		}
	}
	
    public synchronized static boolean isSeedAlreadyUsed(int seed) {
        for (CacheEntry entry : cacheMemory) {
        	if (seed == entry.getSeed()) {
        		incrementFailedCallsCounter();
        		return true;
        	}
        }
        incrementCallsCounter();
        return false;
    }
    
    public synchronized static boolean addResult(int seed, List<IntElement> result) {
		makeRoomForNewEntry();
    	if (!isSeedAlreadyUsed(seed)) {
    		CacheEntry entry = new CacheEntry(seed, result);
        	cacheMemory.add(entry);
        	incrementCallsCounter();
//    		System.out.println("Results with seed " + seed + " has been added to cache.");
        	return true;
    	} else {
    		System.out.println("Seed " + seed + " is already in cache.");
    	}
    	return false;    	
    }
    
    public synchronized static void incrementCallsCounter() {
        allCallsCount++;
        allCallsSinceLastReportCount++;
    }

    public synchronized static void incrementFailedCallsCounter() {
        incrementCallsCounter();
        failedCallsCount++;
        failedCallsSinceLastReportCount++;
    }
	
    public synchronized static int getAllCallsCount() {
        return allCallsCount;
    }

    public synchronized static int getAllCallsSinceLastReportCount() {
        return allCallsSinceLastReportCount;
    }

    public synchronized static int getFailedCallsCount() {
        return failedCallsCount;
    }

    public synchronized static int getFailedCallsSinceLastReportCount() {
        return failedCallsSinceLastReportCount;
    }
    
    public synchronized static void nullLastReportCounters() {
        allCallsSinceLastReportCount = 0;
        failedCallsSinceLastReportCount = 0;
    }
    
    public synchronized static int getCacheEntriesCount() {
    	return cacheMemory.size();
    }
}
