package pwr.server;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import pwr.common.IServerRegister;
import pwr.common.ISorting;
import pwr.common.ServerObject;

public class SolverServer {
	String serverName = "SolvingServerInstance";
	int baseRegistryPort = 8888;
	int baseServerPort = 8889;
	int maxTries = 2;
	static ServerObject serverInfo;
	Registry registry;
	IServerRegister serverRegister;

	
	public SolverServer() {
		System.out.println("Creating new solving server");
		serverInfo = bindServerService();
		registerServer(serverInfo);
	
	}
	
	public static void main(String[] args) {
		SolverServer server = new SolverServer();
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("Enter 1 to close server");
			if (sc.nextInt() == 1) {
				sc.close();
				if (server.unregisterServer(serverInfo)) {
					System.out.println("Server closed properly");
		            System.exit(0);
				} else {
		            System.exit(-1);
				}
			}
		}
	}
	
	public void registerServer(ServerObject server) {
		if (server != null) {
			System.out.println("Solving server is looking for serverlist");	
			int tryCount = 0;
			int registryPort = baseRegistryPort;
			boolean isRegistered = false;
			while(tryCount < maxTries) {
				try {
					Registry serverRegistryService = LocateRegistry.getRegistry("localhost", baseRegistryPort);
					serverRegister = (IServerRegister) serverRegistryService.lookup("ServerRegistryService");
					System.out.println("Solving server found serverlist, now trying to register");	
					isRegistered = serverRegister.registerServer(server);
					break;
				} catch (RemoteException e) {
					tryCount++;
					System.out.println("Registry port " + registryPort + " is already bound, retrying with " + --registryPort);	
				} catch (NotBoundException e) {
					System.out.println("Solving server could not find the server registry");
					e.printStackTrace();
					break;
				}
			}
			if (isRegistered) {
				System.out.println(server.getName() + " has been successfully registered on port " + server.getPort());
			} else {
				System.out.println(server.getName() + " could not be registered.");
			}
		} else {
			System.out.println("Couldn't register server, server object value is null");
		}
	}
	
	public ServerObject bindServerService() {
		int tryCount = 0;
		int serverPort = baseServerPort;
		ServerObject server = null;
		while(tryCount < maxTries) {
			try {
				ISorting sorter = new SortingImpl();	
				registry = LocateRegistry.createRegistry(serverPort);
				registry.bind(serverName, sorter);
				System.out.println("Solving server successfully opened on port " + serverPort);
				server = new ServerObject(serverName, serverPort, "desc");
				break;				
			} catch (RemoteException e) {
				tryCount++;
				System.out.println("Port " + serverPort + " is already bound, retrying with " + ++serverPort);	
			} catch (AlreadyBoundException e) {
				System.out.println("Solving server successfully opened on port " + serverPort);
				e.printStackTrace();
			}
		}
		
		return server;
	}
	
	public boolean unregisterServer(ServerObject server) {
		Boolean result = false;
		if (server != null) {
			try {
				registry.unbind(serverName);
				result = serverRegister.unregisterServer(server);
			} catch (RemoteException | NotBoundException e) {
				e.printStackTrace();
				System.out.println("Server could not be unregistered correctly");
			}
		} else {
			System.out.println("There is no server object to unregister");
			result = true;
		}
		return result;
	}
}
