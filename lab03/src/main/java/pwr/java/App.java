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
    public static List foundClasses;

    static {
        try {
            maintainer = new Maintainer();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }


    public static void main( String[] args ) throws InterruptedException {

        Random rand = new Random();
        int threadsCount = rand.nextInt(50) + 1;
        List<SolverThread> threads = new ArrayList<>();
        for (int i = 0; i <threadsCount; i++) {
            SolverThread thread = new SolverThread("solver " + i);
            threads.add(thread);
            thread.start();
        }

        foundClasses = maintainer.getFoundClasses();
        maintainer.printAlgorithmsInfo();
        maintainer.report();

        for (SolverThread thread : threads) {
            thread.join();
        }

        maintainer.report();
    }
}
