package System;

import java.util.*;
import java.io.*;


class Client extends User {
	String address;
    public Client(String username, String password, String contactNum, String address) {
        super(username, password, contactNum);
        this.address = address;
    }
    
    public String getAddress() {
    	return address;
    }
    public static ArrayList<Client> loadClientsFromFile(String filename) {
        ArrayList<Client> clients = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userInfo = line.split(",");
                String userID = userInfo[0];
                String username = userInfo[1];
                String password = userInfo[2];
                String contactNum = userInfo[3];
                line = reader.readLine();
                String address = line;
                Client client = new Client(username, password, contactNum, address);
                clients.add(client);
            }
        } catch (IOException e) {
            System.err.println("Error loading clients from file: " + e.getMessage());
        }
        return clients;
    }
    
    public static void writeClientsToFile(ArrayList<Client> clients, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (Client client : clients) {
                String clientInfo = client.getUserId() + "," + client.getUsername() + "," + client.getPassword() + "," + client.getContactNum();
                writer.write(clientInfo + "\n");
                writer.write(client.getAddress()+"\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing admins' information to file: " + e.getMessage());
        }
    }
    
    public boolean clientMenu(ArrayList<Product> products, ArrayList<BillingStatement> billingStatements) {
		Scanner scanner = new Scanner(System.in);
		boolean clientMenuRunning =true;
		System.out.println("Client Menu");
        System.out.println("1. Place Order");
        System.out.println("2. Pay for Orders");
        System.out.println("3. View order status");
        System.out.println("4. Logout");
        System.out.print("Enter your choice: ");
        int clientChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (clientChoice) {
            case 1:
                placeOrder(products, billingStatements);
                break;
            case 2:
            	makePayment(billingStatements);
                break;
            case 3:
            	viewOrderStatus(billingStatements);
            	break;
            case 4:
                logout();
                clientMenuRunning = false;
                break;
            default:
                System.out.println("Invalid choice. Please enter a valid option.");
        }
        return clientMenuRunning;
	}
    
    public void placeOrder(ArrayList<Product> products, ArrayList<BillingStatement> billingStatements) {
    	System.out.println("Available products:");
        for(Product product:products) {
        	System.out.println(product.getProductId()+ " " + product.getName() + " - $" + product.getPrice());
        }
        System.out.println("Press enter to continue");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine(); // Consume newline
        Map<Product, Integer> selectedProducts = new HashMap<>();
        boolean continueOrdering = true;

        while (continueOrdering) {
            boolean found = false;
            while (!found) {
                System.out.print("Enter product ID to purchase (or type 'done' to finish): ");
                String productId = scanner.nextLine();
                if ("done".equalsIgnoreCase(productId)) {
                    found = true;
                    continueOrdering = false;
                    break; 
                }

                for (Product product : products) {
                    if (productId.equals(product.getProductId())) {
                        found = true;
                        System.out.print("Enter quantity to purchase: ");
                        int quantity;
                        try {
                            quantity = scanner.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid quantity. Please enter a valid integer.");
                            scanner.nextLine(); 
                            continue; 
                        }
                        scanner.nextLine(); 
                        selectedProducts.put(product, quantity);
                        product.quantitySold(quantity);
                        break; 
                    }
                }
                if (!found) {
                    System.out.println("Invalid product ID");
                }
            }
        }

        if (!selectedProducts.isEmpty()) {
            Order order = new Order("O001", new Date(), selectedProducts);
            boolean foundClient = false;
            for (BillingStatement bs : billingStatements) {
            	if(bs.getClientID().equals(getUserId())) {
            		foundClient = true;
            		bs.addOrder(order);
            	}
            }
            if(!foundClient) {
            	BillingStatement newBS = new BillingStatement(getUserId());
            	newBS.addOrder(order);
            	billingStatements.add(newBS);
            }
            
            System.out.println("Order placed successfully.");
            System.out.println("Press enter to continue");
            scanner.nextLine(); // Consume newline
        }else {
        	System.out.println("No products selected");
        }
        
    }
    
    public void makePayment(ArrayList<BillingStatement> billingStatements) {
    	boolean paymentSuccess = false;
    	for(BillingStatement bs : billingStatements) {
    		if(bs.getClientID().equals(getUserId())){
    			bs.makePayment(new Date());
    			paymentSuccess= true;
    		}
    	}
    	if (!paymentSuccess) {
    		System.out.println("No orders found associated with your account.");
    	}
    }
    
    public void viewOrderStatus(ArrayList<BillingStatement> billingStatements) {
    	boolean found = false;
    	for (BillingStatement bs : billingStatements) {
    		if(bs.getClientID().equals(getUserId())) {
    			found = true;
    			List<Order> orders = bs.getPaidOrders();
    			if(!orders.isEmpty())
    			{
    				for (Order order: orders) {
    					if(order.getOrderStatus().equals("Shipped"))
    						System.out.println("Order ID:"+ order.getOrderId() + " has been shipped.");
    					else
    						System.out.println("Order ID:"+ order.getOrderId() + " is being processed.");
    				}
        		
    			}else {
    				System.out.println("Your orders have not been paid. Please pay your order.");
    			}
    		}
    	}
    	if (!found) {
    		System.out.println("You have no orders");
    	}
    }
}
