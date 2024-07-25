package System;
import java.util.*;

public class InventoryReport {
	private Date inventoryReportDate;
	private String reportID;
	private ArrayList<Product> inventory;

    public InventoryReport(ArrayList<Product> products, Date date) {
        inventory = products;
        inventoryReportDate = date;
        reportID = generateReportID();
    }
    
    public String generateReportID() {
    	String ID ="IR";
    	for(int i=0; i<6; i++) {
    		Random random = new Random();
    		int digit = random.nextInt(0,9);
    		ID += digit;
    	}
    	return ID;
    }

    // Method to get inventory of a product
    public void generateReport() {
        System.out.println("Inventory Report id:" + reportID + " Generated on: " + inventoryReportDate);
        System.out.println("--------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-15s %-20s %-30s %-15s %-15s %-20s%n", 
                          "Product ID", "Name", "Description", "Quantity", "Price per Unit", "Total Value");
        System.out.println("--------------------------------------------------------------------------------------------------------------");
        for (Product product : inventory) {
            double totalValue = product.getPrice() * product.getQuantity();
            System.out.printf("%-15s %-20s %-30s %-15d $%-15.2f $%-20.2f%n",
                              product.getProductId(), product.getName(), product.getDescription(),
                              product.getQuantity(), product.getPrice(), totalValue);
        }
        System.out.println("--------------------------------------------------------------------------------------------------------------");
    }
    
}

