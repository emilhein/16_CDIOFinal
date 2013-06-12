package database;

public class DALException extends Exception
{
	private static final long serialVersionUID = 2946319560906255336L;
	public DALException(String message) { super(message); }    
	public DALException(Exception e) { super(e); }
}
