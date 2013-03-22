package org.adorsys.xlseasy.annotation;


// TODO: Auto-generated Javadoc
/**
 * The Enum ErrorCodeSheet.
 *
 * @author Sandro Sonntag <info@adorsys.de>
 */
public enum ErrorCodeSheet {
	
	/** The unknown. */
	UNKNOWN,
	
	/** The converter init error. */
	CONVERTER_INIT_ERROR,
	
	/** The wrong converter class type. */
	WRONG_CONVERTER_CLASS_TYPE,
	
	/** The unknown cell format. */
	UNKNOWN_CELL_FORMAT,
	
	/** The class has no workbook annotation. */
	CLASS_HAS_NO_WORKBOOK_ANNOTATION, 
	
	/** The class has no sheet annotation. */
	CLASS_HAS_NO_SHEET_ANNOTATION,
	
	/** The read bean data error. */
	READ_BEAN_DATA_ERROR,
	
	/** The wrong sheetdata type. */
	WRONG_SHEETDATA_TYPE,
	
	/** The store bean data error. */
	STORE_BEAN_DATA_ERROR,
	
	/** The save xls failed. */
	SAVE_XLS_FAILED,
	
	/** The load xls failed. */
	LOAD_XLS_FAILED, 
	
	/** The no converter for type. */
	NO_CONVERTER_FOR_TYPE,
	
	/** The instanciate wb bean failed. */
	INSTANCIATE_WB_BEAN_FAILED, 
	
	/** The instanciate record bean failed. */
	INSTANCIATE_RECORD_BEAN_FAILED,
	
	/** The not a workbook session. */
	NOT_A_WORKBOOK_SESSION,
	
	/** The not a sheettype session. */
	NOT_A_SHEETTYPE_SESSION, 
	
	/** The column definition from column order not found. */
	COLUMN_DEFINITION_FROM_COLUMN_ORDER_NOT_FOUND, 
	
	/** The uncompatibe type for this converter. */
	UNCOMPATIBE_TYPE_FOR_THIS_CONVERTER, 
	
	/** The bean introspection exception. */
	BEAN_INTROSPECTION_EXCEPTION, 
	
	/** The formatter instanciation exception. */
	FORMATTER_INSTANCIATION_EXCEPTION,
	
	/** The separator character not allowed in key string. */
	SEPARATOR_CHARACTER_NOT_ALLOWED_IN_KEY_STRING,
	
	/** The separator character not allowed string value. */
	SEPARATOR_CHARACTER_NOT_ALLOWED_STRING_VALUE,
	
	/** The referenced sheet does not provide key annotation. */
	REFERENCED_SHEET_DOES_NOT_PROVIDE_KEY_ANNOTATION,
	
	/** The field with name not found. */
	FIELD_WITH_NAME_NOT_FOUND,
	
	/** The circular dependencies not allowed. */
	CIRCULAR_DEPENDENCIES_NOT_ALLOWED;
}
