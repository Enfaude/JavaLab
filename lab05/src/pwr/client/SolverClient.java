package pwr.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

import pwr.common.IClientRegister;
import pwr.common.ISorting;
import pwr.common.ServerObject;

public class SolverClient {	

	public static void main(String[] args) {
		List<Integer> input = new ArrayList<>();
		List<ServerObject> activeServerList = new ArrayList<>();
		try {
//			Registry sortingRegistry = LocateRegistry.getRegistry("localhost", 8889);
//			ISorting sorter = (ISorting) sortingRegistry.lookup("SortingService");
			
			Registry serverRegistryService = LocateRegistry.getRegistry("localhost", 8888);
			IClientRegister serverRegister = (IClientRegister) serverRegistryService.lookup("ServerRegistryService");
			activeServerList = serverRegister.getServers();
			
//			List<Integer> result = sorter.solve(input);
//			for (Integer el : result) {
//				System.out.println(el);
//			}
//			System.out.println();
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
		
		if (!activeServerList.isEmpty()) {
			System.out.println("Client found servers: ");
			for (ServerObject server : activeServerList) {
				System.out.println(server.getName() + " operating on port " + server.getPort());
				try {
					System.out.println("Attempting to sort data");
					Registry sortingRegistry = LocateRegistry.getRegistry(server.getServerIp(), server.getPort());
					ISorting sorter = (ISorting) sortingRegistry.lookup(server.getName());
					List<Integer> result = sorter.solve(input);
					for (Integer el : result) {
						System.out.println(el);
					}
					System.out.println();
				} catch (NotBoundException | RemoteException e) {
					e.printStackTrace();
				}
			}
		} else {
			System.out.println("Client found no servers");
		}
	}

}
