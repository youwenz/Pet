package System;
import java.util.*;
import java.io.*;

public class Order{
	private String orderId;
    private Date orderDate;
    Map<Product, Integer> orderedProducts;
    private OrderStatus orderStatus;

    public Order(String orderId, Date orderDate, Map<Product, Integer> orderedProducts) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderedProducts = orderedProducts;
        this.orderStatus= OrderStatus.Processing;
    }
    
    public Map<Product, Integer> getOrderedProducts(){
    	return orderedProducts;
    }
    public String getOrderId() {
    	return orderId;
    }
    
    public String getOrderStatus() {
    	return orderStatus.toString();
    }
    
    public void setOrderStatus() {
    	this.orderStatus = OrderStatus.Shipped;
    }
    
    public double calculateTotalAmount() {
    	double totalAmount =0.0;
    	for (Map.Entry<Product, Integer> entry : orderedProducts.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            totalAmount += product.getPrice() * quantity;
        }
        return totalAmount;
    }
    
}
