package database_objects;

public class ProductBatchComp 
{
	int pbId; 	  // produktbatch'ets id
	int cbId;        // i området 1-99999999
	double tara;
	double netto;
	int oprId;					// operatør nummer
	int terminal;

	
	public ProductBatchComp(int pbId, int cbId, double tara, double netto, int oprId, int terminal)
	{
		this.pbId = pbId;
		this.cbId = cbId;
		this.tara = tara;
		this.netto = netto;
		this.oprId = oprId;
		this.terminal = terminal;
	}
	
	public int getPbId() { return pbId; }
	public void setPbId(int pbId) { this.pbId = pbId; }
	public int getCbId() { return cbId; }
	public void setCbId(int cbId) { this.cbId = cbId; }
	public double getTara() { return tara; }
	public void setTara(double tara) { this.tara = tara; }
	public double getNetto() { return netto; }
	public void setNetto(double netto) { this.netto = netto; }
	public int getOprId() { return oprId; }
	public void setOprId(int oprId) { this.oprId = oprId; }
	public int getTerminal(){return terminal;}
	public void setTerminal(int terminal){this.terminal = terminal;}
	public String toString() { 
		return pbId + "\t" + cbId +"\t" + tara +"\t" + netto + "\t" + oprId ; 
	}
	@Override
	public boolean equals(Object obj)
	{
		ProductBatchComp test = (ProductBatchComp)obj;
		if(test.getPbId() == this.pbId
				&& test.getCbId() == this.cbId
				&& test.getNetto() == this.netto
				&& test.getTara() == this.tara
				&& test.getOprId() == this.oprId)
			return true;
		else
			return false;
	}
}
