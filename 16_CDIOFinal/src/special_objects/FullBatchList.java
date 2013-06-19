package special_objects;

public class FullBatchList {
	int CommodityId;
	String CommodityName;
	double nomNetto;
	double tolerance;
	double tara;
	double netto;
	int cbId;
	String oprIni;
	int terminal;
	
	public FullBatchList(int commodityId, String commodityName, double nomNetto, double tolerance, double tara, double netto, int cbId, String oprIni, int terminal) {
		CommodityId = commodityId;
		CommodityName = commodityName;
		this.nomNetto = nomNetto;
		this.tolerance = tolerance;
		this.tara = tara;
		this.netto = netto;
		this.cbId = cbId;
		this.oprIni = oprIni;
		this.terminal = terminal;
	}
	
	public int getCommodityId() {
		return CommodityId;
	}
	public void setCommodityId(int commodityId) {
		CommodityId = commodityId;
	}
	public String getCommodityName() {
		return CommodityName;
	}
	public void setCommodityName(String commodityName) {
		CommodityName = commodityName;
	}
	public double getNomNetto() {
		return nomNetto;
	}
	public void setNomNetto(double nomNetto) {
		this.nomNetto = nomNetto;
	}
	public double getTolerance() {
		return tolerance;
	}
	public void setTolerance(double tolerance) {
		this.tolerance = tolerance;
	}
	public double getTara() {
		return tara;
	}
	public void setTara(double tara) {
		this.tara = tara;
	}
	public double getNetto() {
		return netto;
	}
	public void setNetto(double netto) {
		this.netto = netto;
	}
	public int getCbId() {
		return cbId;
	}
	public void setCbId(int cbId) {
		this.cbId = cbId;
	}
	public String getOprId() {
		return oprIni;
	}
	public void setOprId(String oprIni) {
		this.oprIni = oprIni;
	}
	public int getTerminal() {
		return terminal;
	}
	public void setTerminal(int terminal) {
		this.terminal = terminal;
	}
	
	@Override
	public String toString() {
		return "FullBatchList [CommodityId=" + CommodityId + ", CommodityName="
				+ CommodityName + ", nomNetto=" + nomNetto + ", tolerance="
				+ tolerance + ", tara=" + tara + ", netto=" + netto + ", cbId="
				+ cbId + ", oprIni=" + oprIni + ", terminal=" + terminal + "]";
	}
	
}
