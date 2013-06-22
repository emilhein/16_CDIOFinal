package controller;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class handles keyboard input for a console application.
 * @author Jens Werner Nielsen (s123115).
 */
public class Boundary extends Thread {

	private ArrayList<String> cache = new ArrayList<String>();
	private boolean closing = false;
	
	//# New
	
	/**
	 * When this class gets initialized it starts a background thread that caches the keyboard input.
	 */
	public Boundary() {
		
		start();
		
	}
	
	//# Close
	
	/**
	 * This function closes the background thread. Please note that the thread will continue to run until the current line is finished.
	 */
	public void close() {
		
		closing = true;
				
	}
	
	//# Thread
	
	/**
	 * This function contains the code for the background thread.
	 */
	@Override
	public void run() {

		// Open a new scanner to read input from the keyboard.
		Scanner scanner = new Scanner(System.in);
		
		// Continue reading keyboard input until close() is called.
		while (!closing) {
			
			// Read next line.
			String line = scanner.nextLine();
			
			// Check the line contains at least one character.
			if (line != null && line.length() > 0) {
				
				// Lock the cache and add the line to it.
				synchronized (cache) {
					cache.add(line);
				}
				
			}
			
		}
		
		// Close the scanner.
		scanner.close();
		
	}
	
	//# Functions
	
	/**
	 * This function reads a line with a specific prefix from the cache. 
	 * @param prefix This parameter is a regex pattern the line must begin with. All other lines are ignored.
	 * @return The oldest line that has the right prefix.
	 * @throws Exception If close() is called while waiting on input from the user.
	 */
	public String readString(String prefix) throws Exception {

		// Create a new pattern based on the prefix parameter.
		Pattern pattern = Pattern.compile("^" + prefix + "(.+)$");
		Matcher matcher;
		
		// Continue to loop through the cache until close() is called, or a line matching the pattern is found.
		while (!closing) {
			
			// Lock the cache and loop through it to find a line that matches the pattern.
			synchronized (cache) {
				for (int i = 0; i < cache.size(); i++) {
					
					// Check to see if this line matches the pattern.
					matcher = pattern.matcher(cache.get(i));
					if (matcher.matches()) {
					
						// This line matches the pattern. Remove it from the cache and return the rest of the line after the prefix.
						cache.remove(i);
						return matcher.group(1);
					
					}
					
				}
			}
			
			// No lines matching the pattern was found, wait some time and try again.
			try {
				sleep(100);
			} catch (InterruptedException e) {
			}
			
		}

		throw new Exception("Boundary closed while waiting on input from the user.");
	}
	
	/**
	 * This function reads an integer with a specific prefix from the cache.
	 * @param prefix This parameter is a regex pattern the line must begin with. All other lines are ignored.
	 * @param minimum This parameter specifies the minimum value accepted.
	 * @param maximum This parameter specifies the maximum value accepted.
	 * @return The oldest integer that has the right prefix and are within the specified boundaries.
	 * @throws Exception If close() is called while waiting on input from the user.
	 */
	public int readInt(String prefix, int minimum, int maximum) throws Exception {

		// Continue to loop through the cache until close() is called, or a integer matching the boundaries is found.
		while (!closing) {
			try {
				
				// Reads the oldest line with the right prefix and parse it as an integer.
				int temp = Integer.parseInt(readString(prefix));
				
				// Return the integer if it is within the boundaries.
				if (temp < minimum) {
					System.out.println("The number is less than " + minimum + ".");
				} else if (temp > maximum) {
					System.out.println("The number is greater than " + maximum + ".");
				} else {
					return temp;
				}
				
			} catch(Exception e) {
				
				// The line did not contain a valid integer.
				System.out.println("Invalid number.");
				
			}
		}
		
		throw new Exception("Boundary closed while waiting on input from the user.");
	}
	
	/**
	 * This function reads a double with a specific prefix from the cache.
	 * @param prefix This parameter is a regex pattern the line must begin with. All other lines are ignored.
	 * @param minimum This parameter specifies the minimum value accepted.
	 * @param maximum This parameter specifies the maximum value accepted.
	 * @return The oldest double that has the right prefix and are within the specified boundaries.
	 * @throws Exception If close() is called while waiting on input from the user.
	 */
	public double readDouble(String prefix, double minimum, double maximum) throws Exception {

		// Continue to loop through the cache until close() is called, or a double matching the boundaries is found.
		while (!closing) {
			try {
				
				// Reads the oldest line with the right prefix and parse it as a double.
				double temp = Double.parseDouble(readString(prefix));
				
				// Return the double if it is within the boundaries.
				if (temp < minimum) {
					System.out.println("The number is less than " + minimum + ".");
				} else if (temp > maximum) {
					System.out.println("The number is greater than " + maximum + ".");
				} else {
					return temp;
				}
				
			} catch(Exception e) {
				
				// The line did not contain a valid double.
				System.out.println("Invalid number.");
			
			}
		}

		throw new Exception("Boundary closed while waiting on input from the user.");
	}
	
}
