package database_objects;

public class ProductBatchComp 
{
	int pbId; 	  // produktbatch'ets id
	int cbId;        // i området 1-99999999
	double tara;
	double netto;
	int oprId;					// operatør nummer

	
	public ProductBatchComp(int pbId, int cbId, double tara, double netto, int oprId)
	{
		this.pbId = pbId;
		this.cbId = cbId;
		this.tara = tara;
		this.netto = netto;
		this.oprId = oprId;
	}
	
	public int getPbId() { return pbId; }
	public void setPbId(int pbId) { this.pbId = pbId; }
	public int getRbId() { return oprId; }
	public void setRbId(int cbId) { this.cbId = cbId; }
	public double getTara() { return tara; }
	public void setTara(double tara) { this.tara = tara; }
	public double getNetto() { return netto; }
	public void setNetto(double netto) { this.netto = netto; }
	public int getOprId() { return oprId; }
	public void setOprId(int oprId) { this.oprId = oprId; }
	public String toString() { 
		return pbId + "\t" + cbId +"\t" + tara +"\t" + netto + "\t" + oprId ; 
	}
}
