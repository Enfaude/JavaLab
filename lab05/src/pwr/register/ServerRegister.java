package pwr.register;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import pwr.common.IServerRegister;

public class ServerRegister{
	
	int port = 8888;
	int maxTries = 3;
	static String registryName = "ServerRegistryService";
	static Registry registry;
	
	public ServerRegister() {
		int tryCount = 0;
		System.out.println("Creating new solving server registry");		
		while(tryCount < maxTries) {
			try {
				IServerRegister serverRegister = new ServerRegisterImpl();
				registry = LocateRegistry.createRegistry(port);
				registry.bind(registryName, serverRegister);
				System.out.println("Registry server '" + registryName + "' successfully opened on port " + port);
				break;
			} catch (RemoteException e) {
				tryCount++;
				System.out.println("Port " + port + " is already bound");
				port--;
			} catch (AlreadyBoundException e) {
				e.printStackTrace();
				break;
			}
		}
	}	
	
	public static void main(String[] args) {
		new ServerRegister();
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("Enter 1 to close registry server");
			if (sc.nextInt() == 1) {
				sc.close();
				try {
					registry.unbind(registryName);
					System.out.println("Registry server was unbound correctly");
					System.exit(0);
				} catch (RemoteException | NotBoundException e) {
					System.out.println("Registry server could not be unbound correctly");
					System.exit(-1);
				}
			}
		}
	}

}
