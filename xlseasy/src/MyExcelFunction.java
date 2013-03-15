public class MyExcelFunction {

	protected static final char[] ALPHABET = { 'A', 'B', 'C', 'D', 'E', 'F',
			'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
			'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	protected static final String[] name = { "Abbey", "Abigail", "Agnes", "Allyson",
			"Sanchez", "Anthony", "Stefan", "Martina", "Jana", "Sarah",
			"Nicole" };

	/**
	 * 			"Alisa", "Audrey", "Avril", "Bailey", "Barbara", "Belinda",
			"Benita", "Bernard", "Bertha", "Cadence", "Cathy", "Clara",
			" Ciel", "Claudia", "Corra", "Cindy", "Daniela", "Daphne",
			"Desiree", "Gerald", "Emile", "Bernd", "Thomas", "James",

	 * */
	
	/**
	 * Following functions should be used to manages the attribut *name*.
	 * */

	// returns the number of elements in the array
	public int getArrayLength() {
		return name.length;
	}

	// returns a random name form the array
	public String getRandomName() {
		return name[getRandomIndex()];
	}

	// returns a random index within the range of array
	public int getRandomIndex() {
		return (int) (Math.random() * (getArrayLength()));
	}

	// returns the longest word saved in the array
	public String getLongestWord() {
		String tmp = name[0];
		for (int i = 1; i < name.length; i++) {
			if (tmp.length() < name[i].length()) {
				tmp = name[i];
			}
		}
		return tmp;
	}
	
	
	/**
	 * Following functions should be used to setup cell/rows properties.
	 * */

	// returns the excel cell number (eg. C11, E4, AD1305 etc.) for this cell.
	public String getCellByReference(int row, int col) {
		StringBuffer retval = new StringBuffer();
		int tempcellnum = col;
		do {
			retval.insert(0, ALPHABET[tempcellnum % 26]);
			tempcellnum = (tempcellnum / 26) - 1;
		} while (tempcellnum >= 0);
		retval.append(row + 1);

		return retval.toString().toUpperCase();
	}

	// returns the excel cell name (eg. C, E, AB, ABC etc.) for this cell.
	public String getCellByName(int row, int col) {
		String cellName = getCellByReference(row, col);
		String result = new String();

		for (int i = 0; i < cellName.length(); i++) {
			if (isInAlphabet(cellName.charAt(i))) {
				result += cellName.charAt(i);
			} else {
				break;
			}
		}
		return result;
	}

	// returns the excel cell name (eg. C, E, AB, ABC etc.) for this cell.
	public String getCellByNumber(int row, int col) {
		String cellName = getCellByReference(row, col);
		String result = new String();

		for (int i = 0; i < cellName.length(); i++) {
			if (!isInAlphabet(cellName.charAt(i))) {
				result += cellName.charAt(i);
			}
		}
		return result;
	}

	// check if a char is in the alphabet
	public boolean isInAlphabet(char search) {
		for (int i = 0; i < ALPHABET.length; i++) {
			if (search == ALPHABET[i])
				return true;
		}
		return false;
	}
}
