package com.pablozoani.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(exclude = "id", callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class VendorDTO extends RepresentationModel<VendorDTO> {

    private Long id;

    private String name;
}
