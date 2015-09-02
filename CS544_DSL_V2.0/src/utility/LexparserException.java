package utility;

public class LexparserException extends RuntimeException{

	/**
	 * @see java.lang.RuntimeException#RuntimeException()
	 */
	public LexparserException(String msg)
	{
		super(msg);
	}
	
	/**
	 * @see java.lang.RuntimeException#RuntimeException(String, Throwable)
	 */
	public LexparserException(String msg, Throwable cause)
	{
		super(msg, cause);
	}
}
