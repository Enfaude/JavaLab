package pwr.register;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import pwr.common.IServerRegister;

public class ServerRegister{
	
	int port = 8888;
	int maxTries = 10;
	
	public ServerRegister() {
		int tryCount = 0;
		System.out.println("Creating new solving server registry");		
		while(tryCount < maxTries) {
			try {
				IServerRegister serverRegister = new ServerRegisterImpl();
				Registry registry = LocateRegistry.createRegistry(port);
				registry.bind("ServerRegistryService", serverRegister);
				System.out.println("Registry server successfully opened on port " + port);
				break;
			} catch (RemoteException e) {
				tryCount++;
				System.out.println("Port " + port + " is already bound, retrying with " + --port);	
			} catch (AlreadyBoundException e) {
				e.printStackTrace();
				break;
			}
		}
	}
	
	
	public static void main(String[] args) {
		new ServerRegister();
	}

}
