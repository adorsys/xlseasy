package org.adorsys.xlseasy.annotation;

import java.util.Map;


/**
 * TODO set Javadoc for Class
 * @version $Id: $
 * @author sso
 */
public class SpreadsheetConverterException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param code
	 * @param cause
	 * @param values
	 */
	public SpreadsheetConverterException(ErrorCodeSheet code, Throwable cause,
			Map<String, Object> values) {
	}

	/**
	 * @param code
	 * @param cause
	 */
	public SpreadsheetConverterException(ErrorCodeSheet code, Throwable cause) {
	}

	/**
	 * @param code
	 */
	public SpreadsheetConverterException(ErrorCodeSheet code) {
	}

	public SpreadsheetConverterException addValue(String string,
			Class<?> objectType) {
		// TODO Auto-generated method stub
		return null;
	}

	public ErrorCodeSheet getErrorCode() {
		// TODO Auto-generated method stub
		return null;
	}

}
