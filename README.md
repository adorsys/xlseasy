xlseasy
=======

We use Apache POI to generate Excel Spreadsheets with some Java implementations.


Simplifies mapping between xls files and java bean based on annotations
=======================================================================

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

org.adorsys.xlseasy.impl.proc.SpreadsheetServiceImpl spreadSheetImpl = new org.adorsys.xlseasy.impl.proc.SpreadsheetServiceImpl();

Product p = spreadSheetImpl.loadSpreadsheet(stream, Product.class);

spreadSheetImpl.saveSpreadsheet(Product.class, p, outputStream);
