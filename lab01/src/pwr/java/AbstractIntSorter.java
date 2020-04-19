package pwr.java;

import java.util.List;

/**
 * Abstract class containing essential declarations for IntElements sorting
 */
public abstract class AbstractIntSorter {
    /**
     * Description of sorting algorithm
     */
    String description;
    /**
     * Information about stability of the algorithm
     */
    boolean isStable;
    /**
     * Information if algorithm is 'in place'
     */
    boolean isInSitu;

    /**
     * abstract solve method
     * @param input - list of IntElements to sort
     * @return sorted list of IntElements
     */
    public abstract List<IntElement> solve(List<IntElement> input);

    /**
     * description getter
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * isStable getter
     * @return is algorithm stable
     */
    public Boolean isStable() {
        return isStable;
    }

    /**
     * isInSitu getter
     * @return is algorithm inSitu
     */
    public Boolean isInSitu() {
        return isInSitu;
    }

    /**
     * Finds maximum value among the IntElements
     * @param input - list of IntElements to find the highest value
     * @return the highest value in provided list
     */
    public int getMaxValue(List<IntElement> input) {
        int max = Integer.MIN_VALUE;
        for (IntElement element : input) {
            if (element.getValue() > max) {
                max = (int) element.getValue();
            }
        }
        return max;
    }

    /**
     * Finds minimum value among the IntElements
     * @param input - list of IntElements to find the lowest value
     * @return the lowest value in provided list
     */
    public int getMinValue(List<IntElement> input) {
        int min = Integer.MAX_VALUE;
        for (IntElement element : input) {
            if (element.getValue() < min) {
                min = (int) element.getValue();
            }
        }
        return min;
    }

}
