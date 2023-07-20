
/**
 * Assignment 2 solution for Seat class
 * @author Henry Wahhab
 * @version 1.0 3/4/2023
 */

/**
 * A Seat has a number, character, price, and user associated with it, seat information can be retrieved and seats can be reserved/cancelled 
 */

public class Seat implements Comparable<Seat>{

	private int num;
	private char c;
	private User u;
	private int price; //Seat class: First Class, EconomyPlus, Economy 
	
	/**
	 * Constructs a seat based on the parameters given
	 * @param n - the number associated with the seat
	 * @param ch - the character associated with the seat
	 * @param cost - the cost of the seat based on the class of the seat
	 * precondition: all parameters are valid (correct seat number, character, and one of the 3 prices)
	 */
	public Seat(int n, char ch, int cost)
	{
		num = n;
		c = ch;
		price = cost;
		u = null;
	}
	
	/**
	 * Returns the corresponding seat number
	 * @return the seat number
	 */
	public int getSeatNumber()
	{
		return num;
	}
	
	/**
	 * Returns the seat letter/character
	 * @return the seat character
	 */
	public char getSeatLetter()
	{
		return c;
	}
	
	/**
	 * Reserves the seat by setting the owner of the seat to a user
	 * @param user - the user that is reserving the seat
	 */
	public void reserveSeat(User user)
	{
		u = user;
	}
	
	/**
	 * Sets the user of the seat to be null (no owner)
	 */
	public void cancelSeat()
	{
		u = null;
	}
	
	/**
	 * Returns the user that the seat is reserved under
	 * @return the user of the seat
	 */
	public User getOwner()
	{
		return u;
	}
	
	/**
	 * Returns a string of the seat in the form of the number and the letter
	 * @return the seat name
	 */
	public String toString() 
	{
		return "" + num + c;
	}
	
	/**
	 * Returns the price that the seat costs, associated with the class of the seat
	 * @return the price of the seat
	 */
	public int getPrice()
	{
		return price;
	}
	
	/**
	 * Returns a string naming the class of the seat based on the price
	 * @return the name of the seat class
	 */
	public String getSeatClass()
	{
		if (price == 250)
		{
			return " Economy Class ";
		}
		
		else if (price == 500)
		{
			return " Economy Plus Class ";
		}
		
		else 
		{
			return " First Class ";
		}
	}
	
	/**
	 * Compares the seat numbers and returns a negative integer if this seat comes before the other seat, negative otherwise (checks number first, then letter)
	 * @param other - the other seat that is being compared to
	 * precondition: the other parameter seat is a valid seat on the plane
	 */
	public int compareTo(Seat other)
	{
		if (this.getSeatNumber() == other.getSeatNumber())
		{
			return this.getSeatLetter() - other.getSeatLetter();
		}
		
		return this.getSeatNumber() - other.getSeatNumber();
	}
	
}
