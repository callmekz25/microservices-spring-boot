package com.codewithkz.inventoryservice.mapper;


import com.codewithkz.inventoryservice.dto.InventoryDto;
import com.codewithkz.inventoryservice.entity.Inventory;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
    InventoryDto toDto(Inventory inventory);
    List<InventoryDto> toDtoList(List<Inventory> inventoryList);
}
