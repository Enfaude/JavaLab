package pwr.jmx;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pwr.java.IElement;
import pwr.java.IntElement;
import pwr.java.PigeonholeSort;
import pwr.java.QuickSort;
import pwr.java.SelectionSort;

public class SolverThread extends Thread {
    Random rand;
    int seed;
    List resultList = null;
    List sortingData = null;
    PigeonholeSort ps = new PigeonholeSort();
    QuickSort qs = new QuickSort();
    SelectionSort ss = new SelectionSort();
    boolean alive = true;

    
    @Override
    public void run() {
        while(alive) {
            rand = new Random();
            seed = rand.nextInt() % 50;
            rand.setSeed(seed);
            
//            System.out.println("Starting thread " + getThreadName());
            if (!Cache.isSeedAlreadyUsed(seed)) {
        		sortingData = generateSortingData();
                int algSelect = rand.nextInt() % 3;
                switch (algSelect) {
                	case 1:
                		resultList = ps.solve(sortingData);
                		break;
                	case 2:
                		resultList = qs.solveIElement(sortingData);
                	case 0:
                		resultList = ss.solveIElement(sortingData);
                }
                    Cache.addResult(seed, resultList);
                }
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public String getThreadName() {
        return "(seed: " + seed + ") ";
    }
    
    public static void printList(List<IntElement> input) {
    	synchronized (System.out) {
	        System.out.println("List:");
	        for (IntElement element : input) {
	            System.out.println("Name: "+ element.getName() + " | Value: " + element.getValue());
	        }
    	}
    }
    
    private List<IElement> generateSortingData() {
        int size = rand.nextInt(50) + 50;
        List<IElement> sortingData = new ArrayList<>();
//        System.out.println(getThreadName() + " generates data to sort");

        for (int i = 0; i < size; i++) {
        	IElement el = new IntElement("element " + i + " in thread " + getThreadName(), rand.nextInt(100));
            sortingData.add(el);
        }
        return sortingData;
    }
    
    public void prepareToDie() {
    	alive = false;
    }
}
