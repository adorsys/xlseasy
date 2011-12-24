package org.adorsys.xlseasy.annotation.filter;

import java.util.Map;

import org.adorsys.xlseasy.annotation.ErrorCodeSheet;

/**
 * TODO set Javadoc for Class
 * @version $Id: $
 * @author sso
 */
public class AnnotationSystemException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param errorCode
	 * @param cause
	 * @param values
	 */
	public AnnotationSystemException(ErrorCodeSheet errorCode, Throwable cause,
			Map<String, Object> values) {
	}

	/**
	 * @param errorCode
	 * @param cause
	 */
	public AnnotationSystemException(ErrorCodeSheet errorCode, Throwable cause) {
	}

	/**
	 * @param errorCode
	 */
	public AnnotationSystemException(ErrorCodeSheet errorCode) {
	}

	public AnnotationSystemException addValue(String string, String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
