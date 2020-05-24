package pwr.lab;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import pwr.java.IElement;
import pwr.java.IntElement;
import pwr.java.PigeonholeSort;
import pwr.java.QuickSort;
import pwr.java.SelectionSort;

public class CypherApp {
	static Scanner sc;
	static String key = "klucz kodowania";
	static List<IntElement> inputListInt;
	static List<IntElement> resultInt;
	static List<IElement> inputList;
	static List<IElement> result;
	
	public static void main(String[] args) {
		sc = new Scanner(System.in);
		menu();
	}
	
	public static void menu() {
		while (true) {
			System.out.println("Cypher Application");
			System.out.println("1. Encrypt file");
			System.out.println("2. Decrypt file");
			System.out.println("3. Generate data");
			System.out.println("4. Pigeonhole Sort");
			System.out.println("5. Quick Sort");
			System.out.println("6. Selection Sort");
			System.out.println("0. Close application");
			
			int choice = forceIntegerInput();
			switch (choice) {
				case 1: 
					Cypher.encrypt(key);
					break;
				case 2: 	
					Cypher.decrypt(key);
					break;
				case 3:
					inputList = generateSortingData();
					printList(inputList);
					inputListInt = generateSortingDataInt();
					printIntList(inputListInt);
					break;
				case 4:
					PigeonholeSort ps = new PigeonholeSort();
					resultInt = ps.solve(inputListInt);
					printIntList(resultInt);
					break;
				case 5:
					QuickSort qs = new QuickSort();
					result = qs.solveIElement(inputList);
					printList(result);
					break;
				case 6:
					SelectionSort ss = new SelectionSort();
					result = ss.solveIElement(inputList);
					printList(result);
					break;
				case 0:
					System.out.println("Closing application");
					sc.close();
					System.exit(0);
			}
		}
	}
	
	public static int forceIntegerInput() {
		int value;
		try { 
			value = sc.nextInt();
			sc.nextLine();
		} catch (InputMismatchException e) {
			System.out.println("Please enter correct numeric value");
			value = forceIntegerInput();
		}
		return value;
	}
	
    private static List<IntElement> generateSortingDataInt() {
    	Random rand = new Random();
        int size = 20;
        List<IntElement> sortingData = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            IntElement el = new IntElement("IntElement " + i, rand.nextInt(1000));
            sortingData.add(el);
        }
        return sortingData;
    }
    
    private static List<IElement> generateSortingData() {
    	Random rand = new Random();
        int size = 20;
        List<IElement> sortingData = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            IElement el = new IntElement("IElement " + i, rand.nextInt(1000));
            sortingData.add(el);
        }
        return sortingData;
    }
    
    public static void printIntList(List<IntElement> input) {
        System.out.println("IntElement List:");
        for (IElement element : input) {
            StringBuilder sb = new StringBuilder();
            sb.append("Name: ").append(element.getName()).append(" | Value: ").append(element.getValue());
            System.out.println(sb);
        }
    }
    
    public static void printList(List<IElement> input) {
        System.out.println("IElement List:");
        for (IElement element : input) {
            StringBuilder sb = new StringBuilder();
            sb.append("Name: ").append(element.getName()).append(" | Value: ").append(element.getValue());
            System.out.println(sb);
        }
    }
}
