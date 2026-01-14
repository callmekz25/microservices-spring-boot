package com.codewithkz.productservice;

import com.codewithkz.commoncore.exception.BaseException;
import com.codewithkz.commoncore.exception.NotFoundException;
import com.codewithkz.productservice.dto.CreateDto;
import com.codewithkz.productservice.dto.ProductDto;
import com.codewithkz.productservice.entity.Product;
import com.codewithkz.productservice.infra.outbox.OutboxService;
import com.codewithkz.productservice.infra.rabbitmq.config.RabbitMQConfig;
import com.codewithkz.productservice.mapper.ProductMapper;
import com.codewithkz.productservice.repository.ProductRepository;
import com.codewithkz.productservice.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OutboxService outboxService;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    @Test
    public void shouldReturnAllProducts() {
        List<Product> products = List.of(
                new Product(1L, "Laptop Acer", 1000),
                new Product(2L, "Laptop Dell", 2000)
        );

        List<ProductDto> productDtos = List.of(
                new ProductDto(1L, "Laptop Acer", 1000),
                new ProductDto(2L, "Laptop Dell", 2000)
        );

        Mockito.when(productRepository.findAll()).thenReturn(products);
        Mockito.when(productMapper.toDtoList(products)).thenReturn(productDtos);

        var result = productService.finAll();

        assertNotNull(result);
        assertEquals(productDtos, result);

        Mockito.verify(productRepository).findAll();
        Mockito.verify(productMapper).toDtoList(products);
    }


    @Test
    public void shouldReturnOneProduct() {
        Product product = new Product(1L, "Laptop Acer", 1000);
        ProductDto dto = new ProductDto(1L, "Laptop Acer", 1000);

        Mockito.when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        Mockito.when(productMapper.toDto(product)).thenReturn(dto);

        var result = productService.findById(product.getId());

        assertNotNull(result);
        assertEquals(dto, result);

        Mockito.verify(productRepository).findById(product.getId());
        Mockito.verify(productMapper).toDto(product);
    }

    @Test
    public void shouldThrowNotFoundException() {

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.empty());


        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            productService.findById(1L);
        });


        assertEquals("Product not found", exception.getMessage());

        Mockito.verify(productRepository).findById(1L);
    }

    @Test
    public void shouldCreateProductSuccessfully() {
        CreateDto payload = new CreateDto("Laptop Acer", 1000, 10);
        Product entity =  new Product(null, "Laptop Acer", 1000);
        Product product = new Product(1L, "Laptop Acer", 1000);
        ProductDto dto = new ProductDto(1L, "Laptop Acer", 1000);

        Mockito.when(productMapper.toEntity(payload)).thenReturn(entity);
        Mockito.when(productRepository.save(entity)).thenReturn(product);
        Mockito.when(productMapper.toDto(Mockito.any(Product.class))).thenReturn(dto);

        var result = productService.create(payload);

        assertNotNull(result);
        assertEquals(dto, result);

        Mockito.verify(productMapper).toEntity(payload);
        Mockito.verify(productRepository).save(entity);
        Mockito.verify(productMapper).toDto(Mockito.any(Product.class));

        Mockito.verify(outboxService).save(
                Mockito.eq(RabbitMQConfig.PRODUCT_CREATED_ROUTING_KEY),
                Mockito.eq(RabbitMQConfig.PRODUCT_CREATED_ROUTING_KEY),
                Mockito.any()
        );

    }

    @Test
    public void shouldThrowExceptionWhenCreateProductFailed() {
        CreateDto payload = new CreateDto("Laptop Acer", 1000, 10);
        Product entity = new Product(null, "Laptop Acer", 1000);

        Mockito.when(productMapper.toEntity(payload)).thenReturn(entity);
        Mockito.when(productRepository.save(entity))
                .thenThrow(new RuntimeException("DB error"));

        BaseException exception = assertThrows(BaseException.class, () -> {
            productService.create(payload);
        });

        assertEquals("DB error", exception.getMessage());

        Mockito.verify(productMapper).toEntity(payload);
        Mockito.verify(productRepository).save(entity);


        Mockito.verify(outboxService, Mockito.never()).save(
                Mockito.any(), Mockito.any(), Mockito.any()
        );
    }

}
