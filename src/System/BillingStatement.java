package System;
import java.util.*;
import java.io.*;

public class BillingStatement{
	private String invoiceID;
	private Date invoiceDate;
	private List<Order> unpaidOrders;
	private List<Order> paidOrders;
	private double totalAmount;
	private String clientID;

    public BillingStatement(String clientID) {
        unpaidOrders = new ArrayList<>();
        paidOrders = new ArrayList<>();
        this.clientID = clientID;
        invoiceID = generateInvoiceID();
        setTotalAmount();
    }
    
    public List<Order> getPaidOrders(){
    	return paidOrders;
    }
    
  
    public void setInvoiceDate(Date invoiceDate) {
    	this.invoiceDate = invoiceDate;
    }
    
    public void setTotalAmount() {
    	totalAmount =0.0;
    	for (Order order : unpaidOrders) {
    		totalAmount += order.calculateTotalAmount();
        }
    }
    public String getClientID() {
    	return clientID;
    }
    public String getInvoiceId() {
    	return invoiceID;
    }
    
    public Date getInvoiceDate() {
    	return invoiceDate;
    }
    
    public String generateInvoiceID() {
    	String invID = "INV";
    	for(int i=0; i<6; i++) {
    		Random random = new Random();
        	int digit = random.nextInt(0,9);
        	invID += digit;
    	}
    	return invID;
    }
    // Method to add an order to the billing statement
    public void addOrder(Order order) {
        unpaidOrders.add(order);
        setTotalAmount();
    }
    
    
    public void viewOrder() {
    	for (Order order : unpaidOrders) {
            System.out.println("Order ID: " + order.getOrderId() + " Status: unpaid");
            System.out.println("Ordered Products:");
            for (Map.Entry<Product, Integer> entry : order.getOrderedProducts().entrySet()) {
                Product product = entry.getKey();
                int quantity = entry.getValue();
                System.out.println("Product: " + product.getName() + ", Quantity: " + quantity);
            }
            System.out.println();
        }
    	
    	for (Order order : paidOrders) {
    		System.out.println("Order ID: " + order.getOrderId() + " Status: paid");
            System.out.println("Ordered Products:");
            for (Map.Entry<Product, Integer> entry : order.getOrderedProducts().entrySet()) {
                Product product = entry.getKey();
                int quantity = entry.getValue();
                System.out.println("Product: " + product.getName() + ", Quantity: " + quantity);
            }
            if (order.getOrderStatus() != "Shipped") {
                System.out.println("Confirm to ship this paid order (Y/N)");
                Scanner scanner = new Scanner(System.in);
                char ch;
                boolean validInput = false;
                do {
                    ch = scanner.next().charAt(0);
                    if (ch == 'Y' || ch == 'y' || ch == 'N' || ch == 'n') {
                        validInput = true;
                    } else {
                        System.out.println("Invalid input. Please enter Y/y or N/n.");
                    }
                } while (!validInput);
                if(ch == 'Y' || ch == 'y') {
                    order.setOrderStatus();
                    System.out.println("Order shipped successfully.");
                }
                else {
                    System.out.println("Order not shipped.");
                }
            }
                
            System.out.println(); 
    	}
    }

    public void makePayment(Date billingDate) {
        if (!unpaidOrders.isEmpty()) {
            setInvoiceDate(billingDate);
            System.out.println("Total amount to pay: $" + totalAmount);
            System.out.println("Confirm payment? (Y/N):");
            Scanner scanner = new Scanner(System.in);
            char choice;
            boolean validInput = false;
            do {
                choice = scanner.next().charAt(0);
                if (choice == 'Y' || choice == 'y' || choice == 'N' || choice == 'n') {
                    validInput = true;
                } else {
                    System.out.println("Invalid input. Please enter Y/y or N/n.");
                }
            } while (!validInput);

            if (choice == 'Y' || choice == 'y') {
                paidOrders.addAll(unpaidOrders);
                unpaidOrders.clear();
                System.out.println("Payment successful. Thank you for shopping with us.");
                System.out.println("Press enter to continue");
                scanner.nextLine(); // Consume newline
            } else {
                System.out.println("Payment is unsuccessful.");
            }
        } else {
            System.out.println("All orders are paid.");
        }
    }
    
    public void viewBillingStatement() {
        System.out.println("Invoice ID: " + invoiceID + " Invoice Date: " + invoiceDate + " Client ID: " + clientID
        		+ " Total Amount:" + totalAmount);
    }

   
    
}