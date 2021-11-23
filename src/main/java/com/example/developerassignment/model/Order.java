package com.example.developerassignment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    @JsonIgnore
    @OneToMany(orphanRemoval = true,
            cascade = CascadeType.ALL,
            mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    private String email;

    @Column(name = "order_time")
    private LocalDateTime orderTime;

    @Column(name = "order_value_eur")
    private double orderValueEur;

    public void calculateAndSetOrderValue() {
        this.orderValueEur = orderItems.stream()
                .mapToDouble(e -> e.getProduct().getPriceEur() * e.getQuantity()).sum();
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        orderItems.forEach(this::addOrderItem);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void removeOrderItem(OrderItem orderItem) {
        orderItems.remove(orderItem);
        orderItem.setOrder(null);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderItems=" + orderItems +
                ", email='" + email + '\'' +
                ", orderTime=" + orderTime +
                ", orderValueEur=" + orderValueEur +
                '}';
    }
}
