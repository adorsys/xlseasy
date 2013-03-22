package org.adorsys.xlseasy.annotation;

import java.util.HashMap;
import java.util.Map;


// TODO: Auto-generated Javadoc
/**
 * The Class SpreadsheetConverterException.
 *
 * @version $Id: $
 * @author Sandro Sonntag
 */
public class SpreadsheetConverterException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The error code. */
	private ErrorCodeSheet errorCode;
	
	/** The cause. */
	private Throwable cause;	
	
	/** The values. */
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
	 * Adds the value.
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

	/* (non-Javadoc)
	 * @see java.lang.Throwable#getCause()
	 */
	@Override
	public Throwable getCause() {
		return this.cause;
	}

	/**
	 * Dump.
	 *
	 * @return the string
	 */
	public String dump() {
		return values.toString();
	}	
}