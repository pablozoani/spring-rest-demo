package com.pablozoani.repository;

import com.pablozoani.domain.ProductPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductPhotoRepository extends JpaRepository<ProductPhoto, Long> {

    Optional<ProductPhoto> findProductPhotoByProductId(Long id);
}
