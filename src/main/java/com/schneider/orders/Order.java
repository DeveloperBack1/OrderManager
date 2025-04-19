package com.schneider.orders;


public class Order {
    private int id;
    private String productName;
    private int quantity;
    private double unitPrice;

    public Order(int id, String productName, int quantity, double unitPrice) {
        this.id = id;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public int getId() { return id; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public double getUnitPrice() { return unitPrice; }

    public double getTotalPrice() {
        return quantity * unitPrice;
    }
}