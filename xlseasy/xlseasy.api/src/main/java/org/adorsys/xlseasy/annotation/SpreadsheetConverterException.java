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
	 * @param code
	 * @param cause
	 * @param values
	 */
	public SpreadsheetConverterException(ErrorCodeSheet code, Throwable cause, Map<String, Object> values) {
		this.errorCode = code == null ? ErrorCodeSheet.UNKNOWN : code;
		this.cause = cause;
		this.values = values == null ? new HashMap<String, Object>() : values;
	}

	/**
	 * @param code
	 * @param cause
	 */
	public SpreadsheetConverterException(ErrorCodeSheet code, Throwable cause) {
		this(code, cause, null);
	}

	/**
	 * @param code
	 */
	public SpreadsheetConverterException(ErrorCodeSheet code) {
		this(code, null, null);
	}

	public SpreadsheetConverterException addValue(String descriptor, Object value) {
		values.put(descriptor, value);
		return this;
	}

	public ErrorCodeSheet getErrorCode() {
		return errorCode;
	}

	@Override
	public Throwable getCause() {
		return this.cause;
	}

	public String dump() {
		return values.toString();
	}
	
}
