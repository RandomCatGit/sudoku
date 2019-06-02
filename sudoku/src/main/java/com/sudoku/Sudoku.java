package com.sudoku;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Sudoku is class contains API for sudoku game such as generating a random board, removing random numbers and prining
 * the board.
 *
 * @author RandomCatGit
 */
public class Sudoku {

	private final Random random = new Random();

	private int[][] fixedMap = new int[9][9];

	/*
	 * quadMap is the quadrant mapping of sudoku number positions where each number corresponds to the quadrant the
	 * number is at.
	 */
	private final int[][] quadMap = {
			{
					1, 1, 1, 2, 2, 2, 3, 3, 3 },
			{
					1, 1, 1, 2, 2, 2, 3, 3, 3 },
			{
					1, 1, 1, 2, 2, 2, 3, 3, 3 },
			{
					4, 4, 4, 5, 5, 5, 6, 6, 6 },
			{
					4, 4, 4, 5, 5, 5, 6, 6, 6 },
			{
					4, 4, 4, 5, 5, 5, 6, 6, 6 },
			{
					7, 7, 7, 8, 8, 8, 9, 9, 9 },
			{
					7, 7, 7, 8, 8, 8, 9, 9, 9 },
			{
					7, 7, 7, 8, 8, 8, 9, 9, 9 } };

	public void main(String[] args) {
		int[][] sudoku = new int[9][9];
//		sudoku = fixValues(sudoku, new int[] {
//				0, 0, 1 },
//				new int[] {
//						2, 2, 1 });
//		generateRowSequences();
//		sudoku = fillQuadrant(sudoku, 1);
//		sudoku = fillQuadrant(sudoku, 2);
//		sudoku = fillQuadrant(sudoku, 3);
		sudoku = generateSudokuBoard(sudoku);
		sudoku = removeRandom(sudoku, 40);
//		printBoard(sudoku);
//		checkBoardPass(sudoku);
		printBorderedBoard(sudoku);
	}

	/**
	 * removeRandom method is used for
	 * 
	 * @param sudoku
	 * @param i
	 * @return
	 */
	private int[][] removeRandom(int[][] sudoku, int removeCount) {
		List<String> removedList = new LinkedList<String>();
		int x = 0, y = 0;
		String key = null;
		for (int i = 0; i < removeCount; i++) {
			while (key == null || removedList.indexOf(key) != -1) {
				x = random.nextInt(9);
				y = random.nextInt(9);
				key = String.valueOf(x) + String.valueOf(y);
			}
			removedList.add(key);
			sudoku[x][y] = 0;
		}
		return sudoku;
	}

	/**
	 * fixValues method is used for
	 * 
	 * @param sudoku
	 * @return
	 */
	private int[][] fixValues(int[][] sudoku, int[]... markData) {
		for (int[] data : markData) {
			if (data[0] > 8 || data[1] > 8 || data[0] < 0 || data[1] < 0 || data[2] > 9 || data[2] < 1) {
				System.err.println("Illegal table/data values");
			} else {
				sudoku[data[0]][data[1]] = data[2];
				fixedMap[data[0]][data[1]] = 1;
			}
		}
		return sudoku;
	}

	/**
	 * printBorderedBoard method is used for
	 * 
	 * @param sudoku
	 */
	private void printBorderedBoard(int[][] sudoku) {
		for (int i = 0; i < 9; i++) {
			if (i == 0 || i == 3 || i == 6) {
				System.out.println("-------------------------------");
			}
			for (int j = 0; j < 9; j++) {
				if (j == 0 || j == 3 || j == 6) {
					System.out.print("|");
				}
				System.out.print(" " + (sudoku[i][j] == 0 ? " " : sudoku[i][j]) + " ");
				if (j == 8) {
					System.out.print("|");
				}
			}
			System.out.println("");
		}
		System.out.println("-------------------------------");
	}

	/**
	 * fillQuadrant method is used for
	 * 
	 * @param sudoku
	 * 
	 * @param i
	 * @return
	 */
	private int[][] fillQuadrant(int[][] sudoku, int i) {
		int rs, re, cs, ce;
		if (i < 4) {
			rs = 0;
			re = 2;
		} else if (i > 3 && i < 7) {
			rs = 3;
			re = 5;
		} else {
			rs = 6;
			re = 8;
		}
		if (i == 1 || i == 4 || i == 7) {
			cs = 0;
			ce = 2;
		} else if (i == 2 || i == 5 || i == 8) {
			cs = 3;
			ce = 5;
		} else {
			cs = 6;
			ce = 8;
		}
		for (int r = rs; r <= re; r++) {
			for (int c = cs; c <= ce; c++) {
				int rnd = 0;
				boolean check = true;
				while (check) {
					check = checkViolated(sudoku, r, c, rnd = getRandom());
					System.out.println("Running " + r + " " + c + " checking " + rnd);
					sudoku[r][c] = rnd;
				}
			}
		}
		return sudoku;
	}

	/**
	 * generateSudokuBoard method is used for generating sudoku compliant table of numbers.
	 * 
	 * @param sudoku2
	 * @return
	 * 
	 */
	private int[][] generateSudokuBoard(int[][] sudoku) {
		int count = 0;
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				int rnd = 0;
				boolean check = true;
				Set<Integer> exhaust = new HashSet<>();
				while (check) {
					rnd = getRandom();
					check = checkViolated(sudoku, r, c, rnd);
					exhaust.add(Integer.parseInt(String.valueOf(rnd)));
//					System.out.println("Running " + r + " " + c + " checking " + rnd);
					count++;
//					printBorderedBoard(sudoku);
					if (exhaust.size() == 9) {
//						System.out.println("Exausted. Backtracked to previous row");
						sudoku[r] = new int[] {
								0, 0, 0, 0, 0, 0, 0, 0, 0 };
						if (r == 0) {
							c = -1;
							break;
						}
						r--;
						sudoku[r] = new int[] {
								0, 0, 0, 0, 0, 0, 0, 0, 0 };
						c = -1;
						break;
					}
				}
				if (c != -1) {
					sudoku[r][c] = rnd;
				}
			}
		}
		System.out.println("Total checks " + count);
		checkBoardPass(sudoku);
		return sudoku;
	}

	/**
	 * checkBoardPass method is used for checking if the generated board passed the sudoku rules.
	 * 
	 * @param sudoku
	 */
	private boolean checkBoardPass(int[][] sudoku) {
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				if (checkViolated(sudoku, r, c)) {
					System.err.println("Board failed at " + r + " " + c);
					return false;
				}
			}
		}
		for (int i = 1; i < 10; i++) {
			if (checkQuardrant(sudoku, i)) {
				System.err.println("Board failed at quadrant " + i);
				return false;
			}
		}
		System.out.println("Board Pass!");
		return true;
	}

	/**
	 * printBoard method is used for
	 * 
	 * @param sudoku
	 */
	private void printBoard(int[][] sudoku) {
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				System.out.print(sudoku[r][c] == 0 ? " " : sudoku[r][c]);
			}
			System.out.println();
		}
	}

	/**
	 * checkViolated method is used for
	 * 
	 * @param sudoku
	 * @param r
	 * @param c
	 * @param random2
	 * @return
	 */
	private boolean checkViolated(int[][] sudoku, int r, int c, int random) {
		int[] row = getRow(sudoku, r);
		int[] col = getCol(sudoku, c);
		if (checkIfPresent(row, random) || checkIfPresent(col, random) || checkQuardrant(sudoku, r, c, random)) {
			return true;
		}
		return false;
	}

	private boolean checkViolated(int[][] sudoku, int r, int c) {
		if (sudoku[r][c] == 0) {
			return false; // unfilled spot. No need to check.
		}
		int[] row = getRow(sudoku, r);
		int[] col = getCol(sudoku, c);
		int random = sudoku[r][c];
		row[c] = 0;
		col[r] = 0;
		if (checkIfPresent(row, random) || checkIfPresent(col, random)) {
			return true;
		}
		return false;
	}

	/**
	 * checkQuardrant method is used for
	 * 
	 * @param sudoku
	 * @param r
	 * @param c
	 * @return
	 */
	private boolean checkQuardrant(int[][] sudoku, int q) {
		Set<Integer> checkSet = new HashSet<>();
		int[][] quad = getQuadrant(sudoku, q);
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (quad[i][j] == 0) {
					continue;
				}
				if (!checkSet.add(quad[i][j])) {
					return true;
				}
			}
		}
		return false;
	}

//	/**
//	 * checkQuardrants method is used for
//	 * 
//	 * @param sudoku
//	 * @return
//	 */
//	private  boolean checkQuardrants(int[][] sudoku) {
//		check: for (int q = 1; q <= 9; q++) {
//			Set<Integer> vals = new HashSet<Integer>();
//			int[][] quad = getQuadrant(sudoku, q);
//			for (int i = 0; i < 3; i++) {
//				for (int j = 0; j < 3; j++) {
//					if (quad[i][j] == 0) {
//						break check;
//					}
//					vals.add(Integer.valueOf(String.valueOf(quad[i][j])));
//				}
//			}
//			if (vals.size() == 9) {
//				continue;
//			} else {
//				return true;
//			}
//		}
//		return false;
//	}

	/**
	 * checkQuardrant method is used for
	 * 
	 * @param sudoku
	 * @param r
	 * @param c
	 * @param random
	 * @return
	 */
	private boolean checkQuardrant(int[][] sudoku, int r, int c, int random) {
		int[][] quad = getQuadrant(sudoku, r, c);
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (quad[i][j] == 0) {
					return false;
				}
				if (quad[i][j] == random) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * getQuadrant method is used for
	 * 
	 * @param sudoku
	 * @param r
	 * @param c
	 * @return
	 */
	private int[][] getQuadrant(int[][] sudoku, int r, int c) {
		return getQuadrant(sudoku, quadMap[r][c]);
	}

	/**
	 * getQuadrant method is used for
	 * 
	 * @param sudoku
	 * @param q
	 * @return
	 */
	private int[][] getQuadrant(int[][] sudoku, int q) {
		int[][] quad = new int[3][3];
		int rs, re, cs, ce;
		if (q < 4) {
			rs = 0;
			re = 2;
		} else if (q > 3 && q < 7) {
			rs = 3;
			re = 5;
		} else {
			rs = 6;
			re = 8;
		}
		if (q == 1 || q == 4 || q == 7) {
			cs = 0;
			ce = 2;
		} else if (q == 2 || q == 5 || q == 8) {
			cs = 3;
			ce = 5;
		} else {
			cs = 6;
			ce = 8;
		}
		for (int i = 0, r = rs; r <= re; r++, i++) {
			for (int j = 0, c = cs; c <= ce; c++, j++) {
				quad[i][j] = sudoku[r][c];
			}
		}
		return quad;
	}

	/**
	 * checkIfPresent method is used for
	 * 
	 * @param row
	 * @param random2
	 * @return
	 */
	private boolean checkIfPresent(int[] seq, int random) {
		for (int i = 0; i < 9; i++) {
			if (seq[i] == random) {
				return true;
			}
		}
		return false;
	}

	/**
	 * getRow method is used for
	 * 
	 * @param sudoku
	 * @param r
	 * @return
	 */
	private int[] getRow(int[][] sudoku, int r) {
		return new int[] {
				sudoku[r][0], sudoku[r][1], sudoku[r][2], sudoku[r][3], sudoku[r][4], sudoku[r][5], sudoku[r][6],
				sudoku[r][7], sudoku[r][8] };
	}

	/**
	 * getCol method is used for
	 * 
	 * @param sudoku
	 * @param c
	 * @return
	 */
	private int[] getCol(int[][] sudoku, int c) {
		return new int[] {
				sudoku[0][c], sudoku[1][c], sudoku[2][c], sudoku[3][c], sudoku[4][c], sudoku[5][c], sudoku[6][c],
				sudoku[7][c], sudoku[8][c] };
	}

	/**
	 * getRandom method is used for
	 * 
	 * @return
	 */
	private int getRandom() {
		return random.nextInt(9) + 1;
	}

	/**
	 * generateRowSequences method is used for genreating all row sequence in a sudoku board possible. There are 9! =
	 * 362880 possibilities
	 * 
	 */
	private void generateRowSequences() {
		int count = 0;
		for (int i = 123456789; i <= 987654321; i++) {
			if (checkIfRow(i)) {
				count++;
				System.out.println(i);
			}
		}
		System.out.println("Count " + count);
	}

	/**
	 * checkIfRow method is used for checking if the number qualifies for a row.
	 * 
	 * @return
	 */
	private boolean checkIfRow(int i) {
		String s = String.valueOf(i);
		if (s.contains("0")) { // should not contain a 0
			return false;
		}
		int[] nums = getNumFromRow(s);
		for (int k = 0; k < 9; k++) {
			for (int j = i + 1; j < 9; j++) {
				if (nums[k] == nums[j]) { // same number repeated
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * getSum method is used for calculating the addition of all elements in a row
	 * 
	 * @param nums
	 * @return
	 */
	private int getSum(int[] nums) {
		int sum = 0;
		for (int i = 0; i < 9; i++) {
			sum += nums[i];
		}
		return sum;
	}

	/**
	 * getNumFromRow method is used for converting a string of row into array of integers of individual cell data.
	 * 
	 * @param i
	 * @return
	 */
	private int[] getNumFromRow(String s) {
		return new int[] {
				ifs(s, 0), ifs(s, 1), ifs(s, 2), ifs(s, 3), ifs(s, 4), ifs(s, 5), ifs(s, 6), ifs(s, 7), ifs(s, 8) };
	}

	/**
	 * ifs method is used for parsing from int from string
	 * 
	 * @param s
	 * @param i
	 */
	private int ifs(String s, int i) {
		return Integer.parseInt(String.valueOf(s.charAt(i)));
	}
}
