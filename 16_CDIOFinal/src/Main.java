import controller.*;
import database.DALException;
import database.DatabaseAccess;

public class Main {
	
	public static void main(String[] args) {

		Boundary boundary = new Boundary();
		
		System.out.println();
		System.out.println("1. Start simulator");
		System.out.println("2. Start ASE");
		System.out.println("3. Reset database");
		System.out.println();

		switch (boundary.readInt("", 1, 3)) {
			case 1:
				new Simulator(boundary);
			case 2:
				new ASE(boundary);
			case 3:
				resetDatabase();
		}
				
		boundary.close();
		
	}
	
	private static void resetDatabase() {
		
		System.out.println();
		System.out.println("Resetting database...");
		System.out.println();
		
		try {
			DatabaseAccess.reset();
			System.out.println("The database is now reset");
		} catch (DALException e) {
			System.err.println("Cannot reset database (" + e.getMessage() + ")");
		}
		
	}

}
