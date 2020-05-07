package pwr.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IClientRegister extends Remote{
	List<ServerObject> getServers() throws RemoteException;
}
