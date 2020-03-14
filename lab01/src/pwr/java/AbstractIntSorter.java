package pwr.java;

import java.util.List;

public abstract class AbstractIntSorter {
    String description;
    boolean isStable;
    boolean isInSitu;

    abstract List<IntElement> solve(List<IntElement> input);

    String getDescription() {
        return description;
    }

    Boolean isStable() {
        return isStable;
    }

    Boolean isInSitu() {
        return isInSitu;
    }

    int getMaxValue(List<IntElement> input) {
        int max = Integer.MIN_VALUE;
        for (IntElement element : input) {
            if (element.getValue() > max) {
                max = (int) element.getValue();
            }
        }
        return max;
    }

    int getMinValue(List<IntElement> input) {
        int min = Integer.MAX_VALUE;
        for (IntElement element : input) {
            if (element.getValue() < min) {
                min = (int) element.getValue();
            }
        }
        return min;
    }

}
