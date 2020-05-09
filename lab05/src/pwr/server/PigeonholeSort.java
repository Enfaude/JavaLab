package pwr.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import pwr.common.ISorting;

public class PigeonholeSort extends UnicastRemoteObject implements ISorting {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1097303468715559864L;

	protected PigeonholeSort() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Integer> solve(List<Integer> input) throws RemoteException {
		System.out.println("Pigeonhole sort starts to sort list of " + input.size() + " numbers");

        List<Integer> result = new ArrayList<>();
        int minIndex = getMinValue(input);
        int maxIndex = getMaxValue(input);
        int pigeonSize = maxIndex - minIndex + 1;
        ArrayList<Integer>[] pigeonholes = new ArrayList[pigeonSize];
        for (int i = 0; i < pigeonholes.length; i++) {
            pigeonholes[i] = new ArrayList<>();
        }
        for (Integer element : input) {
            pigeonholes[(int) element - minIndex].add(element);
        }
        for (ArrayList<Integer> list : pigeonholes) {
            result.addAll(list); 
        }

		System.out.println("Pigeonhole sort finished sorting " + input.size() + " numbers");
        return result;
	}
	
    public int getMinValue(List<Integer> input) {
        int min = Integer.MAX_VALUE;
        for (Integer element : input) {
            if (element < min) {
                min = element;
            }
        }
        return min;
    }
    
    public int getMaxValue(List<Integer> input) {
        int max = Integer.MIN_VALUE;
        for (Integer element : input) {
            if (element > max) {
                max = element;
            }
        }
        return max;
    }
	
	

}
