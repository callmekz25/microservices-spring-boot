//package com.codewithkz.productservice;
//
//import com.codewithkz.commoncore.exception.BaseException;
//import com.codewithkz.commoncore.exception.NotFoundException;
//import com.codewithkz.productservice.dto.ProductCreateUpdateRequestDTO;
//import com.codewithkz.productservice.dto.ProductDto;
//import com.codewithkz.productservice.model.Product;
//import com.codewithkz.productservice.service.impl.OutboxServiceImpl;
//import com.codewithkz.productservice.mapper.ProductMapper;
//import com.codewithkz.productservice.repository.ProductRepository;
//import com.codewithkz.productservice.service.impl.ProductServiceImpl;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(MockitoExtension.class)
//public class ProductServiceTest {
//
//    @Mock
//    private ProductRepository productRepository;
//
//    @Mock
//    private OutboxServiceImpl outboxService;
//
//    @Mock
//    private ProductMapper productMapper;
//
//    @InjectMocks
//    private ProductServiceImpl productService;
//
//    @Test
//    public void shouldReturnAllProducts() {
//        List<Product> products = List.of(
//                new Product(1L, "Laptop Acer", 1000),
//                new Product(2L, "Laptop Dell", 2000)
//        );
//
//        List<ProductDto> productDtos = List.of(
//                new ProductDto(1L, "Laptop Acer", 1000),
//                new ProductDto(2L, "Laptop Dell", 2000)
//        );
//
//        Mockito.when(productRepository.findAll()).thenReturn(products);
//        Mockito.when(productMapper.toDTOList(products)).thenReturn(productDtos);
//
//        var result = productService.finAll();
//
//        assertNotNull(result);
//        assertEquals(productDtos, result);
//
//        Mockito.verify(productRepository).findAll();
//        Mockito.verify(productMapper).toDTOList(products);
//    }
//
//
//    @Test
//    public void shouldReturnOneProduct() {
//        Product product = new Product(1L, "Laptop Acer", 1000);
//        ProductDto dto = new ProductDto(1L, "Laptop Acer", 1000);
//
//        Mockito.when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
//        Mockito.when(productMapper.toDTO(product)).thenReturn(dto);
//
//        var result = productService.findById(product.getId());
//
//        assertNotNull(result);
//        assertEquals(dto, result);
//
//        Mockito.verify(productRepository).findById(product.getId());
//        Mockito.verify(productMapper).toDTO(product);
//    }
//
//    @Test
//    public void shouldThrowNotFoundException() {
//
//        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.empty());
//
//
//        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
//            productService.findById(1L);
//        });
//
//
//        assertEquals("Product not found", exception.getMessage());
//
//        Mockito.verify(productRepository).findById(1L);
//    }
//
//    @Test
//    public void shouldCreateProductSuccessfully() {
//        ProductCreateUpdateRequestDTO payload = new ProductCreateUpdateRequestDTO("Laptop Acer", 1000, 10);
//        Product model =  new Product(null, "Laptop Acer", 1000);
//        Product product = new Product(1L, "Laptop Acer", 1000);
//        ProductDto dto = new ProductDto(1L, "Laptop Acer", 1000);
//
//        Mockito.when(productMapper.toEntity(payload)).thenReturn(model);
//        Mockito.when(productRepository.save(model)).thenReturn(product);
//        Mockito.when(productMapper.toDTO(Mockito.any(Product.class))).thenReturn(dto);
//
//        var result = productService.create(payload);
//
//        assertNotNull(result);
//        assertEquals(dto, result);
//
//        Mockito.verify(productMapper).toEntity(payload);
//        Mockito.verify(productRepository).save(model);
//        Mockito.verify(productMapper).toDTO(Mockito.any(Product.class));
//
//        Mockito.verify(outboxService).save(
//                Mockito.any(),
//                Mockito.any()
//        );
//
//    }
//
//    @Test
//    public void shouldThrowExceptionWhenCreateProductFailed() {
//        ProductCreateUpdateRequestDTO payload = new ProductCreateUpdateRequestDTO("Laptop Acer", 1000, 10);
//        Product model = new Product(null, "Laptop Acer", 1000);
//
//        Mockito.when(productMapper.toEntity(payload)).thenReturn(model);
//        Mockito.when(productRepository.save(model))
//                .thenThrow(new RuntimeException("DB error"));
//
//        BaseException exception = assertThrows(BaseException.class, () -> {
//            productService.create(payload);
//        });
//
//        assertEquals("DB error", exception.getMessage());
//
//        Mockito.verify(productMapper).toEntity(payload);
//        Mockito.verify(productRepository).save(model);
//
//
//        Mockito.verify(outboxService, Mockito.never()).save(
//                Mockito.any(), Mockito.any()
//        );
//    }
//
//}
