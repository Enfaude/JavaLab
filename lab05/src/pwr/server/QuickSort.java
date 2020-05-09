package pwr.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pwr.common.ISorting;

public class QuickSort extends UnicastRemoteObject implements ISorting {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8663142308823975161L;

	protected QuickSort() throws RemoteException {
		super();
	}

	@Override
	public List<Integer> solve(List<Integer> input) throws RemoteException {
        List<Integer> result = new ArrayList<>();
        Integer[] inputData = input.toArray(new Integer[input.size()]);
        quicksort(inputData, 0, inputData.length - 1);

        Collections.addAll(result, inputData);
        return result;
	}
	
    private static void quicksort(Integer[] data, int begin, int end ) {
        if (begin < end) {
            int partitionIndex = divide(data, begin, end);
                quicksort(data, begin, partitionIndex - 1);
                quicksort(data, partitionIndex + 1, end);
        }
    }
    
    private static int divide(Integer[] data, int begin, int end) {
        int pivot = data[end];
        int divideIndex = begin;

        for (int i = begin; i < end; i++) {
            if (data[i] < pivot) {
                swapElements(data, divideIndex, i);
                divideIndex++;
            }
        }
        swapElements(data, divideIndex, end);
        return divideIndex;
    }
    
    public static void swapElements(Integer[] data, int indexA, int indexB) {
    	Integer temp = data[indexA];
        data[indexA] = data[indexB];
        data[indexB] = temp;
    }

}
