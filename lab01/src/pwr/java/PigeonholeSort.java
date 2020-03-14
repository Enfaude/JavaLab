package pwr.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PigeonholeSort extends AbstractIntSorter {
    PigeonholeSort() {
        this.description = "PigeonholeSort";
        this.isInSitu = false;
        this.isStable = true;
    }

    @Override
    List<IntElement> solve(List<IntElement> input) {
        List<IntElement> result = new ArrayList<IntElement>();
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
