package database_objects;

public class ProductBatch 
{
	int pbId;                     // i området 1-99999999
	int status;					// 0: ikke påbegyndt, 1: under produktion, 2: afsluttet
	int receptId;
	String startTime; //time stamp, format :'YYYY-MM-DD HH:MM:SS'
	String endTime;
	
	public ProductBatch(int pbId, int receptId, String startTime, String endTime, int status)
	{
		this.pbId = pbId;
		this.status = status;
		this.receptId = receptId;
		this.startTime = startTime;
		this.endTime = endTime;
		
	}
	
	public int getPbId() { return pbId; }
	public void setPbId(int pbId) { this.pbId = pbId; }
	public int getStatus() { return status; }
	public void setStatus(int status) { this.status = status; }
	public int getReceptId() { return receptId; }
	public void setReceptId(int receptId) { this.receptId = receptId; }
	public String getStartTime() {return this.startTime;}
	public void setStartTime(String startTime){this.startTime = startTime;}
	public String getEndTime() {return this.endTime;}
	public void setEndTime(String endTime){this.endTime = endTime;}
	public String toString() { return pbId + "\t" + status + "\t" + receptId + "\t" + startTime + "\t" + endTime; }
	@Override
	public boolean equals(Object obj)
	{
		ProductBatch test = (ProductBatch)obj;
		if(test.getPbId() == this.pbId
				&& test.getReceptId() == this.receptId
				&& test.getStartTime().equals(this.startTime)
				&& test.getEndTime().equals(this.endTime)
				&& test.getStatus() == this.status)
			return true;
		else
			return false;
	}
}

