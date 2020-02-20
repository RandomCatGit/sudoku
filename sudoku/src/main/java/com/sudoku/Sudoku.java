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
		sudoku = fixValuesByData(sudoku, new int[][] {
				{
						9, 8, 2, 7, 3, 6, 5, 4, 1 },
				{
						5, 4, 3, 9, 2, 1, 8, 6, 7 },
				{
						7, 6, 1, 5, 4, 8, 3, 9, 2 },
				{
						6, 2, 4, 1, 8, 9, 7, 5, 3 },
				{
						1, 5, 7, 3, 6, 2, 9, 8, 4 },
				{
						8, 3, 9, 4, 7, 5, 1, 2, 6 },
				{
						4, 9, 6, 8, 1, 3, 2, 7, 5 },
				{
						3, 7, 5, 2, 9, 4, 6, 1, 8 },
				{
						2, 1, 8, 6, 5, 7, 4, 3, 9 } });
//		generateRowSequences();
//		sudoku = fillQuadrant(sudoku, 1);
//		sudoku = fillQuadrant(sudoku, 2);
//		sudoku = fillQuadrant(sudoku, 3);
		sudoku = removeRandom(sudoku, 5);
		sudoku = generateSudokuBoard(sudoku);
		printBoard(sudoku);
//		checkBoardPass(sudoku);
//		printBorderedBoard(sudoku);
	}

	/**
	 * removeRandom method is used for removing removeCount number of random numbers from the already solved sudoku
	 * number array.
	 * 
	 * @param sudoku
	 * @param removeCount
	 * @return
	 */
	public int[][] removeRandom(int[][] sudoku, int removeCount) {
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
	 * fixValues method is used for fixing predefined sudoku values on board.
	 * 
	 * @param sudoku
	 * @param markData fixes values in board, values are fixed iff markdata value [1,9]
	 * @return
	 */
	public int[][] fixValuesByData(int[][] sudoku, int[][] markData) {
		int data;
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				data = markData[row][col];
				if (data > 0 && data < 10) {
					sudoku[row][col] = data;
				}
			}
		}
		return sudoku;
	}

	/**
	 * fixValues method is used for fixing predefined sudoku values on board.
	 * 
	 * @param sudoku
	 * @param markData fixes values in board, Format: new int[] {x, y, value}; x,y - [0,8]; value - [1,9]
	 * @return
	 */
	public int[][] fixValuesByRowCol(int[][] sudoku, int[]... markData) {
		for (int[] data : markData) {
			if (data[0] > 8 || data[1] > 8 || data[0] < 0 || data[1] < 0 || data[2] > 9 || data[2] < 1) {
				throw new IndexOutOfBoundsException("Illegal table/data values");
			} else {
				sudoku[data[0]][data[1]] = data[2];
				fixedMap[data[0]][data[1]] = 1;
			}
		}
		return sudoku;
	}

	/**
	 * printBorderedBoard method is used for printing sudoku data in a bordered and pretty way.
	 * 
	 * @param sudoku
	 */
	public void printBorderedBoard(int[][] sudoku) {
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
	 * fillQuadrant method is used for fill a particular quadrant.
	 * 
	 * @param sudoku
	 * @param quadrant is the quadrant to fill
	 * @return
	 */
	public int[][] fillQuadrant(int[][] sudoku, int quadrant) {
		int rowStart, rowEnd, columnStart, columnEnd, rnd;
		boolean check;
		if (quadrant < 4) {
			rowStart = 0;
			rowEnd = 2;
		} else if (quadrant > 3 && quadrant < 7) {
			rowStart = 3;
			rowEnd = 5;
		} else {
			rowStart = 6;
			rowEnd = 8;
		}
		if (quadrant == 1 || quadrant == 4 || quadrant == 7) {
			columnStart = 0;
			columnEnd = 2;
		} else if (quadrant == 2 || quadrant == 5 || quadrant == 8) {
			columnStart = 3;
			columnEnd = 5;
		} else {
			columnStart = 6;
			columnEnd = 8;
		}
		for (int row = rowStart; row <= rowEnd; row++) {
			for (int col = columnStart; col <= columnEnd; col++) {
				rnd = 0;
				check = true;
				while (check) {
					check = checkViolated(sudoku, row, col, rnd = getRandom());
					System.out.println("Running " + row + " " + col + " checking " + rnd);
					sudoku[row][col] = rnd;
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
	public int[][] generateSudokuBoard(int[][] sudoku) {
		int[][] backup = sudoku; // TODO keep memory of what numbers already tried and failed in each position. int[][][];
		int count = 0, rnd;
		boolean check;
		Set<Integer> exhaust;
		for (int r = 0; r < 9; r++) {
			exhaust = new HashSet<>();
			for (int c = 0; c < 9; c++) {
				if (sudoku[r][c] != 0) {
					exhaust.add(sudoku[r][c]);
					continue;
				}
				rnd = 0;
				check = true;
				while (check) {
					rnd = getRandom(exhaust);
					check = checkViolated(sudoku, r, c, rnd);
					exhaust.add(rnd);
//					System.out.println("Running " + r + " " + c + " checking " + rnd);
					count++;
//					printBorderedBoard(sudoku);
					if (exhaust.size() == 9) {
						exhaust = new HashSet<>();
//						System.out.println("Exausted. Backtracked to previous row");
						sudoku[r] = backup[r]; // backtracking
						if (r == 0) {
							c = -1;
							break;
						}
						r--;
						sudoku[r] = backup[r];
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
	public boolean checkBoardPass(int[][] sudoku) {
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
	public void printBoard(int[][] sudoku) {
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
	public boolean checkViolated(int[][] sudoku, int r, int c, int random) {
		int[] row = getRow(sudoku, r);
		int[] col = getCol(sudoku, c);
		if (checkIfPresent(row, random) || checkIfPresent(col, random) || checkQuardrant(sudoku, r, c, random)) {
			return true;
		}
		return false;
	}

	public boolean checkViolated(int[][] sudoku, int r, int c) {
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
	public boolean checkQuardrant(int[][] sudoku, int q) {
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
	public boolean checkQuardrant(int[][] sudoku, int r, int c, int random) {
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
	public int[][] getQuadrant(int[][] sudoku, int r, int c) {
		return getQuadrant(sudoku, quadMap[r][c]);
	}

	/**
	 * getQuadrant method returns a particular quadrant from the board.
	 * 
	 * @param sudoku
	 * @param q
	 * @return
	 */
	public int[][] getQuadrant(int[][] sudoku, int q) {
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
	 * checkIfPresent method is used to check if a particular number is present in the current sequence.
	 * 
	 * @param seq
	 * @param random
	 * @return
	 */
	public boolean checkIfPresent(int[] seq, int random) {
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
	public int[] getRow(int[][] sudoku, int r) {
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
	public int[] getCol(int[][] sudoku, int c) {
		return new int[] {
				sudoku[0][c], sudoku[1][c], sudoku[2][c], sudoku[3][c], sudoku[4][c], sudoku[5][c], sudoku[6][c],
				sudoku[7][c], sudoku[8][c] };
	}

	/**
	 * getRandom method is used for
	 * 
	 * @return
	 */
	public int getRandom() {
		return random.nextInt(9) + 1;
	}

	/**
	 * getRandom method is used for returning a value not present in exhaust set.
	 * 
	 * @param exhaust
	 * @return
	 */
	private int getRandom(Set<Integer> exhaust) {
		int check;
		while (exhaust.contains(check = getRandom()))
			;
		return check;
	}

	/**
	 * generateRowSequences method is used for genreating all row sequence in a sudoku board possible. There are 9! =
	 * 362880 possibilities
	 * 
	 */
	public void generateRowSequences() {
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
	public boolean checkIfRow(int i) {
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
	public int getSum(int[] nums) {
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
	public int[] getNumFromRow(String s) {
		return new int[] {
				ifs(s, 0), ifs(s, 1), ifs(s, 2), ifs(s, 3), ifs(s, 4), ifs(s, 5), ifs(s, 6), ifs(s, 7), ifs(s, 8) };
	}

	/**
	 * ifs method is used for parsing from int from string
	 * 
	 * @param s
	 * @param i
	 */
	public int ifs(String s, int i) {
		return Integer.parseInt(String.valueOf(s.charAt(i)));
	}
}
