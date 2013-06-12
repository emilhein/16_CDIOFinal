package database_objects;

public class CommodityBatch
{
	int cbId;                     // i området 1-99999999
	int commodityId;             // i området 1-99999999
	double maengde;             // kan være negativ 

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
}
