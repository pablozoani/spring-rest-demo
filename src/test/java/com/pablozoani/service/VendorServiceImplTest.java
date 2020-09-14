package com.pablozoani.service;

import com.pablozoani.api.v1.mapper.ProductMapper;
import com.pablozoani.api.v1.mapper.VendorMapper;
import com.pablozoani.api.v1.model.ProductDTO;
import com.pablozoani.api.v1.model.ProductDTOList;
import com.pablozoani.api.v1.model.VendorDTO;
import com.pablozoani.domain.Product;
import com.pablozoani.domain.Vendor;
import com.pablozoani.exception.ResourceNotFoundException;
import com.pablozoani.repository.ProductRepository;
import com.pablozoani.repository.VendorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class VendorServiceImplTest {

    @Mock
    VendorRepository vendorRepository;

    @Mock
    ProductRepository productRepository;

    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    ProductMapper productMapper = ProductMapper.INSTANCE;

    VendorService vendorService;

    @BeforeEach
    void setUp() {
        initMocks(this);
        vendorService = new VendorServiceImpl(vendorMapper, productMapper, productRepository, vendorRepository);
    }

    @Test
    void getAllVendors() {
        // given
        Vendor vendor1 = new Vendor(null, "Western Tasty Fruits Ltd."),
                vendor2 = new Vendor(null, "Exotic Fruits Company"),
                vendor3 = new Vendor(null, "Home Fruits");
        List<Vendor> vendors = asList(vendor1, vendor2, vendor3);
        // when
        when(vendorRepository.findAll()).thenReturn(vendors);
        List<VendorDTO> vendorDTOS = vendorService.getAllVendors();
        // then
        assertEquals(3, vendorDTOS.size());
        vendorDTOS.forEach(Assertions::assertNotNull);
    }

    @Test
    void createVendor() {
        // given
        Vendor vendor = new Vendor(null, "Nuts for Nuts Company");
        // when
        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);
        VendorDTO vendorDTO = vendorService.createVendor(vendorMapper.vendorToDto(vendor));
        // then
        assertNotNull(vendorDTO);
        assertEquals(vendor.getName(), vendorDTO.getName());
    }

    @Test
    void getVendorById() {
        // given
        Vendor vendor = new Vendor(12L, "Nuts for Nuts Company");
        // when
        when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor));
        VendorDTO vendorDTO = vendorService.getVendorById(12L);
        // then
        assertEquals(vendor.getId(), vendorDTO.getId());
        assertEquals(vendor.getName(), vendorDTO.getName());
        // and when
        when(vendorRepository.findById(anyLong())).thenReturn(Optional.empty());
        // then
        assertThrows(ResourceNotFoundException.class, () -> vendorService.getVendorById(12L));
    }

    @Test
    void patchVendor() {
        // given
        Vendor vendor = new Vendor(12L, "Nuts for Nuts Company");
        VendorDTO vendorDTO = new VendorDTO(12L, "Nuts Company");
        // when
        when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor));
        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);
        VendorDTO vendorDTO1 = vendorService.patchVendor(12L, vendorDTO);
        // then
        assertEquals(12L, vendorDTO1.getId());
        assertEquals(vendorDTO.getName(), vendorDTO1.getName());
        verify(vendorRepository).findById(anyLong());
        verify(vendorRepository).save(any(Vendor.class));
    }

    @Test
    void updateVendor() {
        // given
        VendorDTO vendorDTO = new VendorDTO(null, "Exotic Fruits 2");
        // when
        when(vendorRepository.save(any(Vendor.class)))
                .thenReturn(vendorMapper.dtoToVendor(new VendorDTO(12L, "Exotic Fruits 2")));
        VendorDTO vendorDTO1 = vendorService.updateVendor(12L, vendorDTO);
        //
        assertEquals(vendorDTO.getId(), vendorDTO1.getId());
        assertEquals(vendorDTO.getName(), vendorDTO1.getName());
        verify(vendorRepository).save(any(Vendor.class));
    }

    @Test
    void deleteVendor() {
        vendorService.deleteVendorById(12L);
        verify(vendorRepository).deleteById(anyLong());
    }

    @Test
    void deleteVendorById() {
    }

    @Test
    void getProductsByVendorId() {
        // given
        Set<Product> products = new HashSet<>();
        products.add(new Product(1L, "Apple Pack", 10.0));
        Vendor vendor = new Vendor(7L, "Fruit Corp.", products);
        given(vendorRepository.findById(anyLong())).willReturn(Optional.of(vendor));
        // when
        ProductDTOList productsByVendorId = vendorService.getProductsByVendorId(7L);
        // then
        assertEquals(1, productsByVendorId.getProducts().size());
        assertEquals(products.iterator().next().getId(), productsByVendorId.getProducts().get(0).getId());
        verify(vendorRepository).findById(anyLong());
    }

    @Test
    void addProductToVendor() {
        // given
        ProductDTO productDTO = new ProductDTO(5L, "Apple Pack", 10.0);
        Vendor vendor = new Vendor(3L, "Fruit Inc.");
        given(vendorRepository.findById(anyLong())).willReturn(Optional.of(vendor));
        given(productRepository.save(any(Product.class))).willReturn(productMapper.dtoToProduct(productDTO));
        // when
        ProductDTO result = vendorService.addProductToVendor(5L, productDTO);
        // then
        assertEquals(productDTO, result);
        // and given
        given(vendorRepository.findById(anyLong())).willReturn(Optional.empty());
        // when
        assertThrows(ResourceNotFoundException.class, () -> {
            vendorService.addProductToVendor(5L, productDTO);
        });
    }
}