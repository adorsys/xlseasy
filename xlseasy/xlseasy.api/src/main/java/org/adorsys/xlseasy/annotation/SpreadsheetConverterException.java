package org.adorsys.xlseasy.annotation;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sandro Sonntag
 */
public class SpreadsheetConverterException extends Exception {

	private static final long serialVersionUID = 1L;

	private ErrorCodeSheet errorCode;

	private Throwable cause;
	
	private Map<String, Object> values;
	
	/**
	 * Instantiates a new spreadsheet converter exception.
	 *
	 * @param code the code
	 * @param cause the cause
	 * @param values the values
	 */
	public SpreadsheetConverterException(ErrorCodeSheet code, Throwable cause, Map<String, Object> values) {
		this.errorCode = code == null ? ErrorCodeSheet.UNKNOWN : code;
		this.cause = cause;
		this.values = values == null ? new HashMap<String, Object>() : values;
	}

	/**
	 * Instantiates a new spreadsheet converter exception.
	 *
	 * @param code the code
	 * @param cause the cause
	 */
	public SpreadsheetConverterException(ErrorCodeSheet code, Throwable cause) {
		this(code, cause, null);
	}

	/**
	 * Instantiates a new spreadsheet converter exception.
	 *
	 * @param code the code
	 */
	public SpreadsheetConverterException(ErrorCodeSheet code) {
		this(code, null, null);
	}

	/**
	 * Adds a value.
	 *
	 * @param descriptor the descriptor
	 * @param value the value
	 * @return the spreadsheet converter exception
	 */
	public SpreadsheetConverterException addValue(String descriptor, Object value) {
		values.put(descriptor, value);
		return this;
	}

	/**
	 * Gets the error code.
	 *
	 * @return the error code
	 */
	public ErrorCodeSheet getErrorCode() {
		return errorCode;
	}

	/**
	 * Gets the exception's cause.
	 * 
	 * @return the exception's cause
	 * */
	@Override
	public Throwable getCause() {
		return this.cause;
	}

	public String dump() {
		return values.toString();
	}	
}