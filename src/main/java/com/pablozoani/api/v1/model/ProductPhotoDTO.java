package com.pablozoani.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(exclude = "id")
@AllArgsConstructor
@NoArgsConstructor
public class ProductPhotoDTO {

    private Long id;

    private Byte[] photo;
}
