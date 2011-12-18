package org.adorsys.xlseasy.annotation;

import java.rmi.UnexpectedException;
import java.util.Map;


/**
 * TODO set Javadoc for Class
 * @version $Id: $
 * @author sso
 */
public class SheetSystemException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param errorCode
	 * @param cause
	 * @param values
	 */
	public SheetSystemException(ErrorCodeSheet errorCode, Throwable cause,
			Map<String, Object> values) {
	}

	/**
	 * @param errorCode
	 * @param cause
	 */
	public SheetSystemException(ErrorCodeSheet errorCode, Throwable cause) {
	}

	/**
	 * @param errorCode
	 */
	public SheetSystemException(ErrorCodeSheet errorCode) {
	}

	public SheetSystemException addValue(String string, Object name) {
		// TODO Auto-generated method stub
		return null;
	}

}
