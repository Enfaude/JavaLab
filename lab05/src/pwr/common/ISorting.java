package pwr.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ISorting extends Remote {
	public List<Integer> solve(List<Integer> input) throws RemoteException;
}
