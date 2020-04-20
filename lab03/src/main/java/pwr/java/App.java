package pwr.java;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class App extends Thread
{
    public Thread mainThread = currentThread();
    public static Cache cacheData = new Cache();
    public static Maintainer maintainer;

    static {
        try {
            maintainer = new Maintainer();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }


    public static void main( String[] args ) throws InterruptedException {
//        sleep(5000);
        Random rand = new Random();
        int threadsCount = rand.nextInt(10) + 20;
        List<SolverThread> threads = new ArrayList<>();
        for (int i = 0; i <threadsCount; i++) {
            SolverThread thread = new SolverThread("solver " + i);
            threads.add(thread);
            thread.start();
        }

        maintainer.report();
        maintainer.printAlgorithmsInfo();

        for (SolverThread thread : threads) {
            thread.join();
        }

        maintainer.report();
        System.out.println("Number of threads: " + threadsCount);
        System.out.println("Number of generated reports: " + maintainer.getReportsCount());
    }
}
