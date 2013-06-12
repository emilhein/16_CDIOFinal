package database_objects;

/**
 * R�vare Data Objekt
 * 
 * @author mn/sh/tb
 * @version 1.2
 */

public class Commodity 
{
    /** i omr�det 1-99999999 v�lges af brugerne */
    int commodityId;                     
    /** min. 2 max. 20 karakterer */
    String commodityName;                
    /** min. 2 max. 20 karakterer */
    String supplier;         
	
	public Commodity(int commodityId, String commodityName, String supplier)
	{
		this.commodityId = commodityId;
		this.commodityName = commodityName;
		this.supplier = supplier;
	}
	
    public int getCommodityId() { return commodityId; }
    public void setCommodityId(int commodityId) { this.commodityId = commodityId; }
    public String getCommodityName() { return commodityName; }
    public void setCommodityName(String commodityName) { this.commodityName = commodityName; }
    public String getSupplier() { return supplier; }
    public void setSupplier(String supplier) { this.supplier = supplier; }
    public String toString() { 
		return commodityId + "\t" + commodityName +"\t" + supplier; 
	}
}
