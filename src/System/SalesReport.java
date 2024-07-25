package System;
import java.util.*;

public class SalesReport {
	private Date salesReportDate;
	private String salesReportID;
    private List<Order> orders;

    public SalesReport(List<Order> orders, Date date) {
        this.orders = orders;
        salesReportDate = date;
        salesReportID = generateReportID();
    }
    
    public String generateReportID() {
    	String ID ="SR";
    	for(int i=0; i<6; i++) {
    		Random random = new Random();
    		int digit = random.nextInt(0,9);
    		ID += digit;
    	}
    	return ID;
    }
    

    // Method to generate the sales report
    public void generateReport() {
        System.out.println("Sales Report ID:" + salesReportID + " Generated on:" + salesReportDate);
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.printf("%-15s %-20s %-20s %-20s %-20s%n", 
                          "Product ID", "Product Name", "Unit Price", "Quantity Sold", "Total Revenue");
        System.out.println("---------------------------------------------------------------------------------------------");

        // Initialize variables to track total revenue
        double totalRevenue = 0;

        // Loop through each order
        for (Order order : orders) {
            // Get ordered products from the order
            Map<Product, Integer> orderedProducts = order.getOrderedProducts();
            
            // Loop through each ordered product in the order
            for (Map.Entry<Product, Integer> entry : orderedProducts.entrySet()) {
                Product product = entry.getKey();
                int quantitySold = entry.getValue();
                double unitPrice = product.getPrice();
                double productRevenue = unitPrice * quantitySold;
                totalRevenue += productRevenue;

                // Print product information
                System.out.printf("%-15s %-20s $%-20.2f %-20d $%-20.2f%n", 
                                  product.getProductId(), product.getName(), unitPrice, quantitySold, productRevenue);
            }
        }

        // Print total revenue
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("Total Revenue: $" + totalRevenue);
        System.out.println("Press enter to continue");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }
}
