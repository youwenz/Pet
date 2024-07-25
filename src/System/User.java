package System;

import java.util.*;
import java.io.*;

public class User {
	private String username;
    private String password;
    private String contactNum;
    private String userID;
    private boolean loggedIn;

    public User(String username, String password, String contactNum) {
        this.username = username;
        this.password = password;
        this.loggedIn = false;
        this.contactNum = contactNum;
        this.userID = generateUserID();
    }
    
    public String generateUserID() {
    	String userId="User";
    	for(int i=0; i<5; i++) {
    		Random random = new Random();
        	int digit = random.nextInt(0,9);
        	userId += digit;
    	}
    	return userId;
    }
    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getContactNum() {
        return contactNum;
    }

    public String getUserId() {
        return userID;
    }

    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }
    
    public boolean isLoggedIn() {
    	return loggedIn;
    }
    
    
    public static <T extends User> T login(ArrayList<T> users, String username, String password) throws LoginException {
        for (T user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                user.login();
                return user;
            }
        }
        throw new LoginException("Invalid username or password");
    }
    
    public void login() {
        loggedIn = true;
        System.out.println("Logged in as: " + username);
    }
    // Method to log out
    public void logout() {
        loggedIn = false;
        System.out.println("Logged out successfully.");
    }
    public static void createUser(ArrayList<Admin> admins, ArrayList<Client> clients) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter username: ");
    	String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter contact number: ");
        String contactNum = scanner.nextLine();
        
    	System.out.println("Sign up for admin or client? Enter A/C");
    	char option = scanner.nextLine().charAt(0);
    	boolean createSuccess = false;
    	while(!createSuccess) {
    		if (option == 'A' || option == 'a') {
        		
        		for (Department department : Department.values()) {
                    System.out.println(department.ordinal() + 1 + ". " + department);
                }
                System.out.print("Enter the number corresponding to your department choice: ");
                int ch = scanner.nextInt();
                scanner.nextLine(); 
                while (ch < 1 && ch > Department.values().length) {
                	System.out.println("Invalid choice. Please enter a number within the range:");
                	ch = scanner.nextInt();
                    scanner.nextLine(); 
                }
                Department department = Department.values()[ch-1];
                Admin newUser = new Admin(username, password, contactNum, department);
        		admins.add(newUser);
                System.out.println("Admin account created successfully.");
                createSuccess=true;
        	}
        	else if(option == 'C' || option =='c') {
        		System.out.println("Enter address:");
        		String address = scanner.nextLine();
        		Client newUser = new Client(username, password, contactNum, address);
        		clients.add(newUser);
        		System.out.println("Client account created successfully.");
        		createSuccess=true;
        	}
        	else {
        		System.out.println("Wrong input. Please enter A/C only");
        	}
    	}
    	System.out.println("Account created successfully.");
    	System.out.println("Press enter to continue");
        scanner.nextLine(); // Consume newline
	}
    

}
