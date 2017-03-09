package chess2.customEXC;



public class BadMove extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BadMove()
	{
		super();
	}
	
	public BadMove(String message)
	{
		super(message);
	}
}