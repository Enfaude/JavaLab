package pwr.java;

import java.util.ArrayList;
import java.util.List;

/**
 * Class implementing pigeonhole sorting
 */
public class PigeonholeSort extends AbstractIntSorter {
    /**
     * Class constructor providing information about the algorithm
     */
    public PigeonholeSort() {
        this.description = "PigeonholeSort";
        this.isInSitu = false;
        this.isStable = true;
    }

    /**
     * Solve method for pigeonhole sorting
     * @param input - list of IntElements to sort
     * @return list sorted by pigeonhole algorithm
     */
    @Override
    public List<IntElement> solve(List<IntElement> input) {
        List<IntElement> result = new ArrayList<>();
        int minIndex = getMinValue(input);
        int maxIndex = getMaxValue(input);
        int pigeonSize = maxIndex - minIndex + 1;
        ArrayList<IntElement>[] pigeonholes = new ArrayList[pigeonSize];
        for (int i = 0; i < pigeonholes.length; i++) {
            pigeonholes[i] = new ArrayList<>();
        }
        for (IntElement element : input) {
            pigeonholes[(int) element.getValue()- minIndex].add(element);
        }
        for (ArrayList<IntElement> list : pigeonholes) {
            result.addAll(list);
        }

        return result;
    }
}
