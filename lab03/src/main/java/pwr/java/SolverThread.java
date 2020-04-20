package pwr.java;

import java.io.IOException;
import java.lang.ref.SoftReference;
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
    Maintainer maintainer = App.maintainer;
    SoftReference<ArrayList<IElement>> resultList = null;

    SolverThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        while(cache.data.size() < 99) {
            rand = new Random();
            seed = rand.nextLong() % 50;
            rand.setSeed(seed);
//            System.out.println(name + "'s seed is: " + seed);

//            System.out.println("Starting thread " + getThreadName());
            if (!cache.isSeedAlreadyUsed(seed) || cache.data.get(seed).get() == null) {
                cache.incrementCallsCounter();
                List algList = new ArrayList();
                try {
                    algList = maintainer.findClasses();
                } catch (ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                }
                if (algList.size() > 0) {
//                    System.out.println(getThreadName() + " found sorting algorithms");
                    List sortingData = generateSortingData();

//                    System.out.println(getThreadName() + " generated data:");
//                    printList(sortingData);

                    Class c = (Class) algList.get(rand.nextInt(algList.size()));

                    try {
//                        System.out.println(getThreadName() + " begins sorting with: " + c.getSimpleName());
                        Method methodSolve = c.getDeclaredMethod("solveIElement", List.class);
                        resultList = new SoftReference<>((ArrayList) methodSolve.invoke(c.newInstance(), sortingData));
//                        System.out.println(getThreadName() + " finished sorting");
                    } catch (NoSuchMethodException e) {
                        try {
                            Method methodSolve = c.getDeclaredMethod("solve", List.class);
                            resultList = new SoftReference<>((ArrayList) methodSolve.invoke(c.newInstance(), sortingData));
                        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException ex) {
                            ex.printStackTrace();
                        }
//                        System.out.println(getThreadName() + " finished sorting");
                    } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                        e.printStackTrace();
                    }
//                    printList(resultList.get());
//                    System.out.println(getThreadName() + " adds result to cache");
                    cache.addNewResult(seed, resultList);
                    algList.clear();
                    System.gc();
                } else {
//                    System.out.println(getThreadName() + " have not found sorting algorithms");
                    try {
                        sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                cache.incrementFailedCallsCounter();
//                System.out.println(getThreadName() + "'s seed was already used to sort data present in cache");
            }

            try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            generateReport();
        }
            System.out.println("Closing thread " + getThreadName());
    }

    private List generateSortingData() {
        int size = rand.nextInt(50) + 50;
        List<IElement> sortingData = new ArrayList();
//        System.out.println(getThreadName() + " generates data to sort");

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
//            System.out.println(sb);
        }
    }

    public synchronized void generateReport() {
        //'randomly' generated report
        if (getSeed()%5 == 0) {
            maintainer.report();
        }
    }
}
