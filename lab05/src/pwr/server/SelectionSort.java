package pwr.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pwr.common.ISorting;

public class SelectionSort extends UnicastRemoteObject implements ISorting {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5852345919505041740L;

	protected SelectionSort() throws RemoteException {
		super();
	}

	@Override
	public List<Integer> solve(List<Integer> input) throws RemoteException {
		List<Integer> result = new ArrayList<>();
		Integer[] inputData = input.toArray(new Integer[input.size()]);

        for (int i = 0; i < inputData.length - 1; i++) {
            int minIndex = i;
            for (int j = i; j < inputData.length; j++) {
                if (inputData[j] < inputData[minIndex]) {
                    minIndex = j;
                }
            }
            swapElements(inputData, minIndex, i);
        }

        Collections.addAll(result, inputData);
        return result;
	}
	
    public static void swapElements(Integer[] data, int indexA, int indexB) {
    	Integer temp = data[indexA];
        data[indexA] = data[indexB];
        data[indexB] = temp;
    }

}
