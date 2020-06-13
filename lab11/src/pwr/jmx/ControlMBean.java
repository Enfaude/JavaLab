package pwr.jmx;

public interface ControlMBean {
	public void setThreadCount(int threadCount);
    public int getThreadCount();
    
    public void setCacheSize(int cacheSize);
    public int getCacheSize();
    
    public void printReport();
}