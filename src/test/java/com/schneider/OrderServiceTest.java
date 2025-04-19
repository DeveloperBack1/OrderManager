package com.schneider;


import com.schneider.orders.Order;
import com.schneider.orders.OrderRepository;
import com.schneider.orders.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {
    private OrderRepository orderRepository;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        orderService = new OrderService(orderRepository);
    }

    // === Тесты processOrder ===
    @Test
    void shouldProcessOrderSuccessfully() {
        Order order = createOrder();
        when(orderRepository.saveOrder(order)).thenReturn(1);

        String result = orderService.processOrder(order);

        assertEquals("Order processed successfully", result);
        verify(orderRepository, times(1)).saveOrder(order);
    }

    @Test
    void shouldFailToProcessOrder() {
        Order order = createOrder();
        when(orderRepository.saveOrder(order)).thenReturn(0);

        String result = orderService.processOrder(order);

        assertEquals("Order processing failed", result);
        verify(orderRepository, times(1)).saveOrder(order);
    }

    @Test
    void shouldThrowExceptionWhenRepositoryFails() {
        Order order = createOrder();
        when(orderRepository.saveOrder(order)).thenThrow(new RuntimeException("Database error"));

        Exception exception = assertThrows(RuntimeException.class, () -> orderService.processOrder(order));
        assertEquals("Database error", exception.getMessage());
        verify(orderRepository, times(1)).saveOrder(order);
    }

    // === Тесты calculateTotal ===
    @Test
    void shouldCalculateTotalSuccessfully() {
        Order order = new Order(1, "Monitor", 2, 250.0);
        when(orderRepository.getOrderById(1)).thenReturn(Optional.of(order));

        double result = orderService.calculateTotal(1);

        assertEquals(500.0, result);
        verify(orderRepository, times(1)).getOrderById(1);
    }

    @Test
    void shouldReturnZeroIfOrderNotFound() {
        when(orderRepository.getOrderById(1)).thenReturn(Optional.empty());

        double result = orderService.calculateTotal(1);

        assertEquals(0.0, result);
        verify(orderRepository, times(1)).getOrderById(1);
    }

    @Test
    void shouldHandleZeroQuantityOrPrice() {
        Order order = new Order(1, "Mouse", 0, 100.0);
        when(orderRepository.getOrderById(1)).thenReturn(Optional.of(order));

        double result = orderService.calculateTotal(1);

        assertEquals(0.0, result);
        verify(orderRepository, times(1)).getOrderById(1);
    }

    // === Хелпер ===
    private Order createOrder() {
        return new Order(1, "Laptop", 2, 1500.0);
    }
}