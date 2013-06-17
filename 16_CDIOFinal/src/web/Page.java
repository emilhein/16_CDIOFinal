package web;

public class Page {

	private String title;
	private String name;
	private int rightsRequired;
	
	//# New
	
	public Page(String title, String name, int rightsRequired) {
		
		this.title = title;
		this.name = name;
		this.rightsRequired = rightsRequired;
		
	}
	
	//# Properties
	
	public String getTitle() {
		
		return title;
	}
	public String getName() {
		
		return name;
	}
	public int getRightsRequired() {
		
		return rightsRequired;
	}
	
}
