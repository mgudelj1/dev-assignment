package com.example.developerassignment.endpoints.dtos;

import com.example.developerassignment.model.OrderItem;
import lombok.Data;

import java.util.List;

@Data
public class RequestOrderParams {

    //Could possibly be more of the parameters
    private String email;

    private List<OrderItem> orderItemList;

}
