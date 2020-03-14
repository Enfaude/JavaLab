package pwr.java;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void printIntList(List<IntElement> input) {
        System.out.println("List:");
        for (IElement element : input) {
            StringBuilder sb = new StringBuilder();
            sb.append("Name: ").append(element.getName()).append(" | Value: ").append(element.getValue());
            System.out.println(sb);
        }
    }

    public static void printList(List<IElement> input) {
        System.out.println("List:");
        for (IElement element : input) {
            StringBuilder sb = new StringBuilder();
            sb.append("Name: ").append(element.getName()).append(" | Value: ").append(element.getValue());
            System.out.println(sb);
        }
    }

    public static void main(String[] args) {
        IntElement intel1 = new IntElement("intel1", 16);
        IntElement intel2 = new IntElement("intel2", 11);
        IntElement intel3 = new IntElement("intel3", 11);
        IntElement intel4 = new IntElement("intel4", 18);
        IntElement intel5 = new IntElement("intel5", 1);
        IntElement intel6 = new IntElement("intel6", 1);

        FloatElement flel1 = new FloatElement("flel1", 16.1f);
        FloatElement flel2 = new FloatElement("flel2", 11.11f);
        FloatElement flel3 = new FloatElement("flel3", 13);
        FloatElement flel4 = new FloatElement("flel4", 18.42f);
        FloatElement flel5 = new FloatElement("flel5", 11.11f);
        FloatElement flel6 = new FloatElement("flel6", 1.0f);
        FloatElement flel7 = new FloatElement("flel7", 1);

        List<IntElement> inputIntList = Arrays.asList(intel1, intel2, intel3, intel4, intel5, intel6);
        List<IElement> inputFloatList = Arrays.asList(flel1, flel2, flel3, flel4, flel5, flel6, flel7);

        PigeonholeSort pigeonholeSort = new PigeonholeSort();
        List<IntElement> resultInt = pigeonholeSort.solve(inputIntList);
        QuickSort quickSort = new QuickSort();
        List<IElement> resultFloat = quickSort.solveIElement(inputFloatList);
        SelectionSort selectionSort = new SelectionSort();
        List<IElement> resultSelection = selectionSort.solveIElement(inputFloatList);
        printIntList(resultInt);
        printList(resultFloat);
        printList(resultSelection);
    }
}
