package org.adorsys.xlseasy.annotation.filter;

import java.util.HashMap;
import java.util.Map;

import org.adorsys.xlseasy.annotation.ErrorCodeSheet;

/**
 * The Class AnnotationSystemException.
 * 
 * @author Sandro Sonntag
 */
public class AnnotationSystemException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The error code. */
	private ErrorCodeSheet errorCode;

	/** The cause. */
	private Throwable cause;

	/** The values. */
	private Map<String, Object> values;

	/**
	 * Instantiates a new annotation system exception.
	 * 
	 * @param code
	 *            the error code
	 * @param cause
	 *            the cause
	 * @param values
	 *            the values
	 */
	public AnnotationSystemException(ErrorCodeSheet code, Throwable cause,
			Map<String, Object> values) {
		this.errorCode = (code == null) ? ErrorCodeSheet.UNKNOWN : code;
		this.cause = cause;
		this.values = (values == null) ? new HashMap<String, Object>() : values;
	}

	/**
	 * Instantiates a new annotation system exception.
	 * 
	 * @param code
	 *            the code
	 * @param cause
	 *            the cause
	 */
	public AnnotationSystemException(ErrorCodeSheet code, Throwable cause) {
		this(code, cause, null);
	}

	/**
	 * Instantiates a new annotation system exception.
	 * 
	 * @param code
	 *            the code
	 */
	public AnnotationSystemException(ErrorCodeSheet code) {
		this(code, null, null);
	}

	/**
	 * Adds the value to the map.
	 * 
	 * @param descriptor
	 *            the descriptor
	 * @param value
	 *            the value
	 * @return the annotation system exception
	 */
	public AnnotationSystemException addValue(String descriptor, Object value) {
		values.put(descriptor, value);
		return this;
	}

	/**
	 * Gets the error code.
	 * 
	 * @return errorCode the error code.
	 */
	public ErrorCodeSheet getErrorCode() {
		return errorCode;
	}

	/**
	 * Gets the cause.
	 * 
	 * @return cause the cause of the exception.
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