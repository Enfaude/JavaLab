package pwr.server;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import pwr.common.IServerRegister;
import pwr.common.ISorting;
import pwr.common.ServerObject;

public class SolverServer {
	boolean isRegistered;
	String serverName = "SolvingServerInstance";
	
	public SolverServer() {
		int registryPort = 8888;
		int serverPort = 8889;
		int maxTries = 10;
		int tryCount = 0;
		System.out.println("Creating new solving server");		
//		while(true) {
			try {
				ISorting sorter = new SortingImpl();	
				Registry registry = LocateRegistry.createRegistry(serverPort);
				registry.bind(serverName, sorter);
				System.out.println("Solving server successfully opened on port " + serverPort);
				
				ServerObject server = new ServerObject(serverName, serverPort, "desc");
				
				System.out.println("Solving server is looking for serverlist");		
				Registry serverRegistryService = LocateRegistry.getRegistry("localhost", registryPort);
				IServerRegister serverRegister = (IServerRegister) serverRegistryService.lookup("ServerRegistryService");
				System.out.println("Solving server found serverlist, now trying to register");		
				isRegistered = serverRegister.registerServer(server);
				
//				break;
			} catch (AlreadyBoundException e) {
				e.printStackTrace();
//				break;
			} catch (RemoteException e) {
//				if (tryCount < maxTries) {
//					tryCount++;
//					System.out.println("Port " + serverPort + " is already bound, retrying with " + ++serverPort);	
//				} else {
					e.printStackTrace();
//					break;
//				}
			} catch (NotBoundException e) {
				System.out.println("Solving server could not find the server registry");
				e.printStackTrace();
//				break;
			}
//		}
		
		if (isRegistered) {
			System.out.println(serverName + " has been successfully registered and is ready to roll");
		} else {
			System.out.println(serverName + " could not be registered.");
		}
	}
	
	public static void main(String[] args) {
		new SolverServer();
	}
}
