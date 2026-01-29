package com.codewithkz.productservice.mapper;

import com.codewithkz.commoncore.mapper.BaseMapper;
import com.codewithkz.productservice.dto.ProductCreateUpdateRequestDTO;
import com.codewithkz.productservice.dto.ProductCreateUpdateResponseDTO;
import com.codewithkz.productservice.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper extends BaseMapper<Product, ProductCreateUpdateRequestDTO, ProductCreateUpdateResponseDTO> {
}
