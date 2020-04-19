package pwr.java;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class Maintainer extends Thread {
    List foundClasses;
    Cache cache = App.cacheData;

    public Maintainer() throws ClassNotFoundException, IOException {
        foundClasses = findClasses();
    }

    public synchronized void report() {
        synchronized (System.out) {
            System.out.println("**********   REPORT   **********");
            System.out.println("Total cache calls: " + cache.getAllCallsCount());
            System.out.println("Total failed cache calls: " + cache.getFailedCallsCount());
            System.out.println("Total cache calls since last report: " + cache.getAllCallsSinceLastReportCount());
            System.out.println("Total failed cache calls since last report: " + cache.getFailedCallsSinceLastReportCount());
            System.out.println("**********   END OF REPORT   **********");
        }
        cache.nullLastReportCounters();
    }

    public List getFoundClasses() {
        return foundClasses;
    }

    public synchronized void printAlgorithmsInfo() {
        System.out.println("Found algorithms: ");
        for (Object found : foundClasses) {
            Class c = ((Class) found);
            System.out.println(c.getName());
        }
    }

    private synchronized List findClasses() throws ClassNotFoundException, IOException {
        File classesDir = new File(".\\classes");
        File packageDir = new File(".\\classes\\pwr\\java");
        URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{classesDir.toURI().toURL()});

        String packageName = "pwr.java";
        List classes = new ArrayList();
        if (!packageDir.exists()) {
            return classes;
        }

        System.out.println("Found classes:");
        for (File file : packageDir.listFiles()) {
            Class c = urlClassLoader.loadClass(packageName + "." + file.getName().substring(0, file.getName().length() - 6));
            if(file.getName().endsWith("Sort.class")) {
                classes.add(c);
            }
            System.out.println(c.toString());
        }

        urlClassLoader.close();

        System.out.println("Found sorting classes:");
        for (Object c : classes) {
            System.out.println(c.toString());
        }

        return classes;
    }
}
