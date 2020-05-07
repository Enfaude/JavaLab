package pwr.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import pwr.common.ISorting;

public class SortingImpl extends UnicastRemoteObject implements ISorting {


	/**
	 * 
	 */
	private static final long serialVersionUID = 5217911202786230442L;

	protected SortingImpl() throws RemoteException {
		super();
	}

	@Override
	public List<Integer> solve(List<Integer> input) throws RemoteException {
		List<Integer> result = new ArrayList<>();
		result.add(new Integer(4));
		result.add(new Integer(6));
		
		return result;
	}

}
