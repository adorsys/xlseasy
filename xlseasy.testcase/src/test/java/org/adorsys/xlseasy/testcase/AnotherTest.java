package org.adorsys.xlseasy.testcase;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.adorsys.xlseasy.annotation.SpreadsheetService;
import org.adorsys.xlseasy.boot.SpreadSheetServiceBootStrap;
import org.junit.Test;

/**
 * The class ImpTest.
 *
 * @author Marius Guede
 */
public class AnotherTest {
	
	private List<AnotherTest> anotherTest = new ArrayList<AnotherTest>();

	private String name, address;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public List<AnotherTest> getAnotherTest() {
		return anotherTest;
	}

	/**
	 * Sets the sheet suppliers.
	 *
	 * @param clients the new sheet suppliers
	 */
	public void setAnotherTest(List<AnotherTest> anotherTest) {
		this.anotherTest = anotherTest;
	}
	
	/**
	 * Saves a spreadsheet and then loads it.
	 *
	 * @throws FileNotFoundException the exception
	 */
	@Test
	public void testSaveSpreadsheet() throws FileNotFoundException {

		// Creates a new SpreadSheetServiceBootStrap.
		SpreadSheetServiceBootStrap bootStrap = new SpreadSheetServiceBootStrap();

		// Sets the workbook's class.
		bootStrap.setWorkbookKlass(AnotherTest.class);

		// Creates a new SpreadsheetService.
		SpreadsheetService spreadService = bootStrap.createSpreadService();

		// Creates a new ShopManagement to load the spreadsheet.
		AnotherTest anotherTest = new AnotherTest();

		// Adds clients and products into targeted Lists.
		AnotherTest ano1 = new AnotherTest();
		ano1.setName("Ano1");
		ano1.setAddress("Hauptstr. 1");
		
		AnotherTest ano2 = new AnotherTest();
		ano2.setName("Ano2");
		ano2.setAddress("Hauptstr. 2");
		
		AnotherTest ano3 = new AnotherTest();
		ano3.setName("Ano3");
		ano3.setAddress("Hauptstr. 3");
		
		anotherTest.getAnotherTest().add(ano1);
		anotherTest.getAnotherTest().add(ano1);
		anotherTest.getAnotherTest().add(ano2);

		// The file to create.
		String workbookFile = "target/anothertest.xls";

		// Creates a file stream to write to our file.
		OutputStream outputStream = new FileOutputStream(workbookFile);

		// Saves the spreadsheet.
		spreadService.saveSpreadsheet(AnotherTest.class, anotherTest,
				outputStream);

		
		// These lines will be printed if all tests returned true
		System.out.println("testSaveSpreadsheet: OK");
		System.out.println("The file has been successful created.");
	}
}