package database_objects;

/**
 * Operatør Data Access Objekt
 * 
 * @author mn/tb
 * @version 1.2
 */

public class Operator
{

	private int oprId;			
	private String oprName;					
	private String ini;
	private String cpr;
	private String password;         
	private int rights;

	public Operator(int oprId, String oprName, String ini, String cpr, String password, int rights)
	{
		this.oprId = oprId;
		this.oprName = oprName;
		this.ini = ini;
		this.cpr = cpr;
		this.password = password;
		this.rights = rights;
	}
    
    public int getOprId() { return oprId; }
	public void setOprId(int oprId) { this.oprId = oprId; }
	public String getOprName() { return oprName; }
	public void setOprName(String oprName) { this.oprName = oprName; }
	public String getIni() { return ini; }
	public void setIni(String ini) { this.ini = ini; }
	public String getCpr() { return cpr; }
	public void setCpr(String cpr) { this.cpr = cpr; }
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }
	public int getRights() { return rights; }
	public void setRights(int rights) { this.rights = rights; }
	public String toString() { return oprId + "\t" + oprName + "\t" + ini + "\t" + cpr + "\t" + password + "\t" + rights; }
	
	@Override
	public boolean equals(Object obj)
	{
		Operator test = (Operator)obj;
		if(test.oprId == this.oprId
		&& test.oprName.equals(this.oprName)
		&& test.cpr.equals(this.cpr) 
		&& test.ini.equals(this.ini) 
		&& test.password.equals(this.password) 
		&& test.rights == this.rights
		)
			return true;	
		else
			return false;
	}
}
