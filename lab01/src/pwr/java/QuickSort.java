package pwr.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuickSort extends AbstractFloatSorter {
    QuickSort() {
        this.description = "QuickSort";
        this.isInSitu = true;
        this.isStable = false;
    }

    @Override
    List<IElement> solveIElement(List<IElement> input) {
        List<IElement> result = new ArrayList<>();
        IElement[] inputData = (IElement[]) input.toArray();
        quicksort(inputData, 0, inputData.length - 1);

        Collections.addAll(result, inputData);
        return result;
    }

    private static void quicksort(IElement[] data, int begin, int end ) {
        if (begin < end) {
            int partitionIndex = partition(data, begin, end);
                quicksort(data, begin, partitionIndex - 1);
                quicksort(data, partitionIndex + 1, end);
        }
    }

    private static int partition(IElement[] data, int begin, int end) {
        float pivot = data[end].getValue();
        int partitionIndex = begin;

        for (int i = begin; i < end; i++) {
            if (data[i].getValue() < pivot) {
                swapElements(data, partitionIndex, i);
                partitionIndex++;
            }
        }
        swapElements(data, partitionIndex, end);
        return partitionIndex;
    }

    @Override
    List<IntElement> solve(List<IntElement> input) {
        return null;
    }
}
