package com.pablozoani.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"id"}, callSuper = false)
public class OrderDTO extends RepresentationModel<OrderDTO> {

    // TODO
    private Long id;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String state;

    private CustomerDTO customer;
}
