package vttp.batch5.sdf.task02;

import java.io.*;
import java.util.*;

public class Main {

	static char[][] board = new char[3][3];

	public static void main(String[] args) throws Exception {

		String filePath = args[0];
		ArrayList<Integer[]> emptyPositions = new ArrayList<>();
		Map<String, Integer> utilityMap = new HashMap<>();

		// Read the TTT board config file
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String line;

		// Store as char[3][3] board
		for (int i = 0; i < 3; i++) {
			line = br.readLine();
			for (int j = 0; j < 3; j++) {
				board[i][j] = line.charAt(j);
			}
		}
		br.close();

		// Get index of all empty positions on the TTT board
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j] == '.') {
					Integer[] coordinates = { i, j };
					emptyPositions.add(coordinates);
				}
			}
		}

		// Evaluate each empty position
		for (Integer[] pos : emptyPositions) {
			// Place 'X' temporarily at the empty position
			board[pos[0]][pos[1]] = 'X';

			// Check if placing 'X' results in a win and add 1 to utility
			if (checkWin('X')) {
				utilityMap.put("y=" + pos[0] + ", x=" + pos[1], 1);
			}
			// Check for 2 'O' and 1 space and add -1 to utility
			else if (potentialWinning('O')) {
				utilityMap.put("y=" + pos[0] + ", x=" + pos[1], -1);
			}
			// Otherwise, add 0 to utility
			else {
				utilityMap.put("y=" + pos[0] + ", x=" + pos[1], 0);
			}
			// Reset the position to empty for the next iteration
			board[pos[0]][pos[1]] = '.';
		}

		// Print expected output format
		System.out.println("Processing: " + filePath + "\n");
		System.out.println("Board: \n");
		for (char[] row : board) {
			System.out.println(row);
		}
		System.out.println("------------------------------");
		for (Map.Entry<String, Integer> entry : utilityMap.entrySet()) {
			System.out.println(entry.getKey() + ", " + "utility=" + entry.getValue());
		}
	}

	// Helper methods
	private static boolean checkWin(char player) {
		// Check rows, columns, and diagonals for three in a row for the given player
		for (int i = 0; i < 3; i++) {
			if (board[i][0] == player && board[i][1] == player && board[i][2] == player)
				return true;
			if (board[0][i] == player && board[1][i] == player && board[2][i] == player)
				return true;
		}
		if (board[0][0] == player && board[1][1] == player && board[2][2] == player)
			return true;
		if (board[0][2] == player && board[1][1] == player && board[2][0] == player)
			return true;
		return false;
	}

	private static boolean potentialWinning(char player) {
		// Check each row, column, and both diagonals for two of the player's marks and
		// one empty space
		for (int i = 0; i < 3; i++) {
			// Row check
			if ((board[i][0] == player && board[i][1] == player && board[i][2] == '.') ||
					(board[i][0] == player && board[i][1] == '.' && board[i][2] == player) ||
					(board[i][0] == '.' && board[i][1] == player && board[i][2] == player)) {
				return true;
			}
			// Column check
			if ((board[0][i] == player && board[1][i] == player && board[2][i] == '.') ||
					(board[0][i] == player && board[1][i] == '.' && board[2][i] == player) ||
					(board[0][i] == '.' && board[1][i] == player && board[2][i] == player)) {
				return true;
			}
		}
		// Diagonal check
		if ((board[0][0] == player && board[1][1] == player && board[2][2] == '.') ||
				(board[0][0] == player && board[1][1] == '.' && board[2][2] == player) ||
				(board[0][0] == '.' && board[1][1] == player && board[2][2] == player)) {
			return true;
		}
		// Anti-diagonal check
		if ((board[0][2] == player && board[1][1] == player && board[2][0] == '.') ||
				(board[0][2] == player && board[1][1] == '.' && board[2][0] == player) ||
				(board[0][2] == '.' && board[1][1] == player && board[2][0] == player)) {
			return true;
		}
		return false;
	}
}