package com.pablozoani.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(exclude = "id", callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO extends RepresentationModel<CustomerDTO> {

    private Long id;

    private String firstName;

    private String lastName;
}
