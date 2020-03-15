package pwr.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implements selection sort algorithm
 */
public class SelectionSort extends AbstractFloatSorter {
    /**
     * Class constructor providing information about the algorithm
     */
    SelectionSort() {
        this.description = "SelectionSort";
        this.isInSitu = true;
        this.isStable = false;
    }

    /**
     * Solve method for selection sorting
     * @param input - list of IElements to sort
     * @return sorted list of IElements
     */
    @Override
    List<IElement> solveIElement(List<IElement> input) {
        List<IElement> result = new ArrayList<>();
        IElement[] inputData = (IElement[]) input.toArray();

        for (int i = 0; i < inputData.length - 1; i++) {
            int minIndex = i;
            for (int j = i; j < inputData.length; j++) {
                if (inputData[j].getValue() < inputData[minIndex].getValue()) {
                    minIndex = j;
                }
            }
            swapElements(inputData, minIndex, i);
        }

        Collections.addAll(result, inputData);
        return result;
    }

}
