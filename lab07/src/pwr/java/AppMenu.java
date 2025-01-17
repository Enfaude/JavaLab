package pwr.java;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import javax.xml.soap.SOAPException;

public class AppMenu {
	AppInfo appInfo;
	Messenger messenger;
	Scanner sc;
	public AppMenu(AppInfo appInfo, Messenger messenger) {
		sc = new Scanner(System.in);
		this.appInfo = appInfo;
		this.messenger = messenger;
	}
	
	
	public void menu() {
		while (true) {
			System.out.println("Menu Application " + appInfo.getName());
			System.out.println("1. Send message");
			System.out.println("2. View inbox");
			System.out.println("0. Close application");
			
			int choice = forceIntegerInput();
			switch (choice) {
				case 1: 					
					try {
						send();
					} catch (SOAPException | IOException e1) {
						System.out.println("An error occured, going back to menu");
						menu();
					}
						break;
				case 2: 					
					printInbox();
					break;
				case 0:
					System.out.println("Closing application");
					sc.close();
					System.exit(0);
			}
		}
	}
		 	
	public int forceIntegerInput() {
		int value;
		try { 
			value = sc.nextInt();
			sc.nextLine();
		} catch (InputMismatchException e) {
			System.out.println("Please enter correct numeric value");
			value = forceIntegerInput();
		}
		return value;
	}
	
	public void send() throws SOAPException, IOException {
		Message msg;
		System.out.println("Enter the recipient(type 'all' to send a broadcast message)");
		String recipient = sc.nextLine();
		System.out.println("Enter the message:");
		String text = sc.nextLine();
		if (recipient.toLowerCase().equals("all")) {
			msg = new Message(text, recipient, appInfo.getName(), true);
			msg.addReachedNode(appInfo);
		} else {
			msg = new Message(text, recipient, appInfo.getName(), false);
		}
		messenger.sendMessage(msg);
	}
	
	public void printInbox() {
		List<Message> inbox = messenger.getInbox();
		System.out.println("Your inbox contains " + inbox.size() + " messages:");
		for (Message msg : inbox) {
			System.out.println(msg);
		}
	}

}
