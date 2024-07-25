package System;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

class Product {
    private String productId;
    private String name;
    private String description;
    private double price;
    private int quantity;

    public Product(String productId, String name, String description, double price, int quantity) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public String getProductId(){
    	return productId;
    }
    
    public void setProductId(String id) {
    	productId = id;
    }
    
    public String getName() {
    	return name;
    }
    
    public void setName(String name) {
    	this.name =name;
    }
    
    public String getDescription() {
    	return description;
    }
    
    public void setDescription(String desc) {
    	description = desc;
    }
    
    public double getPrice() {
    	return price;
    }
    
    public void setPrice(double price) {
    	this.price = price;
    }
    
    public void setQuantity(int quantity) {
    	this.quantity = quantity;
    }
    
    public void quantitySold(int quantitySold) {
    	this.quantity -= quantitySold;
    }
    
    public int getQuantity() {
    	return quantity;
    }
    
    public static ArrayList<Product> loadProductsFromFile(String filename) {
        ArrayList<Product> products = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] productInfo = line.split(",");
                String productId = productInfo[0];
                String name = productInfo[1];
                String description = productInfo[2];
                String pr = productInfo[3];
                String quant = productInfo[4];
                double price = Double.parseDouble(pr);
                int quantity = Integer.parseInt(quant);
                Product product = new Product(productId, name, description, price, quantity);
                products.add(product);
            }
        } catch (IOException e) {
            System.err.println("Error loading products from file: " + e.getMessage());
        }
        return products;
    }
    
    public static void writeProductsToFile(ArrayList<Product> products, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (Product pr : products) {
                String productInfo = pr.getProductId() + "," + pr.getName() + "," + pr.getDescription() + "," + Double.toString(pr.getPrice()) + "," + Integer.toString(pr.getQuantity());
                writer.write(productInfo + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing products' information to file: " + e.getMessage());
        }
    }
}
