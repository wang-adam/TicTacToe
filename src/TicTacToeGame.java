import java.util.*;
//Test comment.
public class TicTacToeGame {
	private static String[][] board = new String[3][3];
	private static int numberMove = 0;
	private static String gameMode;
	private static int level;
	static Scanner in = new Scanner(System.in);

	public static void main(String[] args) {
		// Shows the Demo Board.
		for (int i = 0; i <= 2; i++) {
			for (int j = 0; j <= 2; j++) {
				if (i == 0) {
					board[i][j] = "A" + (j + 1);
				} else if (i == 1) {
					board[i][j] = "B" + (j + 1);
				} else {
					board[i][j] = "C" + (j + 1);
				}
			}
		}
		System.out.println("Welcome to The TicTacToe Game!");
		System.out.println("Here, the board is represented by numbers and letters: "
				+ "\nA1 represents the top left corner and C3 represents the bottom right.");
		printBoard();
		cleanBoard();
		System.out.println("S = Single Player");
		System.out.println("M = Multiplayer");
		System.out.print("Choose Mode (S or M): ");
		gameMode = in.nextLine().toUpperCase();
		int player = 1;
		int flag = 0;
		while (!gameMode.equals("M") && !gameMode.equals("S")) {
			System.out.print("**Invalid Game Mode** (S or M): ");
			gameMode = in.nextLine().toUpperCase();
		}
		// If single player, choose difficulty and set player 2.
		// Single player: player = 2, computer = -2
		// Multiplayer: player 1 = 1, player 2 = -1;
		if (gameMode.equals("S")) {
			// 0: Computer Moves Random
			// 1: Smart Computer
			System.out.print("Pick Difficulty Level (0 or 1): ");
			level = Integer.parseInt(in.nextLine());
			while (level != 0 && level != 1) {
				System.out.print("**Invalid Difficulty Level** (0 or 1): ");
				level = Integer.parseInt(in.nextLine());
			}
			player = 2;
		}
		// Player v. Player or Player v. Computer take turns.
		while (flag != 2) {
			playerMove(player);
			flag = endGame();
			if (flag == 1) {
				cleanBoard();
				printBoard();
				player = Math.abs(player);
				numberMove = 0;
			}
			player *= -1;
		}

		in.close();
	}

	// Prints the Board, uses a " " for empty spots.
	public static void printBoard() {
		System.out.println("Board: ");
		for (int i = 0; i <= 2; i++) {
			String row = " " + board[i][0] + " | " + board[i][1] + " | " + board[i][2];
			System.out.println(row);
			if (i < 2) {
				for (int j = 0; j <= row.length(); j++) {
					System.out.print("-");
				}
				System.out.println("");
			}

		}
	}

	// Resets the board to empty.
	public static void cleanBoard() {
		for (int i = 0; i <= 2; i++) {
			for (int j = 0; j <= 2; j++) {
				board[i][j] = " ";
			}
		}
		numberMove = 0;
	}

	// Moves: p=1, -1 when in multiplayer
	// p = 2, -2 when in single player
	public static void playerMove(int p) {
		if (p != -2) {
			if (p == 1) {
				System.out.print("Player X Move: ");
			} else if (p == -1) {
				System.out.print("Player O Move: ");
			} else if (p == 2) {
				System.out.print("Your Move: ");
				numberMove += 1;
			}
			String s = in.nextLine().toUpperCase();
			int r = Character.toUpperCase(s.charAt(0) - 'A');
			int c = Integer.parseInt("" + s.charAt(1)) - 1;
			while (isOccupied(r, c)) {
				System.out.print("**Invalid Move** Try Again: ");
				s = in.nextLine().toUpperCase();
				r = Character.toUpperCase(s.charAt(0) - 'A');
				c = Integer.parseInt(s.substring(1)) - 1;
			}
			if (p == 1) {
				board[r][c] = "X";
				printBoard();
			} else if (p == 2) {
				board[r][c] = "X";
			} else {
				board[r][c] = "O";
				printBoard();
			}

		} else {
			computerMove();
			printBoard();
		}
	}

	// Checks if the game is over.
	public static int endGame() {
		String v1 = "";
		String v2 = "";
		String v3 = "";
		String h1 = "";
		String h2 = "";
		String h3 = "";
		String d1 = "";
		String d2 = "";
		for (int i = 0; i <= 2; i++) {
			v1 += board[i][0];
			v2 += board[i][1];
			v3 += board[i][2];
			h1 += board[0][i];
			h2 += board[1][i];
			h3 += board[2][i];
			d1 += board[i][i];
			d2 += board[2 - i][i];
		}
		if (v1.equals("XXX") || v2.equals("XXX") || v3.equals("XXX") || h1.equals("XXX") || h2.equals("XXX")
				|| h3.equals("XXX") || d1.equals("XXX") || d2.equals("XXX")) {
			if (gameMode.equals("S")) {
				System.out.println("Congratulations: You Win!");
			} else {
				System.out.println("Congratulations: Player X Wins!");
			}
		} else if (v1.equals("OOO") || v2.equals("OOO") || v3.equals("OOO") || h1.equals("OOO") || h2.equals("OOO")
				|| h3.equals("OOO") || d1.equals("OOO") || d2.equals("OOO")) {
			if (gameMode.equals("S")) {
				System.out.println("Game Over: Computer Wins!");
			} else {
				System.out.println("Congratulations: Player O Wins!");
			}
		} else {
			for (int i = 0; i <= 2; i++) {
				for (int j = 0; j <= 2; j++) {
					if (board[i][j].equals(" ")) {
						return 0;
					}
				}
			}
			printBoard();
			System.out.println("Draw!");
		}
		System.out.print("Do You Want To Play Again? (Y or N): ");
		if (in.nextLine().toUpperCase().equals("Y")) {
			return 1;
		}
		return 2;
	}

	// Checks to see if a cell is occupied
	public static boolean isOccupied(int r, int c) {
		if (r < 0 || r > 2 || c < 0 || c > 2) {
			return true;
		}
		if (board[r][c].equals(" ")) {
			return false;
		}
		return true;
	}

	// Computer's Move (level 0 means random move, level 1 means smart move).
	public static void computerMove() {
		if (level == 0) {
			randomMove();
		} else if (level == 1) {
			// Takes the middle if the player does not take it during the first turn.
			if (numberMove == 1) {
				if (!isOccupied(1, 1)) {
					board[1][1] = "O";
				} else {
					board[0][2] = "O";
				}
				numberMove = 2;
				return;
			}
			// If the computer is one move away from winning, it will take the move.
			if (canWin()) {
				return;
			}
			// Checks if the player is about to win by the \ diagonal.
			int temp = diag1InDanger();
			if (temp != -1) {
				board[temp][temp] = "O";
				return;
			}
			// Checks if the player is about to win by the / diagonal.
			temp = diag2InDanger();
			if (temp != -1) {
				board[2 - temp][temp] = "O";
				return;
			}
			for (int i = 0; i <= 2; i++) {
				int c = rowInDanger(i);
				int r = colInDanger(i);
				// Checks if the player is about to win by row i.
				if (c != -1) {
					board[i][c] = "O";
					return;
				}
				// Checks if the player is about to win by column i.
				else if (r != -1) {
					board[r][i] = "O";
					return;
				}
			}
			randomMove();
		}
	}

	// Chooses a random free space for the computer to move.
	public static void randomMove() {
		int r = (int) (Math.random() * 10 % 3);
		int c = (int) (Math.random() * 10 % 3);
		while (isOccupied(r, c)) {
			r = (int) (Math.random() * 10 % 3);
			c = (int) (Math.random() * 10 % 3);
		}
		board[r][c] = "O";
	}

	// Checks if the player is about to win by row r, returns the column of the free
	// space.
	public static int rowInDanger(int r) {
		int numX = 0;
		int ti = -1;
		for (int i = 0; i <= 2; i++) {
			if (board[r][i].equals("X")) {
				numX++;
			} else {
				ti = i;
			}
		}
		if (numX < 2 || isOccupied(r, ti)) {
			return -1;
		}
		return ti;
	}

	// Checks if the player is about to win by column c, returns the row of the free
	// space.
	public static int colInDanger(int c) {
		int numX = 0;
		int ti = -1;
		for (int i = 0; i <= 2; i++) {
			if (board[i][c].equals("X")) {
				numX++;
			} else {
				ti = i;
			}
		}
		if (numX < 2 || isOccupied(ti, c)) {
			return -1;
		}
		return ti;
	}

	// Checks if the player is about to win by the \ diagonal, returns the value of
	// the free space.
	public static int diag1InDanger() {
		int numX = 0;
		int ti = -1;
		for (int i = 0; i <= 2; i++) {
			if (board[i][i].equals("X")) {
				numX++;
			} else {
				ti = i;
			}
		}
		if (numX < 2 || isOccupied(ti, ti)) {
			return -1;
		}
		return ti;
	}

	// Checks if the player is about to win by the / diagonal returns the value of
	// the free space.
	public static int diag2InDanger() {
		int numX = 0;
		int ti = -1;
		for (int i = 0; i <= 2; i++) {
			if (board[2 - i][i].equals("X")) {
				numX++;
			} else {
				ti = i;
			}
		}
		if (numX < 2 || isOccupied(2 - ti, ti)) {
			return -1;
		}
		return ti;
	}

	// Checks if the computer can win this turn. If it can, it will, if not, returns
	// false.
	public static boolean canWin() {
		int numO = 0;
		int row = -1;
		int col = -1;
		for (int r = 0; r <= 2; r++) {
			numO = 0;
			for (int c = 0; c <= 2; c++) {
				if (board[r][c].equals("O")) {
					numO++;
				} else {
					row = r;
					col = c;
				}
			}
			if (numO == 2 && !isOccupied(row, col)) {
				board[row][col] = "O";
				return true;
			}
			row = -1;
			col = -1;
		}
		if (numO < 2) {
			for (int c = 0; c <= 2; c++) {
				numO = 0;
				for (int r = 0; r <= 2; r++) {
					if (board[r][c].equals("O")) {
						numO++;
					} else {
						row = r;
						col = c;
					}
				}
				if (numO == 2 && !isOccupied(row, col)) {
					board[row][col] = "O";
					return true;
				}
				row = -1;
				col = -1;
			}
		}
		if (numO < 2) {
			numO = 0;
			for (int r = 0; r <= 2; r++) {
				if (board[r][r].equals("O")) {
					numO++;
				} else {
					row = r;
					col = r;
				}
			}
			if (numO == 2 && !isOccupied(row, col)) {
				board[row][col] = "O";
				return true;
			}
			row = -1;
			col = -1;
		}
		if (numO < 2) {
			numO = 0;
			for (int r = 0; r <= 2; r++) {
				if (board[2 - r][r].equals("O")) {
					numO++;
				} else {
					row = 2 - r;
					col = r;
				}
			}
			if (numO == 2 && !isOccupied(row, col)) {
				board[row][col] = "O";
				return true;
			}
			row = -1;
			col = -1;
		}
		return false;
	}
}
