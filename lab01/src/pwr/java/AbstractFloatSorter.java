package pwr.java;

import java.util.List;

/**
 * Abstract class containing essential declarations for IElements sorting
 */
public abstract class AbstractFloatSorter extends AbstractIntSorter {
    /**
     * Abstract solveIElement method to implement sorting algorithm
     *
     * @param input - list of IElements to sort
     * @return sorted list of IELements
     */
    abstract List<IElement> solveIElement(List<IElement> input);

    /**
     * Swaps location of IElements in array
     *
     * @param data   - array of IElements
     * @param indexA - index of first swapped element
     * @param indexB - index of second swapped element
     */
    public static void swapElements(IElement[] data, int indexA, int indexB) {
        IElement temp = data[indexA];
        data[indexA] = data[indexB];
        data[indexB] = temp;
    }

    /**
     * Finds IElement with the lowest value
     *
     * @param input - list of IElements to find the lowest valued element
     * @return the IElement holding the lowest value in provided list
     */
    IElement getMinElement(List<IElement> input) {
        IElement min = input.get(0);
        for (IElement element : input) {
            if (element.getValue() < min.getValue()) {
                min = element;
            }
        }
        return min;
    }

    /**
     * Finds IElement with the highest value
     *
     * @param input - list of IElements to find the highest valued element
     * @return the IElement holding the highest value in provided list
     */
    IElement getMaxElement(List<IElement> input) {
        IElement max = input.get(0);
        for (IElement element : input) {
            if (element.getValue() > max.getValue()) {
                max = element;
            }
        }
        return max;
    }

    /**
     * Nullifies unused method inherited from @{@link AbstractIntSorter}
     * @param input - list of IntElements to sort
     * @return
     */
    @Override
    List<IntElement> solve(List<IntElement> input) {
        return null;
    }

}
