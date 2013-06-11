package controller;

import java.util.Scanner;

public class Boundary {

	private Scanner scanner = new Scanner(System.in);
	
	//# Close
	
	public void close() {
		
		scanner.close();
		
	}
	
	//# Functions
	
	public String readString() {
		
		String temp;
		
		while (true) {
			temp = scanner.nextLine();
			if (temp.length() > 0) {
				return temp;
			}
		}
		
	}
	public int readInt(int minimum, int maximum) {
		
		int temp;
		
		while (true) {
			try {
				temp = scanner.nextInt();
				if (temp < minimum) {
					System.out.println("The number is less than " + minimum + ".");
				} else if (temp > maximum) {
					System.out.println("The number is greater than " + maximum + ".");
				} else {
					return temp;
				}
			} catch(Exception e) {
				scanner.skip(".*");
				System.out.println("Invalid number.");
			}
		}
		
	}
	public double readDouble(double minimum, double maximum) {
		
		double temp;
		
		while (true) {
			try {
				temp = scanner.nextDouble();
				if (temp < minimum) {
					System.out.println("The number is less than " + minimum + ".");
				} else if (temp > maximum) {
					System.out.println("The number is greater than " + maximum + ".");
				} else {
					return temp;
				}
			} catch(Exception e) {
				scanner.skip(".*");
				System.out.println("Invalid number.");
			}
		}
		
	}
	
}
