import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Assignment 2 Solution for Passengers class
 * @author Henry Wahhab
 *@version 1.0 3/4/2023
 */


/**
 * Passengers holds all information about users (regardless if they have reserved seats) and admins as well as manages adding users and saving them to the Users file
 */


public class Passengers {

	public static ArrayList<Integer> ADMIN_LIST = new ArrayList<Integer>();
	
	private HashMap<Integer, User> publicUsers;
	
	/**
	 * Creates a HashMap with the ID as the key and User as the value to hold all users (also initializes default administrator ID's)
	 * @param pU - the HashMap that will be used to hold users
	 * precondition: the HashMap used as the parameter is valid
	 */
	public Passengers(HashMap<Integer, User> pU)
	{
		publicUsers = pU;
		defaultEmployees(); //Adds 8 accepted employee/admin ID's to the list
	}
	
	/**
	 * Constructor that takes in the User file and adds all users to the HashMap (also initializes default administrator ID's)
	 * @param f - file containing user information in a specified format
	 * @throws FileNotFoundException if the file cannot be read for any reason
	 */
	public Passengers(File f)
	{
		publicUsers = new HashMap<Integer, User>();
		
		try
		{
			Scanner in = new Scanner(f);
			
			while (in.hasNextLine())
			{
				String[] s = in.nextLine().split(", ");
				User u = new User(s[2], Integer.parseInt(s[0]), s[1]);
				addUser(u);
			}
			
			in.close();
		}		
		catch (FileNotFoundException e)
		{
			System.out.println(e.getMessage());
		}
		
		defaultEmployees();
	}
	
	/**
	 * Returns the HashMap of all users
	 * @return a HashMap
	 */
	public HashMap<Integer, User> getUsers()
	{
		return publicUsers;
	}
	
	/**
	 * Adds user into the HashMap 
	 * @param p - the user to be added
	 * precondition: the user has correct information and is not null
	 */
	public void addUser(User p)
	{
		publicUsers.put(p.getID(), p);
	}
	
	/**
	 * Sets acceptable Admin ID's to the ArrayList specified
	 * @param ls - the ArrayList to contain all acceptable Admin ID's
	 * precondition: the parameter is a non-empty ArrayList
	 */
	public void setAdmins(ArrayList<Integer> ls)
	{
		ADMIN_LIST = ls;
	}
	
	/**
	 * Saves the Users file and writes to it in a specified format so it can be used the next time the program runs
	 * @throws IOException if there is any error in writing/saving the file
	 */
	public void saveUsersFile()
	{
		try 
		{
			FileWriter usersFile = new FileWriter("Users.txt");
			
			int stop = getUsers().size() - 1;
			int i = 0;
			User lastUser = null;
			
			for (User u : getUsers().values())
			{
				if (i == stop) 
				{
					lastUser = u;
					break;
				}
				i += 1;
				usersFile.write(u.getID() + ", " + u.getPassword() + ", " + u.getName() + "\n");
			}
			
			
			if (lastUser != null)
			{
				usersFile.write(lastUser.getID() + ", " + lastUser.getPassword() + ", " + lastUser.getName());
			}
			
			usersFile.close();
			System.out.println("Users file successfully saved.");
		}
		
		catch (IOException e)
		{
			System.out.println("Error saving Users file.");
		}
	}
	
	/**
	 * Helper method to create random employee ID's (put into an ArrayList) for the Administration Menu
	 */
	private void defaultEmployees()
	{
		ADMIN_LIST.add(57598);
		ADMIN_LIST.add(45483);
		ADMIN_LIST.add(32587);
		ADMIN_LIST.add(28795);
		ADMIN_LIST.add(52655);
		ADMIN_LIST.add(11138);
		ADMIN_LIST.add(33300);
		ADMIN_LIST.add(84205);
	}
	
}


