package pwr.register;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import pwr.common.IClientRegister;
import pwr.common.IServerRegister;
import pwr.common.ServerObject;

public class ServerRegisterImpl extends UnicastRemoteObject implements IServerRegister, IClientRegister{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4503936846432529661L;
	List<ServerObject> activeServers = new ArrayList<>();
	
	protected ServerRegisterImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public List<ServerObject> getServers() {
		System.out.println("Sending current server list to client");
		return activeServers;
	}


	@Override
	public boolean registerServer(ServerObject server) {
		System.out.println("Registry have an incoming attempt to register made by " + server.getName());
		if (activeServers.contains(server)) {
			System.out.println("There already is " + server.getName() + " in registry");
			return false;
		} else {
			activeServers.add(server);
			System.out.println(server.getName() + " registered successfully");
			return true;
		}
	}

	@Override
	public boolean unregisterServer(ServerObject server) {
		System.out.println("Registry attempting to unregister " + server.getName());
		if (activeServers.contains(server)) {
			activeServers.remove(server);
			System.out.println(server.getName() + " unregistered successfully");
			return true;
		} else {
			System.out.println("There is no " + server.getName() + " in registry");
			return false;
		}
	}

}
