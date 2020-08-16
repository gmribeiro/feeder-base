package org.assignment.feeder.exception;

public class ArticleNotFoundException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3059680823593087284L;

	public ArticleNotFoundException(String errorMessage) {
		super(errorMessage);
	}
	
	public ArticleNotFoundException(String errorMessage, Throwable t) {
		super(errorMessage, t);
	}
}
