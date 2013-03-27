package org.adorsys.xlseasy.annotation;

import java.util.HashMap;
import java.util.Map;

/**
 * The Class SheetSystemException.
 *
 * @author Sandro Sonntag <info@adorsys.de>
 */
public class SheetSystemException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The error code. */
	private ErrorCodeSheet errorCode;

	/** The cause. */
	private Throwable cause;
	
	/** The values. */
	private Map<String, Object> values;
	
	/**
	 * Instantiates a new sheet system exception.
	 *
	 * @param code the code
	 * @param cause the cause
	 * @param values the values
	 */
	public SheetSystemException(ErrorCodeSheet code, Throwable cause, Map<String, Object> values) {
		this.errorCode = code == null ? ErrorCodeSheet.UNKNOWN : code;
		this.cause = cause;
		this.values = values == null ? new HashMap<String, Object>() : values;
	}

	/**
	 * Instantiates a new sheet system exception.
	 *
	 * @param code the code
	 * @param cause the cause
	 */
	public SheetSystemException(ErrorCodeSheet code, Throwable cause) {
		this(code, cause, null);
	}

	/**
	 * Instantiates a new sheet system exception.
	 *
	 * @param code the code
	 */
	public SheetSystemException(ErrorCodeSheet code) {
		this(code, null, null);
	}

	/**
	 * Adds the value.
	 *
	 * @param descriptor the descriptor
	 * @param value the value
	 * @return the sheet system exception
	 */
	public SheetSystemException addValue(String descriptor, Object value) {
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