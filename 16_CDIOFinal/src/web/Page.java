package web;

public class Page {

	private String name;
	private int rightsRequired;
	
	//# New
	
	public Page(String name, int rightsRequired) {
		
		this.name = name;
		this.rightsRequired = rightsRequired;
		
	}
	
	//# Properties
	
	public String getName() {
		
		return name;
	}
	public int getRightsRequired() {
		
		return rightsRequired;
	}
	
}
