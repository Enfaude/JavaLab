package pwr.mnk;

public class Game {
	public final static char ZERO = '0';
	public final static char ONE = '1';
	public final static char TWO = '2';
	
	public static char[][] board = new char[5][5];
	boolean isOver;
	int turnCounter = 0;
	
	public Game() {
		clearBoard();
		isOver = false;
	}
	
	
	private void clearBoard() {
		for (int x = 0; x < 5; x++) {
			for (int y = 0; y <5; y++) {
				board[x][y] = ZERO;
			}
		}
	}
	
	public void printBoard() {
		for (int x = 0; x < 5; x++) {
			StringBuilder row = new StringBuilder();
			for (int y = 0; y <5; y++) {
				row.append(board[y][x]).append(' ');
			}
			System.out.println(row.toString());
		}
	}
	
	public boolean playTurn() {
		playerTurn();
		if (isOver == true) {
			return true;
		}
		cpuTurn();
		if (isOver == true) {
			return true;
		}
		return false;
	}
	
	public void playerTurn() {
		printBoard();
		System.out.println("Enter row");
		int row = GameApp.forceIntegerInput();
		System.out.println("Enter column");
		int col = GameApp.forceIntegerInput();
		if(isMoveValid(row, col)) {
			makeMove(row, col, ONE);
		} else {
			System.out.println("Suggested move is invalid, try again");
			playerTurn();
		}
	}
	
	public void cpuTurn() {
		int[] cpuMove = ComputerPlayer.playTurn(board);
		
		if(isMoveValid(cpuMove)) {
			makeMove(cpuMove, TWO);
		} else {
			System.out.println("Suggested move is invalid, try again");
			cpuTurn();
		}
	}
	
	static boolean isMoveValid(int row, int col) {
		try {
			return board[col][row] == ZERO;
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Entered coordinates are out of bound");
			return false;
		}
	}
	
	static boolean isMoveValid(int[] coords) {
		try {
			return board[coords[1]][coords[0]] == ZERO;
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Entered coordinates are out of bound");
			return false;
		}
	}
	
	public void makeMove(int row, int col, char player) {
		board[col][row] = player;
		turnCounter++;
		isGameOver(row, col, player);
		//if no player wins in the last turn, game concludes with a draw
		if (turnCounter == 25) {
			finish(ZERO);
		}
	}
	
	public void makeMove(int[] coords, char player) {
		board[coords[1]][coords[0]] = player;
		turnCounter++;
		isGameOver(coords[0], coords[1], player);
		//if no player wins in the last turn, game concludes with a draw
		if (turnCounter == 25) {
			finish(ZERO);
		}
	}
	
	private boolean isAtLeast4(int count) {
		return count >= 4;
	}
	
	public void finish(char player) {
		isOver = true;
		printBoard();
		if (player == '0') {
			System.out.println("Game concluded with a draw.");
		} else {
			System.out.println("Player " + player + " wins.");
		}
	}
	
	public boolean isGameOver(int row, int col, char player) {
		//check if row contains winning combination
		int count = 0;
		for (int i = 0; i < 5; i++) {
			if (board[i][row] == player) {
				count++;
				if (isAtLeast4(count)) {
					finish(player);
					return true;
				}
			} else {
				count = 0;
			}
		}
		//check if column contains winning combination
		count = 0;
		for (int i = 0; i < 5; i++) {
			if (board[col][i] == player) {
				count++;
				if (isAtLeast4(count)) {
					finish(player);
					return true;
				}
			} else {
				count = 0;
			}
		}
		//check '\' diagonal
		if (row == col) {
			count = 0;
			for (int i = 0; i < 5; i++) {
				if (board[i][i] == player) {
					count++;
					if (isAtLeast4(count)) {
						finish(player);
						return true;
					}
				} else {
					count = 0;
				}
			}
		}			
		//check '/' diagonal
		if (row + col == 4) { 
			count = 0;
			for (int i = 0; i < 5; i++) {
				if (board[i][4-i] == player) {
					count++;
					if (isAtLeast4(count)) {
						finish(player);
						return true;
					}
				} else {
					count = 0;
				}
			}
		}
		//check lower '\' diagonal
		if (row - col == 1) {
			count = 0;
			for (int i = 0; i < 4; i++) {
				if (board[i][i+1] == player) {
					count++;				
				} else {
					break;
				}
			}
			if (isAtLeast4(count)) {
				finish(player);
				return true;
			}
			count = 0;
		}
		//check upper '\' diagonal
		if (row - col == -1) {
			count = 0;
			for (int i = 0; i < 4; i++) {
				if (board[i+1][i] == player) {
					count++;				
				} else {
					break;
				}
			}
			if (isAtLeast4(count)) {
				finish(player);
				return true;
			}
		}		
		//check upper '/' diagonal
		if (row + col == 3) {
			count = 0;
			for (int i = 0; i < 4; i++) {
				if (board[i][3-i] == player) {
					count++;				
				} else {
					break;
				}
			}
			if (isAtLeast4(count)) {
				finish(player);
				return true;
			}
		}
		//check lower '/' diagonal
		if (row + col == 5) {
			count = 0;
			for (int i = 1; i < 5; i++) {
				if (board[i][5-i] == player) {
					count++;				
				} else {
					break;
				}
			}
			if (isAtLeast4(count)) {
				finish(player);
				return true;
			}
		}
		
		return false;
	}
		
}
