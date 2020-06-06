package pwr.mnk;

import java.util.InputMismatchException;
import java.util.Scanner;

public class GameApp {
	static Scanner sc;

	public static void main(String[] args) {
		sc = new Scanner(System.in);
		menu();
	}
	
	public static void menu() {
		while (true) {
			System.out.println("5,5,4-game Application");
			
			System.out.println("1. Start new game");
			System.out.println("0. Close application");
			
			int choice = forceIntegerInput(); 
			switch (choice) {
				case 1: 
					Game game = new Game();
					game.play();
					break;
				case 2: 	
					break;
				case 3:
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
