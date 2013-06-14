package database_objects;

/**
 * Råvare Data Objekt
 * 
 * @author mn/sh/tb
 * @version 1.2
 */

public class Commodity 
{
    /** i området 1-99999999 vælges af brugerne */
    private int commodityId;                     
    /** min. 2 max. 20 karakterer */
    private String commodityName;                
    /** min. 2 max. 20 karakterer */
    private String supplier;         
	
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
    
    @Override
    public String toString() { 
		return commodityId + "\t" + commodityName +"\t" + supplier; 
	}
    @Override
	public boolean equals(Object obj)
    {
    	Commodity test = (Commodity)obj;
    	if(test.getCommodityId() == this.commodityId
    			&& test.getCommodityName().equals(this.commodityName)
    			&& test.getSupplier().equals(this.supplier))
    		return true;
    	else
    		return false;
    }
}
