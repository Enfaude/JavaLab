package pwr.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implements quicksort algorithm
 */
public class QuickSort extends AbstractFloatSorter {
    /**
     * Class constructor providing information about the algorithm
     */
    public QuickSort() {
        this.description = "QuickSort";
        this.isInSitu = true;
        this.isStable = false;
    }

    /**
     * Solve method for quicksort sorting
     * @param input - list of IElements to sort
     * @return sorted list
     */
    @Override
    public List<IElement> solveIElement(List<IElement> input) {
        List<IElement> result = new ArrayList<>();
        IElement[] inputData = (IElement[]) input.toArray();
        quicksort(inputData, 0, inputData.length - 1);

        Collections.addAll(result, inputData);
        return result;
    }

    /**
     * Recurring method to sort array of IElements
     * @param data - The array of IElements to sort
     * @param begin - The first index to consider while sorting an array
     * @param end - The last index to consider while sorting an array
     */
    private static void quicksort(IElement[] data, int begin, int end ) {
        if (begin < end) {
            int partitionIndex = divide(data, begin, end);
                quicksort(data, begin, partitionIndex - 1);
                quicksort(data, partitionIndex + 1, end);
        }
    }

    /**
     * Method to find the index at which main array should be divided (Quicksort is the 'divide and conquer' type of algorithm)
     * Pivot is the last element among provided elements
     * @param data - The array of IElements to sort
     * @param begin - The first index to consider during dividing the array
     * @param end - The last index to consider during dividing the array
     * @return index at which the provided array should be divided in the next step
     */
    private static int divide(IElement[] data, int begin, int end) {
        float pivot = data[end].getValue();
        int divideIndex = begin;

        for (int i = begin; i < end; i++) {
            if (data[i].getValue() < pivot) {
                swapElements(data, divideIndex, i);
                divideIndex++;
            }
        }
        swapElements(data, divideIndex, end);
        return divideIndex;
    }

}
