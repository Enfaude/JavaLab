package pwr.client;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import pwr.common.IClientRegister;
import pwr.common.ISorting;
import pwr.common.ServerObject;

public class SolverClient {	
	static int baseRegistryPort = 8888;
	static int maxTries = 3;
	static String registryName = "ServerRegistryService";
	static List<Integer> inputList = new ArrayList<>();
	static List<Integer> resultList = new ArrayList<>();
	static List<ServerObject> activeServerList = new ArrayList<>();
	static JList<String> listServers = new JList<String>();
	
	public static List<ServerObject> getActiveServers() {
		System.out.println("App is trying to get latest server registry");
		List<ServerObject> activeServerList = new ArrayList<>();
		int tryCount = 0;
		int registryPort = baseRegistryPort;
		while(tryCount < maxTries) {
			try {
				Registry serverRegistryService = LocateRegistry.getRegistry("localhost", registryPort);
				IClientRegister serverRegister = (IClientRegister) serverRegistryService.lookup(registryName);
				activeServerList = serverRegister.getServers();
				System.out.println("App have retrieved the server registry from port " + registryPort);				
				break;
			} catch (RemoteException e) {
				tryCount++;
				System.out.println("Registry port " + registryPort + " is unavailable");	
				registryPort--;
			} catch (NotBoundException e) {
				tryCount++;
				System.out.println("App could not find the server registry on port " + registryPort);
				registryPort--;
			}
		}
		if (tryCount == maxTries) {
			System.out.println("App could not find the server registry");
		}
		
		return activeServerList;
	}
	
    public static void printListElements(List<Integer> list) {
        for (Integer element : list) {
            System.out.println(element);
        }
    }
    
    public static void printGeneratedList() {
        System.out.println("Generated list:");
        printListElements(inputList);
    }
    
    public static void printSortedList() {
        System.out.println("Sorted list:");
        printListElements(resultList);
    }
    
    public static void updateGuiServerList() {
    	DefaultListModel<String> serversArr = new DefaultListModel<>();
    	for (ServerObject server : activeServerList) {
    		String serverText = "Port " + server.getPort() + " | " + server.getName();
    		serversArr.addElement(serverText);
    	}
		listServers.setModel(serversArr);
	}    
    
    public static List<Integer> generateInputData(int size) {
    	List<Integer> newInputData = new ArrayList<Integer>();
    	Random rand = new Random();
    	for (int i = 0; i < size; i++) {
    		newInputData.add(rand.nextInt(100));
    	}
    	return newInputData;
    }
    
    public static List<Integer> sortData(int index) {
    	ServerObject server = activeServerList.get(index);
    	List<Integer> result = new ArrayList<>();
    	try {
			System.out.println("Attempting to sort data");
			Registry sortingRegistry = LocateRegistry.getRegistry(server.getServerIp(), server.getPort());
			ISorting sorter = (ISorting) sortingRegistry.lookup(server.getName());
			result = sorter.solve(inputList);
			System.out.println("Data has been sorted successfully");
		} catch (NotBoundException | RemoteException e) {
			System.out.println("Could not get data sorted, check if server list is up-to-date");
		}
    	return result;
    }
    

	public static void main(String[] args) {
		JFrame mainFrame = new JFrame("RMI Solver Client");
		mainFrame.getContentPane().setLayout(null);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setBounds(640, 360, 530, 400);
		
		listServers.setVisibleRowCount(10);
		listServers.setFont(new Font("Tahoma", Font.PLAIN, 12));
		listServers.setBounds(174, 36, 336, 275);
		mainFrame.getContentPane().add(listServers);
		
		JLabel lblAvailableServers = new JLabel("Available servers");
		lblAvailableServers.setBounds(174, 11, 100, 14);
		mainFrame.getContentPane().add(lblAvailableServers);
		
		JPanel panelGenerate = new JPanel();
		panelGenerate.setBorder(null);
		panelGenerate.setBounds(10, 11, 154, 348);
		mainFrame.getContentPane().add(panelGenerate);
		panelGenerate.setLayout(null);
		
		JLabel lblGenerateTitle = new JLabel("Generate data to sort:");
		lblGenerateTitle.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblGenerateTitle.setBounds(10, 11, 235, 17);
		panelGenerate.add(lblGenerateTitle);
		
		JLabel lblGenerateSize = new JLabel("Size of list");
		lblGenerateSize.setBounds(10, 39, 235, 14);
		panelGenerate.add(lblGenerateSize);
		
		JTextField textGenerateSize = new JTextField();
		textGenerateSize.setBounds(10, 64, 44, 20);
		panelGenerate.add(textGenerateSize);
		textGenerateSize.setColumns(10);
		
		JButton btnGenerate = new JButton("Generate");
		btnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					int inputSize = Integer.parseInt(textGenerateSize.getText());
					inputList = generateInputData(inputSize);
					System.out.println("Input list has been generated");
				} catch (NumberFormatException e) {
					System.out.println("Incorrect number format provided(Int is required)");
				}
			}
		});
		btnGenerate.setBounds(10, 95, 134, 23);
		panelGenerate.add(btnGenerate);
		
		JButton btnPrintGeneratedData = new JButton("Generated data");
		btnPrintGeneratedData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				printGeneratedList();
			}
		});
		btnPrintGeneratedData.setBounds(10, 204, 134, 23);
		panelGenerate.add(btnPrintGeneratedData);
		
		JButton btnPrintSortedData = new JButton("Sorted result");
		btnPrintSortedData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				printSortedList();
			}
		});
		btnPrintSortedData.setBounds(10, 238, 134, 23);
		panelGenerate.add(btnPrintSortedData);
		
		JLabel lblPrint = new JLabel("Print in console:");
		lblPrint.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblPrint.setBounds(10, 176, 134, 17);
		panelGenerate.add(lblPrint);
		
		JButton btnSort = new JButton("Sort");
		btnSort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (listServers.getSelectedIndex() >= 0) {
					resultList = sortData(listServers.getSelectedIndex());
				} else {
					System.out.println("Select server from the list");
				}
			}
		});
		btnSort.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnSort.setBounds(174, 322, 100, 23);
		mainFrame.getContentPane().add(btnSort);
		
		JButton btnRefreshServers = new JButton("Refresh");
		btnRefreshServers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				activeServerList = getActiveServers();
				updateGuiServerList();
			}
		});
		btnRefreshServers.setBounds(421, 7, 89, 23);
		mainFrame.getContentPane().add(btnRefreshServers);
				
		mainFrame.setVisible(true);
	}
}
