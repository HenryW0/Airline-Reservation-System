
/**
 * Assignment 2 solution for User class
 * @author Henry Wahhab
 * @version 1.0 3/4/2023
 */


/**
 * A User has a name, a unique ID, and password associated with their account, all can be accessed and changed if needed
 */

public class User {

	private String name;
	private int ID;
	private String password;
	
	/**
	 * Constructs a user with a name, ID, and password
	 * @param n - the string that will be set to the name of the user
	 * @param id - the integer that will be set to the ID of the user
	 * @param pass - the string that will be set to the 
	 * precondition: all parameters are valid and the id is unique and 5 digits 
	 * Note: program should still work if it is not 5 digits though
	 */
	public User(String n, int id, String pass)
	{
		name = n;
		ID = id;
		password = pass;
	}
	
	/**
	 * Sets the name of the user
	 * @param n - the string that the name of the user will be set to
	 * precondition: name is a valid (non-empty) string
	 */
	public void setName(String n)
	{
		name = n;
	}
	
	/**
	 * Sets the ID of the user
	 * @param id - the unique integer that the ID will be set to
	 * precondition: id is a unique, 5 digit integer 
	 */
	public void setID(int id)
	{
		ID = id;
	}
	
	/**
	 * Sets the password of the user
	 * @param pass - the string that will be set as the password
	 * precondition: pass is a valid (non-empty) string
	 */
	public void setPassword(String pass)
	{
		password = pass;
	}
	
	/**
	 * Returns the name of the user
	 * @return the name of user
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Returns the ID of the user
	 * @return the ID of user
	 */
	public int getID()
	{
		return ID;
	}
	
	/**
	 * Returns the password of the user
	 * @return the password of User
	 */
	public String getPassword()
	{
		return password;
	}	
	
	/**
	 * Returns a string containing user information
	 * @return user information 
	 */
	public String toString()
	{
		return ID + ", " + password + ", " + name;
	}
	
}
