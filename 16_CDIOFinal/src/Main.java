import controller.*;

public class Main {
	
	public static void main(String[] args) {
		
		Boundary boundary = new Boundary();

		System.out.println();
		System.out.println("1. Start simulator");
		System.out.println("2. Start ASE");
		System.out.println();
		
		if (boundary.readInt(1, 2) == 1) {
			new Simulator(boundary);
		} else {
			new ASE(boundary);
		}
		
	}

}
