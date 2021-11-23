package com.example.developerassignment.endpoints.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchOrderParams {

    //Could possibly be more of the parameters
    private LocalDateTime startDate;

    private LocalDateTime endDate;
}
