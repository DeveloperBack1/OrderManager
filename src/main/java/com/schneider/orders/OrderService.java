package com.schneider.orders;

public class OrderService {
    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public String processOrder(Order order) {
        try {
            int result = repository.saveOrder(order);
            return result > 0 ? "Order processed successfully" : "Order processing failed";
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public double calculateTotal(int id) {
        return repository.getOrderById(id)
                .map(Order::getTotalPrice)
                .orElse(0.0);
    }
}

