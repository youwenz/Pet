package System;
import java.util.*;

public class PetOutletSystem {
	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        ArrayList<Admin> admins = new ArrayList<>();
        admins.addAll(Admin.loadAdminsFromFile("adminData.txt"));
        
        ArrayList<Client> clients = new ArrayList<>();
        clients.addAll(Client.loadClientsFromFile("clientData.txt"));
        
        ArrayList<Product> products = new ArrayList<>();
        products.addAll(Product.loadProductsFromFile("productData.txt"));

        // Create a billing statement
        ArrayList<BillingStatement> billingStatements = new ArrayList<>();
        
        
        boolean running = true;
        while (running) {
            System.out.println("Welcome to the Point of Sale Application");
            System.out.println("1. Admin Login");
            System.out.println("2. Client Login");
            System.out.println("3. New user register");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter admin username: ");
                    String adminUsername = scanner.nextLine();
                    System.out.print("Enter admin password: ");
                    String adminPassword = scanner.nextLine();
                    try{
                    	Admin admin = User.login(admins, adminUsername, adminPassword);
                    	System.out.println("Admin logged in successfully.");
                        while (admin.adminMenu(products, billingStatements )) {       
                        }
                    }catch (LoginException e) {
                    	System.out.println(e.getMessage());
                    }
                    break;
                    
                case 2:
                    System.out.print("Enter client username: ");
                    String clientUsername = scanner.nextLine();
                    System.out.print("Enter client password: ");
                    String clientPassword = scanner.nextLine();
                    try {
                    	Client client = User.login(clients, clientUsername, clientPassword);
                    	System.out.println("Client logged in successfully.");
                        while (client.clientMenu(products, billingStatements)) { 
                        }
                    } catch(LoginException e){
                    	System.out.println(e.getMessage());
                    }    
                    break;
                    
                case 3:
                	User.createUser(admins, clients);
                	break;
                	
                case 4:
                    running = false;
                    System.out.println("Exiting Pets outlet system. Goodbye!");
                    Admin.writeAdminsToFile(admins, "adminData.txt");
                    Client.writeClientsToFile(clients, "clientData.txt");
                    Product.writeProductsToFile(products, "productData.txt");
                    break;
                    
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }

        scanner.close();
    }

	
	
	
}
