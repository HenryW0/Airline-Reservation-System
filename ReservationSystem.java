import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Assignment 2 Solution for ReservationSystem class
 * @author Henry Wahhab
 *@version 1.0 3/4/2023
 */

/**
 * ReservationSystem takes user inputs from the console as well as command line arguments to read/create files and allows users to sign in to reserve, cancel, and view 
 * seat reservations and lets administrators save passenger and reservation data
 */

public class ReservationSystem {
	
	public static void main(String[] args)
	{	
		
		//Initialization
		Airplane planeArray = new Airplane();
		Passengers passList = null;
		
		File CL34;
		File Users;
		
		//Reading from files
		try
		{			
			CL34 = new File(args[0] + ".txt");
			Users = new File(args[1] + ".txt");
			
			if (!CL34.exists() && !Users.exists())
			{
				System.out.println("CL34 and Users are now created.");
				System.out.println();

				CL34.createNewFile();
				Users.createNewFile();	
				passList = new Passengers(new HashMap<Integer, User>());
			}
				
			else if (!CL34.exists() && Users.exists())
			{
				System.out.println("CL34 File is now created.");
				System.out.println("Existing Users file is loaded.");
				System.out.println();

				passList = new Passengers(Users);
				CL34.createNewFile();
			}
						
			else 
			{	
				System.out.println("Existing Reservations and Users are loaded.");
				passList = new Passengers(Users);
				planeArray.reservedFromFile(CL34, passList);
				System.out.println();
			}	
		}
		
		catch (IOException e)
		{
			System.out.println("File Error " + e.getMessage());
		}
	
		Scanner in = new Scanner(System.in);
		boolean mainLoop = true;
		
		while (mainLoop)
		{
			System.out.println("[P]ublic User or [A]dministrator ?");
			System.out.println();
			String userInput = in.next();
			System.out.println();

			
			if (userInput.equals("P"))
			{
				System.out.println("[F]irst time user or [E]xisting user ?");
				System.out.println();

				userInput = in.next();
				System.out.println();
				
				if (userInput.equals("F"))
				{
					System.out.println("Registration process: ");
					System.out.println("Please enter your first and last name: ");
					System.out.println();

					in.nextLine();
					String newName = in.nextLine();
					System.out.println();

					System.out.println("Please your unique 5-digit user ID: ");
					System.out.println();

					int newUserID = Integer.parseInt(in.next()); //Should check to make sure this ID is not already taken but directions say assume correct
					System.out.println();

					System.out.println("Please create a password: ");
					System.out.println();
					
					String newPass = in.next();
					System.out.println();
					
					User register = new User(newName, newUserID, newPass);
					System.out.println(register.getName());
				
					passList.addUser(register);
					
					System.out.println("You are now registered!");
					System.out.println();
				}
				
				else if (userInput.equals("E"))
				{
					boolean invalid = true;
					while (invalid)
					{
						System.out.println("Please sign in to access your account: ");
						System.out.println("Enter your unique 5-digit user ID: ");
						System.out.println();
						
						int exUserID = 0;
						try
						{
							exUserID = Integer.parseInt(in.next());
							System.out.println();
						}
						
						catch (Exception e)
						{
							System.out.println();
							System.out.println("Please enter a number for your ID.");
							System.out.println();
							continue;
						}
						
						System.out.println();
						
						System.out.println("Enter your password: ");
						System.out.println();

						String exPass = in.next();
						System.out.println();

						
						if (!passList.getUsers().containsKey(exUserID)) //Checks if user ID is contained in the passenger list
	                    {
	                        System.out.println("Invalid User ID, please try again.");
	                        System.out.println();
	                    }
	                    
						else if (!passList.getUsers().get(exUserID).getPassword().equals(exPass)) //Checks if password is correct
	                    {
	                        System.out.println("Invalid Password, please try again.");
	                        System.out.println();
	                    }
						
	                    else
	                    {
	                    	invalid = false;
	                    	
	                    	User currUser = passList.getUsers().get(exUserID);
	                    	
	                    	//Existing User Main Menu Options:
	                    	System.out.println("Welcome " + currUser.getName() + " how may I assist you ?");
	                        System.out.println();

	                    	
	                        boolean notDone = true;
	                    	while (notDone)
	                    	{
		                        System.out.println("Check [A]vailability, Make [R]eservation, [C]ancel Reservation, [V]iew Reservations, [D]one");
	                    		System.out.println();
	                    		
	                    		userInput = in.next();
	                    		System.out.println();
	                    		
	                    		if (userInput.equals("A"))
	                    		{
	                    			System.out.println("Seat Availability: ");
	                    			
	                    			planeArray.getSeatAvail();
	                    		}
	                    		
	                    		else if (userInput.equals("R"))
	                    		{
	                    			System.out.println("To reserve a seat, enter a Seat Number: ");
	                    			System.out.println();
	                    			
	                    			boolean notDoneR = true;
	                    			while (notDoneR)
	                    			{
	                    				userInput = in.next();
		                    			System.out.println();
		                    			
		                    			Seat addRes = planeArray.getSeat(userInput);
		                    			
		                    			if (addRes != null)
		                    			{
		                    				if (addRes.getOwner() == null)
		                    				{
		                    			
		                    					System.out.println("Seat Information: " + addRes.toString() + addRes.getSeatClass() + "$" + addRes.getPrice());
		                    					System.out.println("Please confirm you would like to reserve seat (Y/N): ");
		                    					System.out.println();
		                    					
		                    					userInput = in.next();
		                    					System.out.println();
		                    					
		                    					if (userInput.equals("Y"))
		                    					{
		                    						planeArray.reserveSeat(addRes, currUser);
		                    						System.out.println(addRes  + " has been reserved.");
		                    						System.out.println();
		                    					}
		                    					
		                    					else
		                    					{
		                    						System.out.println("Seat has not been reserved.");
			                    					System.out.println();

		                    					}
		                    					
		                    					System.out.println("Would you like to make another reservation (Y/N) ?");
		                    					System.out.println();

	                    						userInput = in.next();
		                    					System.out.println();
	                    						
	                    						if (userInput.equals("Y"))
	                    						{
	                    							System.out.println("To reserve a seat, enter a Seat Number: ");
			    	                    			System.out.println();
	                    						}	
	                    						else
	                    						{
	                    							notDoneR = false;
		                    						break;
	                    						}
			                    			}
		                    				
		                    				else
		                    				{
		                    					System.out.println("Seat is unavailable, please try again.");
		                    					System.out.println();
		                    					
		                    					System.out.println("To reserve a seat, enter a Seat Number: ");
		    	                    			System.out.println();
		                    				}
		                    			}
		                    			
		                    			else
		                    			{
		                    				System.out.println("Seat does not exist, please try again.");
		                    				System.out.println("To reserve a seat, enter a Seat Number: ");
			                    			System.out.println();
		                    			}
	                    			}
	                    		}
	                    		
	                    		else if (userInput.equals("C"))
	                    		{
	                    			boolean notDoneC = true;
	                    			Seat delSeat = null;
	                    			ArrayList<Seat> currUserRes = null;
	                    			
	                    			if (planeArray.getReservedSeatsOfUser(currUser) == null)
	                    			{
	                    				System.out.println("No reserved seats currently.");
	                    				System.out.println();
	                    				notDoneC = false;
	                    			}
	                    			
	                    			else
	                    			{
	                    				currUserRes = planeArray.getReservedSeatsOfUser(exUserID);
		                    			
		                    			System.out.println("Reserved Seats under " + currUser.getName() + ": " + currUserRes);
		                    			System.out.println();
		                    			
		                    			System.out.println("Enter the Seat Number that you would like to cancel: ");
		                    			System.out.println();

		                    			userInput = in.next();
		                    			System.out.println();
		                    			
		                    			delSeat = planeArray.getSeat(userInput);
	                    			}
	                    			
	                    			while (notDoneC)
	                    			
	                    			if (!currUserRes.contains(delSeat))
	                    			{
	                    				
	                    				if (delSeat != null) //For not valid seat numbers
	                    				{
	                    					System.out.println("Not a listed seat, please try again.");
	                    				}
	                    				
	                    				else //For seat numbers that are not listed, error message handled in method
	                    				{
	                    					System.out.println("Please try again.");
	                    				}
	                    				
	                    				System.out.println("Enter the Seat Number that you would like to cancel: ");
		                    			System.out.println();
		                    			
		                    			System.out.println("Reserved Seats under " + currUser.getName() + ": " + currUserRes);
		                    			System.out.println();
		                    			
		                    			userInput = in.next();
		                    			System.out.println();
		                    			delSeat = planeArray.getSeat(userInput);
	                    			}
	                    			
	                    			else
	                    			{
	                    				planeArray.cancelSeat(delSeat);
	                    				System.out.println(delSeat.toString() + " has been cancelled.");
		                    			System.out.println();
		                    			notDoneC = false;
		                    			break;
	                    			}
	                    		}
	                    		
	                    		else if (userInput.equals("V"))
	                    		{
	                    			planeArray.viewReserve(currUser);
	                    		}
	                    		
	                    		else if (userInput.equals("D"))
	                    		{
	                    			System.out.println("Logging out...");
	                    			System.out.println();
	                    			notDone = false;
	                    			break;
	                    		}
	                    		
	                    		else
	                    		{
	                    			System.out.println("Not a valid option.");
	                    			System.out.println();
	                    		}
	                    	}
	                    }
					}
				}
				
				else
				{
					System.out.println("Not a valid user type, returning to initial menu.");
        			System.out.println();
				}
			}
			
			else if (userInput.equals("A"))
			{	
				
				boolean notDoneA = true;	
				while (notDoneA)
				{
					System.out.println("Please Enter Administrator/Employee ID: ");
					System.out.println();
					
					
					int adminIn = 0;
					try
					{
						adminIn = Integer.parseInt(in.next());
						System.out.println();
					}
					
					catch (Exception e)
					{
						System.out.println();
						System.out.println("Please enter a number for your ID.");
						System.out.println();
						continue;
					}
					
					System.out.println();
					String adminInput;
					
					if (Passengers.ADMIN_LIST.contains(adminIn))
					{
						notDoneA = false;
						System.out.println("Admin Login Successful.");
						
						boolean notDoneAA = true;
						while (notDoneAA)
						{
							System.out.println("Show [M]anifest list,  E[X]it");
							System.out.println();
							
							adminInput = in.next();
							System.out.println();
							
							if (adminInput.equals("M"))
							{
								planeArray.printManifestList();
								System.out.println();
							}
							
							else if (adminInput.equals("X"))
							{
								//Save information to files
								passList.saveUsersFile();
								planeArray.saveCL34File();
								notDoneAA = false;
								mainLoop = false;
								break;
							}
							
							else
							{
								System.out.println("Not a valid input, please try again.");
							}
						}		
					}
					
					else
					{
						System.out.println("Not a valid Admin/Employee ID, please try again.");
						System.out.println();
					}
				}	
			}
			
			else
			{
				System.out.println("Not a valid option, please try again.");
				System.out.println();
			}		
		}
		in.close();
	}
}
