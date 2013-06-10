import controller.*;

public class Main {
	
	public static void main(String[] args) {
		
		Boundary boundary = new Boundary();

		System.out.println();
		System.out.println(" 1. Simulator");
		System.out.println(" 2. ASE");
		System.out.println();
		
		int response = boundary.readInt(1, 2);
		
		System.out.println();
		
		if (response == 1) {
			new Simulator(boundary);
		} else {
			new ASE(boundary);
		}
		
	}

}
