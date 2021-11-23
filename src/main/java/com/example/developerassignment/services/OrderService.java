package com.example.developerassignment.services;

import com.example.developerassignment.endpoints.dtos.RequestOrderParams;
import com.example.developerassignment.model.Order;
import com.example.developerassignment.model.OrderItem;
import com.example.developerassignment.model.Product;
import com.example.developerassignment.repositories.OrderRepository;
import com.example.developerassignment.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {

    final OrderRepository orderRepository;
    final ProductRepository productRepository;
    final EntityManager entityManager;

    //N+1 select problem
    public List<Order> fetchAllOrders() {
        List<Order> orders = entityManager.createQuery("select distinct ord from Order ord join ord.orderItems ordIt " +
                " join ordIt.product", Order.class)
                .getResultList();
        return orders;
    }

    public Order placeOrder(RequestOrderParams requestOrder) {
        final Order entity = createEntityFromRequest(requestOrder);
        orderRepository.saveAndFlush(entity);
        return entity;
    }


    private Order createEntityFromRequest(RequestOrderParams requestOrder) {
        Order order = new Order();
        final List<OrderItem> orderItemList = requestOrder.getOrderItemList();
        final List<Long> productIds = orderItemList.stream().map(item -> item.getProduct().getId()).collect(Collectors.toList());

        final List<Product> productLists = productRepository.findAllById(productIds);
        HashMap<Long, Double> productPrices = new HashMap<>();
        productLists.forEach(e -> productPrices.put(e.getId(), e.getPriceEur()));

        orderItemList.forEach(e -> {
            final Product product = e.getProduct();
            product.setPriceEur(productPrices.get(product.getId()));
        });

        order.setOrderItems(orderItemList);
        order.setEmail(requestOrder.getEmail());
        order.setOrderTime(LocalDateTime.now());
        order.calculateAndSetOrderValue();
        return order;
    }

    public List<Order> fetchAllOrdersBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findByDateRange(startDate, endDate);
    }
}
