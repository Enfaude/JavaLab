package pwr.java;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

public class Maintainer {
    ArrayList foundClasses;
    Cache cache = App.cacheData;

    public Maintainer() throws ClassNotFoundException, IOException {
        foundClasses = findClasses();
    }

    private int reportsCount = 0;

    private synchronized void incrementReportsCount() {
        reportsCount++;
    }

    public int getReportsCount() {
        return reportsCount;
    }

    public synchronized void report() {
        synchronized (System.out) {
            System.out.println("**********   REPORT   **********");
            System.out.println("Total cache calls: " + cache.getAllCallsCount());
            System.out.println("Total failed cache calls: " + cache.getFailedCallsCount());
            System.out.println("Total fails percentage: " + ((float)cache.getFailedCallsCount()/(float)cache.getAllCallsCount())*100);
            System.out.println("Cache calls since last report: " + cache.getAllCallsSinceLastReportCount());
            System.out.println("Failed cache calls since last report: " + cache.getFailedCallsSinceLastReportCount());
            System.out.println("Fails since last report percentage: " + ((float)cache.getFailedCallsSinceLastReportCount()/(float)cache.getAllCallsSinceLastReportCount())*100);
            System.out.println("**********   END OF REPORT   **********");
        }
        cache.nullLastReportCounters();
        incrementReportsCount();
    }

    public ArrayList getFoundClasses() {
        return foundClasses;
    }

    public synchronized void printAlgorithmsInfo() {
        System.out.println("Found algorithms: ");
        for (Object found : foundClasses) {
            Class c = ((Class) found);
            System.out.println(c.getName());
        }
    }

    public synchronized ArrayList findClasses() throws ClassNotFoundException, IOException {
        File classesDir = new File(".\\classes");
        File packageDir = new File(".\\classes\\pwr\\java");
        URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{classesDir.toURI().toURL()});

        String packageName = "pwr.java";
        ArrayList classes = new ArrayList();
        if (!packageDir.exists()) {
            return classes;
        }

        for (File file : packageDir.listFiles()) {
            if(file.getName().endsWith("Sort.class")) {
                Class c = urlClassLoader.loadClass(packageName + "." + file.getName().substring(0, file.getName().length() - 6));
                classes.add(c);
            }
        }

        urlClassLoader.close();

//        System.out.println("Found sorting classes:");
//        for (Object c : classes) {
//            System.out.println(c.toString());
//        }

        return classes;
    }
}
