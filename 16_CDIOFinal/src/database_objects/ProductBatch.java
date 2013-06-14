package database_objects;

public class ProductBatch 
{
	int pbId;                     // i området 1-99999999
	int status;					// 0: ikke påbegyndt, 1: under produktion, 2: afsluttet
	int receptId;
	String timeStamp; //time stamp, format :'YYYY-MM-DD HH:MM:SS'
	
	public ProductBatch(int pbId, int receptId, String timeStamp, int status)
	{
		this.pbId = pbId;
		this.status = status;
		this.receptId = receptId;
		this.timeStamp = timeStamp;
	}
	
	public int getPbId() { return pbId; }
	public void setPbId(int pbId) { this.pbId = pbId; }
	public int getStatus() { return status; }
	public void setStatus(int status) { this.status = status; }
	public int getReceptId() { return receptId; }
	public void setReceptId(int receptId) { this.receptId = receptId; }
	public String getTimeStamp() {return this.timeStamp;}
	public void setTimeStamp(String timeStamp){this.timeStamp = timeStamp;}
	public String toString() { return pbId + "\t" + status + "\t" + receptId + "\t" + timeStamp; }
	@Override
	public boolean equals(Object obj)
	{
		ProductBatch test = (ProductBatch)obj;
		if(test.getPbId() == this.pbId
				&& test.getReceptId() == this.receptId
				&& test.getTimeStamp().equals(this.timeStamp)
				&& test.getStatus() == this.status)
			return true;
		else
			return false;
	}
}

