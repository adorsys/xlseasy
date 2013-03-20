import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class MyExcelFunction {

	protected static final char[] ALPHABET = { 'A', 'B', 'C', 'D', 'E', 'F',
			'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
			'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	protected static final String[] name = { "Abbey", "Abigail", "Agnes",
			"Allyson", "Alisa", "Audrey", "Avril", "Bailey", "Barbara",
			"Belinda", "Benita", "Bernard", "Bertha", "Cadence", "Cathy",
			"Clara", " Ciel", "Claudia", "Corra", "Cindy", "Daniela", "Daphne",
			"Desiree", "Gerald", "Emile", "Bernd", "Thomas", "James",
			"Sanchez", "Anthony", "Stefan", "Martina", "Jana", "Sarah",
			"Nicole" };

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

	// returns the reference (eg. C11, E4, AD1305 etc.) for the cell to the
	// targeted coordinates (row, col).
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

	// returns the name (eg. E for E4, AK for AK32, etc...) for the cell to the
	// targeted coordinates (row, col).
	// @using: getCellByReference(int row, int col)
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

	// returns the row's number (eg. 4 for E4, 32 for AK32, etc...) for the cell
	// to the targeted coordinates (row, col).
	// @using: getCellByReference(int row, int col)
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

	// checks if the parameter is an alphabet
	public boolean isInAlphabet(char search) {
		for (int i = 0; i < ALPHABET.length; i++) {
			if (search == ALPHABET[i])
				return true;
		}
		return false;
	}

	/**
	 * We use from here Apache POI's function to handles targeted elements
	 * (Workbook, Sheet, Row, Cell, ...): org.apache.poi.hssf
	 * */

	// sets and returns the default style to the targeted workbook
	public HSSFCellStyle getMyDefaultStyle(HSSFWorkbook book) throws Exception {
		// First of all we have to create the style for this book
		HSSFCellStyle style = book.createCellStyle();

		// We establish the background color
		style.setFillForegroundColor(HSSFColor.LIME.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setAlignment((short) 0x2);

		// We establish a new font for this book
		HSSFFont font = book.createFont();
		font.setColor(HSSFColor.GREEN.index);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short) 12);
		style.setFont(font);

		return style;
	}

	// sets the color of the targeted cell
	public void setMyCellColor(HSSFWorkbook book, HSSFCell cell, short color)
			throws Exception {

		// First of all we have to create the style for this book
		HSSFCellStyle style = book.createCellStyle();

		// We establish the color for this cell
		HSSFFont font = book.createFont();
		font.setColor(color);
		cell.setCellStyle(style);
	}

	// sets font's properties of the targeted cell
	public void setMyCellFont(HSSFWorkbook book, HSSFCell cell,
			short boldWeight, short fontHeightInPoints) throws Exception {

		// First of all we have to create the style for this book
		HSSFCellStyle style = book.createCellStyle();

		// We establish a new font for this book
		HSSFFont font = book.createFont();
		font.setBoldweight(boldWeight);
		font.setFontHeightInPoints(fontHeightInPoints);
		style.setFont(font);
		cell.setCellStyle(style);
	}

	// set the alignment of the targeted cell
	public void setMyCellAlignment(HSSFWorkbook book, HSSFCell cell,
			short alignment) throws Exception {

		// First of all we have to create the style for this book
		HSSFCellStyle style = book.createCellStyle();

		// We establish a new font for this book
		style.setAlignment(alignment);
		cell.setCellStyle(style);
	}
}
