package pwr.java;

import java.util.List;

public abstract class AbstractFloatSorter extends AbstractIntSorter {
    abstract List<IElement> solveIElement(List<IElement> input);

    public static void swapElements(IElement[] data, int indexA, int indexB) {
        IElement temp = data[indexA];
        data[indexA] = data[indexB];
        data[indexB] = temp;
    }

    IElement getMinElement(List<IElement> input) {
        IElement min = input.get(0);
        for (IElement element : input) {
            if (element.getValue() < min.getValue()) {
                min = element;
            }
        }
        return min;
    }

    IElement getMaxElement(List<IElement> input) {
        IElement max = input.get(0);
        for (IElement element : input) {
            if (element.getValue() > max.getValue()) {
                max = element;
            }
        }
        return max;
    }
}
