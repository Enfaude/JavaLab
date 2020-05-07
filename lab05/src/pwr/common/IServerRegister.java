package pwr.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServerRegister extends Remote {
	public boolean registerServer(ServerObject server) throws RemoteException;
	public boolean unregisterServer(ServerObject server) throws RemoteException;
}
