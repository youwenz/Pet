package System;

import java.io.*;
import java.util.*;

class Admin extends User {
	private Department department;
	
    public Admin(String username, String password, String contactNum, Department department) {
        super(username, password, contactNum);
        this.department = department;
    }
    
    public Department getDepartment() {
    	return department;
    }
    
    public Product createItem() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("Enter product ID: ");
                String newProductId = scanner.nextLine();
                if (newProductId.isEmpty()) {
                    throw new IllegalArgumentException("Product ID cannot be empty. Please enter a valid ID.");
                }

                System.out.print("Enter product name: ");
                String name = scanner.nextLine();
                if (name.isEmpty()) {
                    throw new IllegalArgumentException("Product name cannot be empty. Please enter a valid name.");
                }

                System.out.print("Enter product description: ");
                String description = scanner.nextLine();
                if (description.isEmpty()) {
                    throw new IllegalArgumentException("Product description cannot be empty. Please enter a valid description.");
                }

                System.out.print("Enter product price: ");
                String priceInput = scanner.nextLine();
                if (priceInput.isEmpty()) {
                    throw new IllegalArgumentException("Product price cannot be empty. Please enter a valid price.");
                }
                double price = Double.parseDouble(priceInput);
                if (price <= 0) {
                    throw new IllegalArgumentException("Invalid product price. Please enter a positive number.");
                }

                System.out.print("Enter product quantity: ");
                String quantityInput = scanner.nextLine();
                if (quantityInput.isEmpty()) {
                    throw new IllegalArgumentException("Product quantity cannot be empty. Please enter a valid quantity.");
                }
                int quantity = Integer.parseInt(quantityInput);
                if (quantity <= 0) {
                    throw new IllegalArgumentException("Invalid product quantity. Please enter a positive integer.");
                }

                Product newProduct = new Product(newProductId, name, description, price, quantity);
                System.out.println("New product created successfully.");
                return newProduct;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input format. Please enter a valid number.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    public static ArrayList<Admin> loadAdminsFromFile(String filename) {
        ArrayList<Admin> admins = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userInfo = line.split(",");
                String userId = userInfo[0];
                String username = userInfo[1];
                String password = userInfo[2];
                String contactNum = userInfo[3];
                String depart = userInfo[4];
                Department department = Department.valueOf(depart);
                Admin admin = new Admin(username, password, contactNum, department);
                admins.add(admin);
            }
        } catch (IOException e) {
            System.err.println("Error loading users from file: " + e.getMessage());
        }
        return admins;
    }
    
    public static void writeAdminsToFile(ArrayList<Admin> admins, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (Admin admin : admins) {
                String adminInfo = admin.getUserId() + "," + admin.getUsername() + "," + admin.getPassword() + "," + admin.getContactNum()
                + "," + admin.getDepartment().toString();
                writer.write(adminInfo + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing admins' information to file: " + e.getMessage());
        }
    }
    
    public boolean adminMenu(ArrayList<Product> products, ArrayList<BillingStatement> billingStatements) {
		boolean adminMenuRunning = true;
		Scanner scanner = new Scanner(System.in);
		System.out.println("Admin Menu");
        System.out.println("1. Generate Inventory Report");
        System.out.println("2. Generate Sales Report");
        System.out.println("3. Create New Product");
        System.out.println("4. View Billing Statements");
        System.out.println("5. Update orders");
        System.out.println("6. Logout");
        System.out.print("Enter your choice: ");
        int adminChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        switch (adminChoice) {
            case 1:
            	InventoryReport inventoryReport = new InventoryReport(products, new Date());
            	inventoryReport.generateReport();
                break;
                
            case 2:
            	generateSalesReport(billingStatements);
            	break;
                
            case 3:
                Product newProduct = createItem();
                products.add(newProduct);
                break;
                
            case 4:
            	viewBillingStatement(billingStatements);
                break;
                
            case 5:
            	updateOrders(billingStatements);
            	break;
            	
            case 6:
                logout();
                adminMenuRunning = false;
                break;
            default:
                System.out.println("Invalid choice. Please enter a valid option.");
        }  
       return adminMenuRunning;
	}
    
    public void generateSalesReport(ArrayList<BillingStatement> billingStatements) {
    	List<Order> orders = new ArrayList<>();
    	if(!billingStatements.isEmpty()) {
    		for(BillingStatement bs : billingStatements) {
            	orders = bs.getPaidOrders();
    		}
    		SalesReport salesReport = new SalesReport(orders, new Date());
    		salesReport.generateReport();
    	}else {
    		System.out.println("No sales record found.");
    		System.out.println("Press enter to continue");
    		Scanner scanner = new Scanner(System.in);
            scanner.nextLine(); 
        }
    }
    
    public void viewBillingStatement(ArrayList<BillingStatement> billingStatements) {
    	if(!billingStatements.isEmpty()) {
    		for(BillingStatement bs : billingStatements) {
            	bs.viewBillingStatement();
    		}
    	}else {
    		System.out.println("No billing statements found.");
    		System.out.println("Press enter to continue");
    		Scanner scanner = new Scanner(System.in);
            scanner.nextLine(); 
        }
    }
    
    public void updateOrders(ArrayList<BillingStatement> billingStatements) {
    	if(!billingStatements.isEmpty()) {
    		for(BillingStatement bs : billingStatements) {
            	bs.viewOrder();
    		}
    	}else {
    		System.out.println("No orders found.");
    		System.out.println("Press enter to continue");
    		Scanner scanner = new Scanner(System.in);
            scanner.nextLine(); 
        }
    }
    
}
