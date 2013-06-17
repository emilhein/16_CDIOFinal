package special_objects;

import database_objects.Commodity;

public class Commodity_Sum {

	private int commodityId;                     
	private String commodityName;                
	private double sum;         

	public Commodity_Sum(int commodityId, String commodityName, double sum)
	{
		this.commodityId = commodityId;
		this.commodityName = commodityName;
		this.sum = sum;
	}

	public int getCommodityId() { return commodityId; }
	public void setCommodityId(int commodityId) { this.commodityId = commodityId; }
	public String getCommodityName() { return commodityName; }
	public void setCommodityName(String commodityName) { this.commodityName = commodityName; }
	public double getSum() { return sum; }
	public void setSum(double sum) { this.sum = sum; }

	@Override
	public String toString() { 
		return commodityId + "\t" + commodityName +"\t" + sum; 
	}
	@Override
	public boolean equals(Object obj)
	{
		Commodity_Sum test = (Commodity_Sum)obj;
		if(test.getCommodityId() == this.commodityId
				&& test.getCommodityName().equals(this.commodityName)
				&& test.getSum() == this.sum)
			return true;
		else
			return false;
	}
}
