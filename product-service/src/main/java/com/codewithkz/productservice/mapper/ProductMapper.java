package com.codewithkz.productservice.mapper;

import com.codewithkz.productservice.dto.CreateDto;
import com.codewithkz.productservice.dto.ProductDto;
import com.codewithkz.productservice.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toEntity(CreateDto dto);
    ProductDto toDto(Product entity);
    List<ProductDto> toDtoList(List<Product> entities);
}
