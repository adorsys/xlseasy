package org.adorsys.xlseasy.annotation;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sandro Sonntag
 */
public class SheetSystemException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> values;
	
	/** Instantiates a new sheet system exception. */
	public SheetSystemException(ErrorCodeSheet code, Throwable cause, Map<String, Object> values) {
		this.errorCode = code == null ? ErrorCodeSheet.UNKNOWN : code;
		this.cause = cause;
		this.values = values == null ? new HashMap<String, Object>() : values;
	}

	/** Instantiates a new sheet system exception. */
	public SheetSystemException(ErrorCodeSheet code, Throwable cause) {
		this(code, cause, null);
	}

	/** Instantiates a new sheet system exception. */
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

	/** Gets the error code. */
	private ErrorCodeSheet errorCode;
	public ErrorCodeSheet getErrorCode() {
		return errorCode;
	}

	private Throwable cause;
	@Override
	public Throwable getCause() {
		return this.cause;
	}

	public String dump() {
		return values.toString();
	}
}
