xlseasy
=======

We use Apache POI to generate Excel Spreadsheets with some Java implementations.
<<<<<<< HEAD
=======


Simplifies mapping between xls files and java bean based on annotations
=======================================================================
https://code.google.com/p/xlseasy/

	@Sheet(autoSizeColumns=true,
                freezeColumnHeader=true,
                columnOrder={"name", "amount", "description", "topSeller", "validFrom"})
	public class Product {

        @SheetColumn(columnName="Name", headerStyle=@SheetCellStyle(align=CellAlign.CENTER, fontStyleBold=true))
        private String name;
        
        @SheetColumn(headerStyle=@SheetCellStyle(align=CellAlign.CENTER, fontStyleBold=true))
        private String description;
        
        @SheetColumn(headerStyle=@SheetCellStyle(align=CellAlign.CENTER, fontStyleBold=true))
        private Double amount;
        
        @SheetColumn(headerStyle=@SheetCellStyle(align=CellAlign.CENTER, fontStyleBold=true))
        private Boolean topSeller;
        
        @SheetColumn(headerStyle=@SheetCellStyle(align=CellAlign.CENTER, fontStyleBold=true))
        private Date validFrom;
	}

===

	org.adorsys.xlseasy.impl.proc.SpreadsheetServiceImpl spreadSheetImpl = new org.adorsys.xlseasy.impl.proc.SpreadsheetServiceImpl();

	Product p = spreadSheetImpl.loadSpreadsheet(stream, Product.class);

	spreadSheetImpl.saveSpreadsheet(Product.class, p, outputStream);

===

A released and downloadable version comes soon.

Installation
============

The library is avalible as maven artifact. Put this dependencies in your maven POM:

	<project>
		..
		<dependency>
      		<groupId>org.adorsys.xlseasy</groupId>
     		<artifactId>xlseasy.api</artifactId>
			<version>1.3-CR5</version>
		</dependency>
		<dependency>
    		<groupId>org.adorsys.xlseasy</groupId>
   			<artifactId>xlseasy.impl</artifactId>
    		<version>1.3-CR5</version>
		</dependency>
		<dependency>
    	  	<groupId>org.adorsys.xlseasy</groupId>
    	  	<artifactId>xlseasy.cbe</artifactId>
    	  	<version>1.3-CR5</version>
		</dependency>
	</project>
>>>>>>> 7e3715ce736e8c3959282cd2ae5ae3de761c480b
