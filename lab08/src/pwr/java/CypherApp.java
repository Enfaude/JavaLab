package pwr.java;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CypherApp {
	static Scanner sc;
	static String key = "klucz kodowania";
	
	public static void main(String[] args) {
		sc = new Scanner(System.in);
		menu();
	}
	
	public static void menu() {
		while (true) {
			System.out.println("Cypher Application");
			System.out.println("1. Encrypt file");
			System.out.println("2. Decrypt file");
			System.out.println("0. Close application");
			
			int choice = forceIntegerInput();
			switch (choice) {
				case 1: 
					Cypher.encrypt(key);
					break;
				case 2: 	
					Cypher.decrypt(key);
					break;
				case 0:
					System.out.println("Closing application");
					sc.close();
					System.exit(0);
			}
		}
	}
	
	public static int forceIntegerInput() {
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

}
