package Exception;
public class InputPigeonException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InputPigeonException()
	{
	    super( " ERROR - Champ vide ou des espaces dans le nom");
	}
	
	public InputPigeonException( String str)
	{
		super( "ERROR - Un pigeon possédant le nom " + str + " existe déjà");
	}
}