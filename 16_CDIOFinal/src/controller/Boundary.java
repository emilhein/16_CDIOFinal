package controller;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Boundary extends Thread {

	private ArrayList<String> cache = new ArrayList<String>();
	private boolean closing = false;
	
	//# New
	
	public Boundary() {
		
		start();
		
	}
	
	//# Close
	
	public void close() {
		
		closing = true;
				
	}
	
	//# Thread
	
	@Override
	public void run() {

		Scanner scanner = new Scanner(System.in);
		String line;
		
		while (!closing) {
			
			line = scanner.nextLine();
			
			if (line != null && line.length() > 0) {
				synchronized (cache) {
					cache.add(line);
				}
			}
			
		}
		
		scanner.close();
		
	}
	
	//# Functions
	
	public String readString(String prefix) {

		Pattern pattern = Pattern.compile("^" + prefix + "(.+)$");
		Matcher matcher;
		
		while (true) {
						
			synchronized (cache) {
				for (int i = 0; i < cache.size(); i++) {
					matcher = pattern.matcher(cache.get(i));
					if (matcher.matches()) {
						cache.remove(i);
						return matcher.group(1);
					}
				}
			}
			
			try {
				sleep(100);
			} catch (InterruptedException e) {
			}
			
		}
		
	}
	public int readInt(String prefix, int minimum, int maximum) {
		
		int temp;
		
		while (true) {
			try {
				temp = Integer.parseInt(readString(prefix));
				if (temp < minimum) {
					System.out.println("The number is less than " + minimum + ".");
				} else if (temp > maximum) {
					System.out.println("The number is greater than " + maximum + ".");
				} else {
					return temp;
				}
			} catch(Exception e) {
				System.out.println("Invalid number.");
			}
		}
		
	}
	public double readDouble(String prefix, double minimum, double maximum) {
		
		double temp;
		
		while (true) {
			try {
				temp = Double.parseDouble(readString(prefix));
				if (temp < minimum) {
					System.out.println("The number is less than " + minimum + ".");
				} else if (temp > maximum) {
					System.out.println("The number is greater than " + maximum + ".");
				} else {
					return temp;
				}
			} catch(Exception e) {
				System.out.println("Invalid number.");
			}
		}
		
	}
	
}
