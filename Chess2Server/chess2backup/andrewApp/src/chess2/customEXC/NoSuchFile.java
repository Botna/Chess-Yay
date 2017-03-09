package chess2.customEXC;



public class NoSuchFile extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoSuchFile()
	{
		super();
	}
	
	public NoSuchFile(String message)
	{
		super(message);
	}
}