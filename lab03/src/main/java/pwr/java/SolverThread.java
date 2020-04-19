package pwr.java;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SolverThread extends Thread {
    Random rand;
    long seed;
    String name;
    Cache cache = App.cacheData;
    List resultList = new ArrayList();
    Maintainer maintainer = App.maintainer;

    SolverThread(String name) {
        rand = new Random();
        seed = rand.nextLong()%50;
        rand.setSeed(seed);
        this.name = name;
        System.out.println(name + "'s seed is: " + seed);
    }

    @Override
    public void run() {
        System.out.println("Running thread " + getThreadName());
        if(!cache.isSeedAlreadyUsed(seed)) {
            List algList = new ArrayList();
            try {
                algList = maintainer.findClasses();
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
            if (algList.size() > 0) {
                System.out.println(getThreadName() + " found sorting algorithms");
                List sortingData = generateSortingData();

                System.out.println(getThreadName() + " generated data:");
                printList(sortingData);

                Class c = (Class) algList.get(rand.nextInt(algList.size()));

                try {
                    System.out.println(getThreadName() + " begins sorting with: " + c.getSimpleName());
                    Method methodSolve = c.getDeclaredMethod("solveIElement", List.class);
                    resultList = (ArrayList) methodSolve.invoke(c.newInstance(), sortingData);
                    System.out.println(getThreadName() + " finished sorting");
                } catch (NoSuchMethodException e) {
                    try {
                        Method methodSolve = c.getDeclaredMethod("solve", List.class);
                        resultList = (ArrayList) methodSolve.invoke(c.newInstance(), sortingData);
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException ex) {
                        ex.printStackTrace();
                    }
                    System.out.println(getThreadName() + " finished sorting");
                } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                    e.printStackTrace();
                }
                printList(resultList);
                System.out.println(getThreadName() + " adds result to cache");
                cache.addNewResult(seed, resultList);
            } else {
                System.out.println(getThreadName() + " have not found sorting algorithms");
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println(this.name + "'s seed was already used to sort data");
        }

        generateReport();
        System.out.println("Closing thread " + this.name);
    }

    private List generateSortingData() {
        int size = rand.nextInt(100) + 1;
        List<IElement> sortingData = new ArrayList();
        System.out.println(getThreadName() + " generates data to sort");

        for (int i = 0; i < size; i++) {
            IntElement el = new IntElement("element " + i + " in thread " + getThreadName(), rand.nextInt(1000000));
            sortingData.add(el);
        }
        return sortingData;
    }

    public long getSeed() {
        return seed;
    }

    public String getThreadName() {
        return name + "(seed: " + getSeed() + ") ";
    }

    public static synchronized void printList(List<IElement> input) {
        System.out.println("List:");
        for (IElement element : input) {
            StringBuilder sb = new StringBuilder();
            sb.append("Name: ").append(element.getName()).append(" | Value: ").append(element.getValue());
            System.out.println(sb);
        }
    }

    public synchronized void generateReport() {
        //'randomly' generated report
        if (getSeed()%10 == 0) {
            maintainer.report();
        }
    }
}
