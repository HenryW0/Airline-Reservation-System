import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

/**
 * Assignment 2 Solutions for Airplane class
 * @author Henry Wahhab
 * @version 1.0 3/4/2023
 */

/**
 * An airplane contains the layout and arrangement of all seats on the flight and manages retrieving seats, collecting reserved seats, checking validity of seats,
 * reserving and canceling seats, reading/saving the CL34 file, and other methods regarding the seats of the plane
 */

public class Airplane {
	
	private Seat[][] layout;
	
	private static final int NUM_ROWS = 10;
	private static final int NUM_COLS = 40;
	
	private static final String SEAT_CHAR_FIRSTCLASS = "LK GFED BA";
	private static final String SEAT_CHARS = "LKJGFEDCBA";
	
	private static final int FIRSTCLASS_PRICE = 1000;
	private static final int ECONOMYPLUS_PRICE = 500;
	private static final int ECONOMY_PRICE = 250;
	
	private static final int INDEX_PLANE = 13; //This value corrects the index of the array to the numbers on the plane design
	
	private static ArrayList<Seat> ECON_CLASS_SEATS = new ArrayList<Seat>();
	private static ArrayList<Seat> ECON_PLUS_SEATS = new ArrayList<Seat>();
	private static ArrayList<Seat> FIRST_CLASS_SEATS = new ArrayList<Seat>();
	
	private static ArrayList<Integer> VALID_SEAT_NUMS;
	
	private HashMap<Integer, ArrayList<Seat>> reservedSeats;
		
	/**
	 * Initializes a 2D array containing all of the seats on a plane as well as a HashMap for reserved seat
	 */
	public Airplane()
	{
		reservedSeats = new HashMap<Integer, ArrayList<Seat>>();
		
		layout = new Seat[NUM_ROWS][NUM_COLS];
		
		//First Class Section of Plane Initialization
		
		for (int r = 0; r < NUM_ROWS; r ++)
		{
			if (r != 2 && r != 7) //Makes sure no first class seats with J or C get created
			{
				for (int c = 0; c < 4; c ++)
				{
					layout[r][c] = new Seat(c + 1, SEAT_CHAR_FIRSTCLASS.charAt(r), FIRSTCLASS_PRICE);
				}
			}
		}
		
		for (int i = 3; i < 7; i ++) //Remove seats between and including G and D
		{
			layout[i][3] = null;
		}
		
		
		//Economy Plus Class Section of Plane Initialization:
		
		//LKJ Seats
		
		for (int r = 0; r < 3; r ++)
		{
			for (int c = 4; c < INDEX_PLANE + 1; c ++) 
			{
				layout[r][c] = new Seat(c + INDEX_PLANE, SEAT_CHARS.charAt(r), ECONOMYPLUS_PRICE); //Add 13 to match airplane floor plan
			}
		}
		
		//GFED Seats
		
		for (int r = 3; r < 7; r ++)
		{
			for (int c = 4; c < 12; c ++) 
			{
				layout[r][c] = new Seat(c + INDEX_PLANE, SEAT_CHARS.charAt(r), ECONOMYPLUS_PRICE); //Add 13 to match airplane floor plan
			}
		}
		
		//CBA Seats
		
		for (int r = 7; r < 10; r ++)
		{
			for (int c = 4; c < INDEX_PLANE + 1; c ++) 
			{
				layout[r][c] = new Seat(c + INDEX_PLANE, SEAT_CHARS.charAt(r), ECONOMYPLUS_PRICE); //Add 13 to match airplane floor plan
			}
		}
		
		//Back Columns Economy Plus Seats
		
		//Economy Plus Column 39, skip middle section
		
		for (int r = 0; r < 10; r ++)
		{
			int c = 25;
			layout[r][c] = new Seat(c + INDEX_PLANE + 1, SEAT_CHARS.charAt(r), ECONOMYPLUS_PRICE); //Add 14 to match airplane floor plan, lane 38 is skipped
			
			if (r == 2) { r += 4;}
		}
		
		//Economy Plus Column 40, only middle section
		
		for (int r = 3; r < 7; r ++)
		{
			int c = 26;
			layout[r][c] = new Seat(c + INDEX_PLANE + 1, SEAT_CHARS.charAt(r), ECONOMYPLUS_PRICE); //Add 14 to match airplane floor plan, lane 38 is skipped

		}
		
		
		//Economy seats initialization
		
		//Economy Class column 25 and 26 seats of airplane floor plan
		
		for (int r = 3; r < 7; r ++)
		{
			for (int c = 12; c < INDEX_PLANE + 1; c ++) 
			{
				layout[r][c] = new Seat(c + INDEX_PLANE, SEAT_CHARS.charAt(r), ECONOMY_PRICE); //Add 13 to match airplane floor plan
			}
		}
		
		//Economy Class column 27 to 37 of airplane floor plan
		
		for (int r = 0; r < 10; r ++)
		{
			for (int c = INDEX_PLANE + 1; c < 25; c ++) 
			{
				layout[r][c] = new Seat(c + INDEX_PLANE, SEAT_CHARS.charAt(r), ECONOMY_PRICE); //Add 13 to match airplane floor plan
			}
		}
		
		//Economy Class remove middle section from Column 37
		
		for (int r = 2; r < 8; r ++)
		{
			int c = 24;
			layout[r][c] = null; //Column 24 corresponds to column 37 on the airplane floor plan
		}
		
		//Economy Class Column 40, skip middle section
		
		for (int r = 0; r < 10; r ++)
		{
			int c = 26;
			layout[r][c] = new Seat(c + + INDEX_PLANE + 1, SEAT_CHARS.charAt(r), ECONOMY_PRICE); //Lane 38 is skipped
			if (r == 2) { r += 4;}
		}
		
		//Economy Class from 41 to 47
		
		for (int r = 0; r < 10; r ++)
		{
			for (int c = 27; c < 34; c ++)
			{
				layout[r][c] = new Seat(c + INDEX_PLANE + 1, SEAT_CHARS.charAt(r), ECONOMY_PRICE);
			}
		}
		
		//Economy Class from 48 to 51
		
		for (int r = 0; r < 10; r ++)
		{
			for (int c = 34; c < 38; c ++)
			{
				if (r != 2 && r != 7)
				{
					layout[r][c] = new Seat(c + INDEX_PLANE + 1, SEAT_CHARS.charAt(r), ECONOMY_PRICE);
	
				}				
			}
		}
		
		//Economy Class from 52 to 53
		
		for (int r = 3; r < 10; r ++)
		{
			for (int c = 38; c < 40; c ++)
			{
				if (c == 39 && r > 6)
				{
					break;
				}	
				if (r != 7)
				{
					layout[r][c] = new Seat(c + INDEX_PLANE + 1, SEAT_CHARS.charAt(r), ECONOMY_PRICE);
				}
			}
		}
		//2D Array fully initialized
		
		
		//Initializes static array lists containing different class seats (used by other methods)
		for (int c = 0; c < NUM_COLS; c ++)
		{
			for (int r = NUM_ROWS - 1; r >= 0; r --)
			{
				if (layout[r][c] != null)
				{
					if (layout[r][c].getPrice() == FIRSTCLASS_PRICE) 
					{
						FIRST_CLASS_SEATS.add(layout[r][c]);
					}
					else if (layout[r][c].getPrice() == ECONOMYPLUS_PRICE) 
					{
						ECON_PLUS_SEATS.add(layout[r][c]);
					}
					else 
					{
						ECON_CLASS_SEATS.add(layout[r][c]);
					}
				}
			}
		}
		
		Collections.sort(FIRST_CLASS_SEATS);
		Collections.sort(ECON_PLUS_SEATS);
		Collections.sort(ECON_CLASS_SEATS);
		
		initValidSeats(); //Helper method
	}
	
	/**
	 * Returns the 2D array containing all the seats
	 * @return all the seats
	 */
	public Seat[][] getAllSeats()
	{
		return layout;
	}
	
	/**
	 * Returns the HashMap containing all of the reserved seats
	 * @return the reserved seats
	 */
	public HashMap<Integer, ArrayList<Seat>> getAllReservedSeats()
	{
		return reservedSeats;
	}
	
	/**
	 * Returns all seats reserved under a specific user
	 * @param u - user that the reserved seats are being checked under
	 * @return seats that belong to this user
	 * precondition: u is a valid user
	 */
	public ArrayList<Seat> getReservedSeatsOfUser(User u)
	{
		if (reservedSeats.get(u.getID()) == null || reservedSeats.get(u.getID()).size() == 0)
		{
			return null;
		}	
		Collections.sort(reservedSeats.get(u.getID()));		
		return reservedSeats.get(u.getID());
	}
	
	/**
	 * Returns all seats reserved under a specific user based on their ID
	 * @param i - the ID of the user that the reserved seats are being checked under
	 * @return seats that belong to the user with this ID
	 * precondition: the ID is valid
	 */
	public ArrayList<Seat> getReservedSeatsOfUser(int i)
	{
		if (reservedSeats.get(i) == null || reservedSeats.get(i).size() == 0)
		{
			return null;
		}
		Collections.sort(reservedSeats.get(i));
		return reservedSeats.get(i);
	}
	
	/**
	 * Returns a seat based on the seat name in the form of a string (Eg. 17B)
	 * @param s - the name of the seat as a string
	 * @return the seat based on the name
	 * @throws NumberFormatException if the string is not in the correct format
	 */
	public Seat getSeat(String s)
	{
		int resNum = -1;
		char resCh = 'z';
		
		if (s.length() == 2)
		{
			try 
			{
				resNum = Integer.parseInt("" + s.charAt(0));
				resCh = s.charAt(1);
				return getSeat(resNum, resCh);
			}
			catch (NumberFormatException e)
			{
				System.out.println("Seat Number is Invalid.");
				System.out.println();
				return null;
			}
		}
		
		else if (s.length() == 3)
		{	
			try
			{
				resNum = Integer.parseInt(s.substring(0, 2));
				resCh = s.charAt(2);
				return getSeat(resNum, resCh);
			}	
			catch (NumberFormatException e)
			{
				System.out.println("Seat Number is Invalid.");
				System.out.println();
				return null;
			}
		}
		
		else
		{
			System.out.println("Seat Number is Invalid.");
			System.out.println();
			return null;
		}
	}
	
	/**
	 * Returns the seat based on the number and character associated with it
	 * @param num - the number of the seat 
	 * @param c - the character of the seat
	 * @return the seat with this name
	 * precondition: the number and character refer to a valid seat on the plane
	 */
	public Seat getSeat(int num, char c)
	{
		
		if (SEAT_CHARS.indexOf(c) == -1 || !VALID_SEAT_NUMS.contains(num))
		{
			return null;
		}
		
		if (num < 5) //Corresponds to the first class section
		{
			if (SEAT_CHAR_FIRSTCLASS.indexOf(c) == -1)
			{
				//System.out.println("Seat Number is not valid.");
				System.out.println();
				return null;
			}	
			return layout[SEAT_CHAR_FIRSTCLASS.indexOf(c)][num - 1];
		}
		
		else if (num < 38) //Corresponds to middle section of plane 17 to 37
		{
			return layout[SEAT_CHARS.indexOf(c)][num - INDEX_PLANE];
		}
		
		else //Seats 39 to 53
		{
			return layout[SEAT_CHARS.indexOf(c)][num - INDEX_PLANE - 1];
		}
	}
	
	/**
	 * Reserves a seat to the given user if it is not already reserved
	 * @param s - the seat to be reserved
	 * @param u - the user the seat will be reserved to
	 * precondition: the seat and user both exist and are valid
	 */
	public void reserveSeat(Seat s, User u)
	{
		if (s.getOwner() == null)
		{
			s.reserveSeat(u);
			
			if (reservedSeats.containsKey(u.getID()))
			{
				reservedSeats.get(u.getID()).add(s);
			}
			else
			{
				ArrayList<Seat> ls = new ArrayList<Seat>();
				ls.add(s);
				reservedSeats.put(u.getID(), ls);
			}
		}
		else
		{
			System.out.println("Seat is unavailable.");
		}
	}
	
	/**
	 * Sets the owner of this seat to null and removes from the ArrayList containing all reserved seats for this user
	 * @param s - the seat to be unreserved 
	 * precondition: the seat is valid
	 */
	public void cancelSeat(Seat s)
	{
		User oldOwner = s.getOwner();
		s.cancelSeat();
		getReservedSeatsOfUser(oldOwner).remove(s);
	}
	
	/**
	 * Prints all reserved seats and other total cost for the specified user
	 * @param u - the user which the reserved seats will be checked for
	 * precondition: the user is valid (this will be checked)
	 */
	public void viewReserve(User u)
	{
		System.out.println("Name: " + u.getName());
		
		ArrayList<Seat> userRes = getReservedSeatsOfUser(u);
		int sum = 0;
		
		if (userRes != null && userRes.size() != 0)
		{
			System.out.print("Seats: ");
			Seat firstSeat = userRes.get(0);
			System.out.print(firstSeat.toString() + " $" + firstSeat.getPrice());
			sum += firstSeat.getPrice();
			for (int i = 1; i < userRes.size(); i ++)
			{
				Seat currSeat = userRes.get(i);
				System.out.print(", " + currSeat.toString() + " $" + currSeat.getPrice());
				sum += currSeat.getPrice();
			}
			System.out.println();
			System.out.println("Total Balance Due: $" + sum);
			System.out.println();
		}
		
		else
		{
			System.out.println("No seats reserved.");
			System.out.println("No balance due.");
			System.out.println();
		}
		
	}
	
	/**
	 * Reads in reserved seats from the CL34 file and stores them them in the Plane 2D array as well as the HashMap with all reserved seats
	 * @param f - file that is going to be read to collect reserved seats
	 * @param p - Passengers object that contains all of the current registered users
	 * precondition: the file is in the correct format and the Passengers object is valid and contains the correct users
	 * @throws FileNotFoundException if f cannot be accessed or found
	 */
	public void reservedFromFile(File f, Passengers p)
	{
		try
		{
			Scanner in = new Scanner(f);
			HashMap<Integer, User> userList = p.getUsers();
			
			while (in.hasNextLine())
			{
				String[] s = in.nextLine().split(": ");
				int userID = Integer.parseInt(s[0]);
				
				String[] seats = s[1].split(", ");
				
				for (String a : seats)
				{
					User userRes = userList.get(userID);
					
					if (a.length() == 2) //One digit seat number with 1 letter
					{
						int num = Integer.parseInt("" + a.charAt(0));
						char ch = a.charAt(1);
						reserveSeat(getSeat(num, ch), userRes);
					}
					
					else //Assumes user inputs 2 digit seat number correctly
					{
						int num = Integer.parseInt(a.substring(0, 2));
						char ch = a.charAt(2);
						reserveSeat(getSeat(num, ch), userRes);
					}	
				}
			}
			
			in.close();
		}	
		
		catch (FileNotFoundException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Saves the reserved seats in the CL34 file in a specific format for it to be read the next time the program begins
	 * @throws IOException if for any reason the file cannot be saved
	 */
	public void saveCL34File()
	{
		try 
		{
			FileWriter CL34File = new FileWriter("CL34.txt");
			
			int stop = reservedSeats.size() - 1;
			int j = 0;
			
			for (Entry<Integer, ArrayList<Seat>> entry : reservedSeats.entrySet()) //Map.Entry was not working so I just put Entry
			{
			    int key = entry.getKey();
			    ArrayList<Seat> value = entry.getValue();
			    Collections.sort(value);
			    
			    if (value.size() == 1)
			    {
			    	if (j != stop)
			    	{
			    		CL34File.write(key + ": " + value.get(0).toString() + "\n");
			    	}
			    	else
			    	{
			    		CL34File.write(key + ": " + value.get(0).toString());
			    	}
			    	j += 1;
			    }
			    
			    else
			    {
			    	CL34File.write(key + ": " + value.get(0).toString());
			    	for (int i = 1; i < value.size(); i ++)
			    	{
			    		CL34File.write(", " + value.get(i).toString());
			    	}	
			    	if (j != stop)
			    	{
			    		CL34File.write("\n");
			    	}
			    	j += 1;
			    }  
			}
			
			CL34File.close();
			System.out.println("CL34 file successfully saved.");
		}
		
		catch (IOException e)
		{
			System.out.println("Error saving CL34 file.");
		}
	}
	
	/**
	 * Prints out Manifest List for current reserved seats and passengers in specific format
	 * precondition: users/owners of the reserved seats are valid
	 */
	public void printManifestList()
	{
		//Cycle through entire 2D array and only print if the seat is reserved, iterate through columns first then rows to get order
		
		ArrayList<Seat> firstClassRes = new ArrayList<Seat>();
		ArrayList<Seat> econPlusRes = new ArrayList<Seat>();
		ArrayList<Seat> econRes = new ArrayList<Seat>();
		
		
		//Can also use the 3 ArrayLists that are already sorted by class but that would require 3 loops but less if statements
		for (int c = 0; c < NUM_COLS; c ++)
		{
			for (int r = NUM_ROWS - 1; r >= 0; r --)
			{
				if (layout[r][c] != null && layout[r][c].getOwner() != null)
				{
					if (layout[r][c].getPrice() == FIRSTCLASS_PRICE) {firstClassRes.add(layout[r][c]);}
					else if (layout[r][c].getPrice() == ECONOMYPLUS_PRICE) {econPlusRes.add(layout[r][c]);}
					else {econRes.add(layout[r][c]);}
				}
			}
		}
		
		System.out.println("First Class: ");
		System.out.println();
		for (int f = 0; f < firstClassRes.size(); f ++)
		{
			Seat s = firstClassRes.get(f);
			System.out.println(s.toString() + ": " + s.getOwner().getName());
		}
		
		System.out.println();
		System.out.println("Economy Plus: ");
		System.out.println();
		
		for (int p = 0; p < econPlusRes.size(); p ++)
		{
			Seat s = econPlusRes.get(p);
			System.out.println(s.toString() + ": " + s.getOwner().getName());
		}
		
		System.out.println();
		System.out.println("Economy Class: ");
		System.out.println();
		
		for (int e = 0; e < econRes.size(); e ++)
		{
			Seat s = econRes.get(e);
			System.out.println(s.toString() + ": " + s.getOwner().getName());
		}
	}
	
	/**
	 * Prints out all available seats in a specific format
	 * precondition: seat owners/users are correctly managed
	 */
	public void getSeatAvail() 
	{
		ArrayList<Seat> firstClassAva = new ArrayList<Seat>();
		ArrayList<Seat> econPlusAva = new ArrayList<Seat>();
		ArrayList<Seat> econAva = new ArrayList<Seat>();
		
		for (Seat f : FIRST_CLASS_SEATS)
		{
			if (f.getOwner() == null)
			{
				firstClassAva.add(f);
			}
		}
		
		for (Seat p : ECON_PLUS_SEATS)
		{
			if (p.getOwner() == null)
			{
				econPlusAva.add(p);
			}
		}
		
		for (Seat e : ECON_CLASS_SEATS)
		{
			if (e.getOwner() == null)
			{
				econAva.add(e);
			}
		}
		
		Collections.sort(firstClassAva);
		Collections.sort(econPlusAva);
		Collections.sort(econAva);
		
		
		System.out.println("First Class (price: $1000/seat)");
		System.out.println();
		
		System.out.print(firstClassAva.get(0).getSeatNumber() + ": ");
		
		for (int i = 0; i < firstClassAva.size() - 1; i ++)
		{
			
			if (firstClassAva.get(i).getSeatNumber() == firstClassAva.get(i + 1).getSeatNumber())
			{
				System.out.print(firstClassAva.get(i).getSeatLetter() + ", ");
			}
			
			else
			{
				System.out.println(firstClassAva.get(i).getSeatLetter());
				System.out.print(firstClassAva.get(i + 1).getSeatNumber() + ": ");
			}
		}
	
		System.out.println(firstClassAva.get(firstClassAva.size() - 1).getSeatLetter());
		System.out.println();
		
		System.out.println("Economy Plus (price: $500/seat)");
		System.out.println();
		
		System.out.print(econPlusAva.get(0).getSeatNumber() + ": ");
		
		for (int j = 0; j < econPlusAva.size() - 1; j ++)
		{
			
			if (econPlusAva.get(j).getSeatNumber() == econPlusAva.get(j + 1).getSeatNumber())
			{
				System.out.print(econPlusAva.get(j).getSeatLetter() + ", ");
			}
			
			else
			{
				System.out.println(econPlusAva.get(j).getSeatLetter());
				System.out.print(econPlusAva.get(j + 1).getSeatNumber() + ": ");
			}
		}
		
		System.out.println(econPlusAva.get(econPlusAva.size() - 1).getSeatLetter());
		System.out.println();
		
		System.out.println("Economy (price: $250/seat)");
		System.out.println();
		
		System.out.print(econAva.get(0).getSeatNumber() + ": ");
		
		for (int k = 0; k < econAva.size() - 1; k ++)
		{
			
			if (econAva.get(k).getSeatNumber() == econAva.get(k + 1).getSeatNumber())
			{
				System.out.print(econAva.get(k).getSeatLetter() + ", ");
			}
			
			else
			{
				System.out.println(econAva.get(k).getSeatLetter());
				System.out.print(econAva.get(k + 1).getSeatNumber() + ": ");
			}
		}
		
		System.out.println(econAva.get(econAva.size() - 1).getSeatLetter());
		System.out.println();
		
		//Method completed
		System.out.println();

	}
	
	
	/**
	 * Helper method for the constructor to initialize all valid seat numbers in order to keep track of valid seats
	 */
	private void initValidSeats()
	{
		
		VALID_SEAT_NUMS = new ArrayList<Integer>();
		
		for (Seat a : layout[0])
		{
			if (a != null)
			{
				VALID_SEAT_NUMS.add(a.getSeatNumber());
			}
		}
		
		VALID_SEAT_NUMS.add(52); //Have to add manually
		VALID_SEAT_NUMS.add(53);
	}
	
	/**
	 * Prints out a display of the 2D array that represents the airplane layout
	 */
	public void printPlane() //This is not a needed method but helps for visualization
	{
		for (Seat[] a : getAllSeats())
		{
			for (Seat b : a)
			{
				if (b != null)
				{
					System.out.print(b.toString() + " ");
				}
				else 
				{
					System.out.print("x  ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}
}
