package pwr.server;

import java.rmi.RemoteException;
import java.util.Scanner;

import pwr.common.ISorting;

public class ServerApp {
	static String pigeonholeName = "PigeonholeServer";
	static String selectionSortName = "SelectionSortServer";
	static String quickSortName = "QuickSortServer";

	public static void main(String[] args) {
		ISorting sorter;
		try {
			sorter = new PigeonholeSort();
			SolverServer server = new SolverServer(pigeonholeName, sorter);
//			sorter = new SelectionSort();
//			SolverServer server = new SolverServer(selectionSortName, sorter);
//			sorter = new QuickSort();
//			SolverServer server = new SolverServer(quickSortName, sorter);
			Scanner sc = new Scanner(System.in);
			while (true) {
				System.out.println("Enter 1 to close server");
				if (sc.nextInt() == 1) {
					sc.close();
					if (server.unregisterServer(server.serverInfo)) {
						System.out.println("Server closed properly");
			            System.exit(0);
					} else {
						System.out.println("Server closed with an error during unregistering");
			            System.exit(-1);
					}
				}
			}
		} catch (RemoteException e) {
			System.out.println("ServerApp failed to ");
			e.printStackTrace();
		}
	}
}
