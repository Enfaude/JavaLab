package pwr.jmx;

public class Control implements ControlMBean {
	int threadCount;
	int cacheSize;
	
	public Control(int threads, int cacheSize) {
		this.threadCount = threads;
		this.cacheSize = cacheSize;
	}

	@Override
	public void setThreadCount(int threadCount) {
		if (threadCount < 0) {
			this.threadCount = 0;
		} else {
			this.threadCount = threadCount;
		}
	}

	@Override
	public int getThreadCount() {
		return threadCount;
	}

	@Override
	public void setCacheSize(int cacheSize) {
		if (cacheSize < 1) {
			this.cacheSize = 1;
		} else {
			this.cacheSize = cacheSize;
		}
	}

	@Override
	public int getCacheSize() {
		return cacheSize;
	}

	@Override
	public void printReport() {
		synchronized (System.out) {
            System.out.println("************   REPORT   ************");
			System.out.println("Number of threads: " + threadCount);
			System.out.println("Cache size: " + cacheSize);
			System.out.println("Used cache: " + Cache.getCacheEntriesCount() + "/" + cacheSize);
            System.out.println("Total cache calls: " + Cache.getAllCallsCount());
            System.out.println("Total failed cache calls: " + Cache.getFailedCallsCount());
            System.out.println("Total fails percentage: " + ((float)Cache.getFailedCallsCount()/(float)Cache.getAllCallsCount())*100);
            System.out.println("Cache calls since last report: " + Cache.getAllCallsSinceLastReportCount());
            System.out.println("Failed cache calls since last report: " + Cache.getFailedCallsSinceLastReportCount());
            System.out.println("Fails since last report percentage: " + ((float)Cache.getFailedCallsSinceLastReportCount()/(float)Cache.getAllCallsSinceLastReportCount())*100);
            System.out.println("************   END OF REPORT   ************");
			Cache.nullLastReportCounters();
		}
	}

}
