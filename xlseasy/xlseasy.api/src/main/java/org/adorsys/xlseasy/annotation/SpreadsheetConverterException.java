package org.adorsys.xlseasy.annotation;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sandro Sonntag
 */
public class SpreadsheetConverterException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> values;
	
	/** Instantiates a new spreadsheet converter exception. */
	public SpreadsheetConverterException(ErrorCodeSheet code, Throwable cause, Map<String, Object> values) {
		this.errorCode = (code == null) ? ErrorCodeSheet.UNKNOWN : code;
		this.cause = cause;
		this.values = (values == null) ? new HashMap<String, Object>() : values;
	}

	/** Instantiates a new spreadsheet converter exception. */
	public SpreadsheetConverterException(ErrorCodeSheet code, Throwable cause) {
		this(code, cause, null);
	}

	/** Instantiates a new spreadsheet converter exception. */
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

	/** The error code. */
	private ErrorCodeSheet errorCode;
	public ErrorCodeSheet getErrorCode() {
		return errorCode;
	}

	/** The exception's cause. */
	private Throwable cause;
	@Override
	public Throwable getCause() {
		return this.cause;
	}

	public String dump() {
		return values.toString();
	}	
}