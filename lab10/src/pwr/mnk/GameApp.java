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
					gameMenu();
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
	
	public static boolean gameMenu() {		
		Game game = new Game();
		ComputerPlayer.scanAlgs();
		ComputerPlayer.selectAlg();	
		while (true) {				
			System.out.println("Currently selected algorythm: " + ComputerPlayer.currentAlg);
			System.out.println("Game menu:");			
			System.out.println("1. Play next turn");
			System.out.println("2. Change AI algorythm");
			System.out.println("3. Print board");
			System.out.println("9. Go back to main menu");
			System.out.println("0. Close application");
			
			int choice = forceIntegerInput(); 
			switch (choice) {
				case 1:					
					if(game.playTurn()) {
						return true;
					}
					break;
				case 2:
					ComputerPlayer.scanAlgs();
					ComputerPlayer.selectAlg();
					break;
				case 3:
					game.printBoard();
					break;
				case 9:
					menu();
					break;
				case 0:
					System.out.println("Closing application");
					sc.close();
					System.exit(0);
			}
		}
	}
	
	public static Integer forceIntegerInput() {
		Integer value = null;
		boolean correct = false;
		while(!correct) {
			try { 
				value = sc.nextInt();
				correct = true;
			} catch (InputMismatchException e) {
				System.out.println("Please enter correct numeric value");
				sc.nextLine();
			}
		}
		return value;
	}

}
