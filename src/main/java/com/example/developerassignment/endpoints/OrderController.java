package com.example.developerassignment.endpoints;

import com.example.developerassignment.endpoints.dtos.RequestOrderParams;
import com.example.developerassignment.endpoints.dtos.SearchOrderParams;
import com.example.developerassignment.model.Order;
import com.example.developerassignment.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/orders")
@AllArgsConstructor
@Tag(name = "Orders", description = "Order access point")
public class OrderController {

    final OrderService orderService;

    @GetMapping
    @Operation(summary = "Fetching all orders stored in db")
    public ResponseEntity<List<Order>> listAllOrders() {
        return ResponseEntity.ok(orderService.fetchAllOrders());
    }

    @PostMapping
    @Operation(summary = "Create new order")
    public ResponseEntity<Order> placeOrder(@RequestBody RequestOrderParams requestOrder) {
        final Order newOrder = orderService.placeOrder(requestOrder);
        return ResponseEntity.ok(newOrder);
    }

    @PostMapping("/dateRange")
    @Operation(summary = "Find all orders in provided date range")
    public ResponseEntity<List<Order>> fetchAllOrdersForDateRange(@RequestBody SearchOrderParams orderParams) {
        return ResponseEntity.ok(orderService.fetchAllOrdersBetween(orderParams.getStartDate(), orderParams.getEndDate()));
    }


}
