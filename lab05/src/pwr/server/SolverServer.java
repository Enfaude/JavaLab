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
	String serverName;
	int baseRegistryPort = 8888;
	int baseServerPort = 8889;
	int maxTries = 3;
	ServerObject serverInfo;
	Registry registry;
	IServerRegister serverRegister;
	boolean isRegistered = false;

	
	public SolverServer(String serverName, ISorting sort) {
		this.serverName = serverName;
		System.out.println("Creating new solving server");
		serverInfo = bindServerService(sort);
		registerServer(serverInfo);
	
	}
	
	public void registerServer(ServerObject server) {
		if (server != null) {
			System.out.println("Solving server is looking for serverlist");	
			int tryCount = 0;
			int registryPort = baseRegistryPort;
			while(tryCount < maxTries) {
				try {
					Registry serverRegistryService = LocateRegistry.getRegistry("localhost", registryPort);
					serverRegister = (IServerRegister) serverRegistryService.lookup("ServerRegistryService");
					System.out.println("Solving server found serverlist, now trying to register");	
					isRegistered = serverRegister.registerServer(server);
					break;
				} catch (RemoteException e) {
					tryCount++;
					System.out.println("Registry could not be reached on port " + registryPort);
					registryPort--;
				} catch (NotBoundException e) {
					tryCount++;
					System.out.println("Solving server could not find the server registry on port " + registryPort);
					registryPort--;
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
	
	public ServerObject bindServerService(ISorting sorter) {
		int tryCount = 0;
		int serverPort = baseServerPort;
		ServerObject server = null;
		while(tryCount < maxTries) {
			try {
				registry = LocateRegistry.createRegistry(serverPort);
				registry.bind(serverName, sorter);
				System.out.println("Solving server successfully opened on port " + serverPort);
				server = new ServerObject(serverName, serverPort);
				break;				
			} catch (RemoteException e) {
				tryCount++;
				System.out.println("Port " + serverPort + " is already bound");
				serverPort++;
			} catch (AlreadyBoundException e) {
				System.out.println("Solving server successfully opened on port " + serverPort);
				e.printStackTrace();
			}
		}		
		return server;
	}
	
	public boolean unregisterServer(ServerObject server) {
		Boolean result = false;
		if (isRegistered) {
			try {
				registry.unbind(serverName);
				result = serverRegister.unregisterServer(server);
				System.out.println("Server was unregistered correctly");
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
