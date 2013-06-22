import controller.ASE;
import controller.Boundary;
import controller.Simulator;
import database.DALException;
import database.DatabaseAccess;

/**
 * This class contains the starting point of the console application.
 * @author Jens Werner Nielsen (s123115).
 */
public class Main {
	
	/**
	 * This function is the starting point of the console application.
	 * @param args This parameter is not used.
	 */
	public static void main(String[] args) {

		// Open keyboard input.
		Boundary boundary = new Boundary();
		
		menu:
		while (true) {
		
			System.out.println();
			System.out.println("1. Start simulator");
			System.out.println("2. Start ASE");
			System.out.println("3. Reset database");
			System.out.println("0. Exit");
			System.out.println();

			// Wait for the user to type a number between zero and three.
			int response;
			try {
				response = boundary.readInt("", 0, 3);
			} catch (Exception e) {
				break;
			}

			switch (response) {
				case 1:
					
					// The user has chosen to start the simulator.
					new Simulator(boundary);
					break;
					
				case 2:
					
					// The user has chosen to start the ASE.
					new ASE(boundary);
					break;
					
				case 3:
				
					// The user has chosen to reset the database.
					resetDatabase();
					break;
				
				default:
					
					// The user has chosen to close the console application.
					break menu;
					
			}
			
		}
		
		// Close keyboard input.
		boundary.close();
		
	}

	/**
	 * This function calls DatabaseAccess.reset() to reset the database.
	 */
	private static void resetDatabase() {
		
		System.out.println();
		System.out.println("Resetting database...");
		
		try {
			DatabaseAccess.reset();
			System.out.println();
			System.out.println("The database is now reset");
		} catch (DALException e) {
			System.err.println();
			System.err.println("Cannot reset database (" + e.getMessage() + ")");
		}
		
	}

}
