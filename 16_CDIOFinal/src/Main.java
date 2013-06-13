import controller.*;
import database.DALException;
import database.DatabaseAccess;

public class Main {
	
	public static void main(String[] args) {

		Boundary boundary = new Boundary();
		
		menu:
		while (true) {
		
			System.out.println();
			System.out.println("1. Start simulator");
			System.out.println("2. Start ASE");
			System.out.println("3. Reset database");
			System.out.println("0. Exit");
			System.out.println();

			switch (boundary.readInt("", 0, 3)) {
				case 1:
					new Simulator(boundary);
					break;
				case 2:
					new ASE(boundary);
					break;
				case 3:
					resetDatabase();
					break;
				default:
					break menu;
			}

			
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
