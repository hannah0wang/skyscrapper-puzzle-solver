import java.io.FileNotFoundException;
import java.io.File;
import java.util.*;

/**
 * Reads in configurations of potential solutions to Skyscraper puzzles and
 * evaluates their validity before printing out both the grids and their status
 * as an answer to the puzzles.
 *
 * @author Hannah Wang <hannah.a.wang@vanderbilt.edu>
 * @version JavaSE-17
 */
public class Skyscraper {
	/**
	 * The driver method to process Skyscraper puzzle settings The task is to check
	 * their validity and then print the grids along with the results of those
	 * checks.
	 *
	 * @param theArgs is used for command line input.
	 */
	public static void main(final String[] theArgs) throws FileNotFoundException {
		if (theArgs.length != 1) {
			System.out.println("ERROR: Program must be run using the name of an input file that "
					+ "contains skyscraper information as a command line argument.");
		} else {
			// START FILLING YOUR CODE HERE
			Scanner myInput = null;
			try {
				File myFile = new File(theArgs[0]);
				myInput = new Scanner(myFile);

				while (myInput.hasNextLine()) {
					int[][] myArray = getSkyscrapers(myInput);
					boolean myValidity = validateSkyscrapers(myArray);

					printSkyscrapers(myArray, myValidity);
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (myInput != null) {
					myInput.close();
				}
			}
			// END FILLING YOUR CODE HERE
			System.out.println("COMPLETED PROCESSING SKYSCRAPERS");
		}
	}

	/**
	 * Prints the Skyscraper puzzle configuration and whether it is valid or not as
	 * a solution.
	 *
	 * @param theGrid     is a 2D integer array representing a configuration of
	 *                    integers as a potential answer to a Skyscraper puzzle.
	 * @param theValidity is a boolean value representing whether the 2D integer
	 *                    array is an acceptable solution or not.
	 */
	public static void printSkyscrapers(final int[][] theGrid, final boolean theValidity) {
		final StringBuilder sb = new StringBuilder();
		for (int row = 0; row < theGrid.length; row++) {
			for (int col = 0; col < theGrid[0].length; col++) {
				if (theGrid[row][col] == 0) {
					sb.append(" ");
				} else {
					sb.append(theGrid[row][col]);
				}
			}
			sb.append("\n");
		}
		if (theValidity) {
			sb.append("VALID");
		} else {
			sb.append("NOT VALID");
		}
		System.out.println(sb.toString());
	}

	/**
	 * Stores and returns content from Scanner file to a 6 x 6 array of integers.
	 * Corners have a value of 0 stored.
	 *
	 * @param theFile is a Scanner that reads input from txt file containing clues
	 *                and skyscraper grid.
	 */
	public static int[][] getSkyscrapers(final Scanner theFile) {
		int[][] myArray = new int[6][6];

		// initialize each element in array to corresponding integer in file
		for (int row = 0; row < 6; row++) {
			for (int col = 0; col < 6; col++) {
				// set corner values to 0
				if ((row == 0 && col == 0) || (row == 0 && col == 5) || (row == 5 && col == 0) || (row == 5 && col == 5)) {
					myArray[row][col] = 0;
					// set every other value to next integer in file
				} else {
					myArray[row][col] = theFile.nextInt();
				}
			}
		}

		return myArray;
	}

	/**
	 * Determines if the Skyscraper puzzle configuration is valid or not as a
	 * solution.
	 *
	 * @param theGrid is a 2D integer array representing a configuration of integers
	 *                as a potential answer to a Skyscraper puzzle.
	 */
	public static boolean validateSkyscrapers(final int[][] theGrid) {
		int myClue;
		boolean myValidity = false;
		int[][] mySkyscraper = onlySkyscrapers(theGrid);

		// check if there are duplicates in the array and return false if so
		if (!duplicates(mySkyscraper)) {
			return false;
		}

		// check if skyscraper is correct
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				// check top clues
				if (i == 0 && j > 0 && j < 5) {
					myClue = theGrid[i][j];
					// if clue is 1
					if (myClue == 1 && theGrid[i + 1][j] == 4) {
						myValidity = true;
						// if clue is 4
					} else if (myClue == 4 && theGrid[i + 1][j] == 1 && theGrid[i + 2][j] == 2 && theGrid[i + 3][j] == 3
							&& theGrid[i + 4][j] == 4) {
						myValidity = true;
						// if clue is 2
					} else if (myClue == 2 && theGrid[i + 2][j] == 4 && theGrid[i + 2][j] > theGrid[i + 1][j]) {
						myValidity = true;
					} else if (myClue == 2 && theGrid[i + 3][j] == 4 && theGrid[i + 1][j] > theGrid[i + 2][j]) {
						myValidity = true;
					} else if (myClue == 2 && theGrid[i + 4][j] == 4 && theGrid[i + 1][j] > theGrid[i + 2][j]
							&& theGrid[i + 1][j] > theGrid[i + 3][j]) {
						myValidity = true;
						// if clue is 3
					} else if (myClue == 3 && theGrid[i + 3][j] == 4 && theGrid[i + 2][j] > theGrid[i + 1][j]
							&& theGrid[i + 3][j] > theGrid[i + 2][j]) {
						myValidity = true;
					} else if (myClue == 3 && theGrid[i + 4][j] == 4 && theGrid[i + 2][j] > theGrid[i + 1][j]
							&& theGrid[i + 2][j] > theGrid[i + 3][j]) {
						myValidity = true;
					} else if (myClue == 3 && theGrid[i + 4][j] == 4 && theGrid[i + 1][j] > theGrid[i + 2][j]
							&& theGrid[i + 3][j] > theGrid[i + 2][j]) {
						myValidity = true;
					} else {
						return false;
					}
					// check left clues
					if (j == 0 && i > 0 && i < 5) {
						myClue = theGrid[i][j];
						// if clue is 1
						if (myClue == 1 && theGrid[i][j + 1] == 4) {
							myValidity = true;
							// if clue is 4
						} else if (myClue == 4 && theGrid[i][j + 1] == 1 && theGrid[i][j + 2] == 2
								&& theGrid[i][j + 3] == 3 && theGrid[i][j + 4] == 4) {
							myValidity = true;
							// if clue is 2
						} else if (myClue == 2 && theGrid[i][j + 2] == 4 && theGrid[i][j + 2] > theGrid[i][j + 1]) {
							myValidity = true;
						} else if (myClue == 2 && theGrid[i][j + 3] == 4 && theGrid[i][j + 1] > theGrid[i][j + 2]) {
							myValidity = true;
						} else if (myClue == 2 && theGrid[i][j + 4] == 4 && theGrid[i][j + 1] > theGrid[i][j + 2]
								&& theGrid[i][j + 1] > theGrid[i][j]) {
							myValidity = true;
							// if clue is 3
						} else if (myClue == 3 && theGrid[i][j + 3] == 4 && theGrid[i][j + 2] > theGrid[i][j + 1]
								&& theGrid[i][j + 3] > theGrid[i][j + 2]) {
							myValidity = true;
						} else if (myClue == 3 && theGrid[i][j + 4] == 4 && theGrid[i][j + 2] > theGrid[i][j + 1]
								&& theGrid[i][j + 2] > theGrid[i][j + 3]) {
							myValidity = true;
						} else if (myClue == 3 && theGrid[i][j + 4] == 4 && theGrid[i][j + 1] > theGrid[i][j + 2]
								&& theGrid[i][j + 3] > theGrid[i][j + 2]) {
							myValidity = true;
						} else {
							return false;
						}
					}
					// check right clues
					if (j == 5 && i > 0 && i < 5) {
						myClue = theGrid[i][j];
						// if clue is 1
						if (myClue == 1 && theGrid[i][j - 1] == 4) {
							myValidity = true;
							// if clue is 4
						} else if (myClue == 4 && theGrid[i][j - 1] == 1 && theGrid[i][j - 2] == 2
								&& theGrid[i][j - 3] == 3 && theGrid[i][j - 4] == 4) {
							myValidity = true;
							// if clue is 2
						} else if (myClue == 2 && theGrid[i][j - 2] == 4 && theGrid[i][j - 2] > theGrid[i][j - 1]) {
							myValidity = true;
						} else if (myClue == 2 && theGrid[i][j - 3] == 4 && theGrid[i][j - 1] > theGrid[i + 2][j]) {
							myValidity = true;
						} else if (myClue == 2 && theGrid[i][j - 4] == 4 && theGrid[i][j - 1] > theGrid[i + 2][j]
								&& theGrid[i][j - 1] > theGrid[i][j - 3]) {
							myValidity = true;
							// if clue is 3
						} else if (myClue == 3 && theGrid[i][j - 3] == 4 && theGrid[i][j - 2] > theGrid[i + 1][j]
								&& theGrid[i][j - 3] > theGrid[i][j - 2]) {
							myValidity = true;
						} else if (myClue == 3 && theGrid[i][j - 4] == 4 && theGrid[i][j - 2] > theGrid[i + 1][j]
								&& theGrid[i][j - 2] > theGrid[i][j - 3]) {
							myValidity = true;
						} else if (myClue == 3 && theGrid[i][j - 4] == 4 && theGrid[i][j - 1] > theGrid[i + 2][j]
								&& theGrid[i][j - 3] > theGrid[i][j - 2]) {
							myValidity = true;
						} else {
							return false;
						}
					}
					// check bottom clues
					if (i == 5 && j > 0 && j < 5) {
						myClue = theGrid[i][j];
						// if clue is 1
						if (myClue == 1 && theGrid[i - 1][j] == 4) {
							myValidity = true;
							// if clue is 4
						} else if (myClue == 4 && theGrid[i - 1][j] == 1 && theGrid[i - 2][j] == 2
								&& theGrid[i - 3][j] == 3 && theGrid[i - 4][j] == 4) {
							myValidity = true;
							// if clue is 2
						} else if (myClue == 2 && theGrid[i - 2][j] == 4 && theGrid[i - 2][j] > theGrid[i - 1][j]) {
							myValidity = true;
						} else if (myClue == 2 && theGrid[i - 3][j] == 4 && theGrid[i - 1][j] > theGrid[i - 2][j]) {
							myValidity = true;
						} else if (myClue == 2 && theGrid[i - 4][j] == 4 && theGrid[i - 1][j] > theGrid[i - 2][j]
								&& theGrid[i - 1][j] > theGrid[i - 3][j]) {
							myValidity = true;
							// if clue is 3
						} else if (myClue == 3 && theGrid[i - 3][j] == 4 && theGrid[i - 2][j] > theGrid[i - 1][j]
								&& theGrid[i - 3][j] > theGrid[i - 2][j]) {
							myValidity = true;
						} else if (myClue == 3 && theGrid[i - 4][j] == 4 && theGrid[i - 2][j] > theGrid[i - 1][j]
								&& theGrid[i - 2][j] > theGrid[i - 3][j]) {
							myValidity = true;
						} else if (myClue == 3 && theGrid[i - 4][j] == 4 && theGrid[i - 1][j] > theGrid[i - 2][j]
								&& theGrid[i - 3][j] > theGrid[i - 2][j]) {
							myValidity = true;
						} else {
							return false;
						}
					}
				}
			}
		}
		return myValidity;
	}

	/**
	 * Returns a 4 x 4 array containing only the skyscraper of the array without any
	 * clues.
	 *
	 * @param theGrid is a 2D integer array representing a configuration of integers
	 *                as a potential answer to a Skyscraper puzzle.
	 */
	public static int[][] onlySkyscrapers(final int[][] theGrid) {
		int[][] mySkyscraper = new int[4][4];

		// traverse skyscrapers section of array and set the skyscraper to values of
		// this section
		for (int row = 1; row < 5; row++) {
			for (int col = 1; col < 5; col++) {
				mySkyscraper[row - 1][col - 1] = theGrid[row][col];
			}
		}
		return mySkyscraper;
	}

	/**
	 * Determines whether there are any duplicates in any row or column of the
	 * array.
	 *
	 * @param theSkyGrid is a 2D integer array with dimension 4 x 4 representing a
	 *                   configuration of integers as a potential answer to a
	 *                   Skyscraper puzzle without the clues.
	 */
	public static boolean duplicates(final int theSkyGrid[][]) {
		// Stores unique values from 1 to 4
		boolean[] myValues = new boolean[5];

		// Traverse each row of theSkyGrid
		for (int row = 0; row < 4; row++) {
			// Initialize myValues to false
			Arrays.fill(myValues, false);

			for (int col = 0; col < 4; col++) {
				int myVar = theSkyGrid[row][col];

				// Check if current row stores a duplicate value
				if (myValues[myVar]) {
					return false;
				}
				myValues[myVar] = true;
			}
		}

		// Traverse each column of theSkyGrid
		for (int col = 0; col < 4; col++) {
			// Initialize myValues to false
			Arrays.fill(myValues, false);

			for (int row = 0; row < 4; row++) {
				int myVar = theSkyGrid[col][row];

				// Check if current column stores a duplicate value
				if (myValues[myVar]) {
					return false;
				}
				myValues[myVar] = true;
			}
		}
		return true;
	}
}