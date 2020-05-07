package pwr.register;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import pwr.common.IServerRegister;

public class ServerRegister{
	
	int port = 8888;
	
	public ServerRegister() {
		int maxTries = 10;
		int tryCount = 0;
		System.out.println("Creating new solving server registry");		
		while(true) {
			try {
				IServerRegister serverRegister = new ServerRegisterImpl();
				Registry registry = LocateRegistry.createRegistry(port);
				registry.bind("ServerRegistryService", serverRegister);
				System.out.println("Registry server successfully opened on port " + port);
				break;
			} catch (RemoteException e) {
				if (tryCount < maxTries) {
					tryCount++;
					System.out.println("Port " + port + " is already bound, retrying with " + --port);	
				} else {
					e.printStackTrace();
					break;
				}
			} catch (AlreadyBoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public static void main(String[] args) {
		new ServerRegister();
	}

}
