package database_objects;

public class CommodityBatch
{
	private int cbId;                     // i omr�det 1-99999999
	private int commodityId;             // i omr�det 1-99999999
	private double maengde;             // kan v�re negativ 

	public CommodityBatch(int cbId, int commodityId, double maengde)
	{
		this.cbId = cbId;
		this.commodityId = commodityId;
		this.maengde = maengde;
	}
	
	public int getCbId() { return cbId; }
	public void setCbId(int cbId) { this.cbId = cbId; }
	public int getCommodityId() { return commodityId; }
	public void setCommodityId(int commodityId) { this.commodityId = commodityId; }
	public double getMaengde() { return maengde; }
	public void setMaengde(double maengde) { this.maengde = maengde; }
	public String toString() { 
		return cbId + "\t" + commodityId +"\t" + maengde; 
	}
	@Override
	public boolean equals(Object obj)
	{
		CommodityBatch test = (CommodityBatch)obj;
		if(test.getCbId() == this.cbId
				&& test.getCommodityId() == this.commodityId
				&& test.getMaengde() == this.maengde)
			return true;
		else
			return false;
	}
}
