package chess2.customEXC;

public class InCheck extends Exception
{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InCheck()
	{
		super();
		
	}
	
	public InCheck(String message)
	{
		super(message);
	}
}