package com.codewithkz.productservice;


import com.codewithkz.commoncore.exception.BadRequestException;
import com.codewithkz.productservice.controller.ProductController;
import com.codewithkz.productservice.dto.CreateDto;
import com.codewithkz.productservice.dto.ProductDto;
import com.codewithkz.productservice.entity.Product;
import com.codewithkz.productservice.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @Test
    public void findAll() throws Exception {
        List<ProductDto> list = List.of(
                new ProductDto(1L, "Laptop Acer", 1000),
                new ProductDto(2L, "Laptop Dell", 2000)
        );

        Mockito.when(productService.finAll()).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.length()").value(2))
                .andDo(print());
    }

    @Test
    public void shouldCreateOk() throws Exception {
        CreateDto dto = new CreateDto("Laptop Acer", 1000, 10);
        ProductDto response = new ProductDto(1L, dto.getName(), dto.getPrice());

        Mockito.when(productService.create(dto)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.length()").value(3))
                .andDo(print());
    }

    @Test
    public void shouldCreateFail() throws Exception {
        CreateDto dto = new CreateDto("Laptop Acer", 1000, 10);


        Mockito.when(productService.create(dto)).thenThrow(new BadRequestException("Something went wrong"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.data").isEmpty())
                .andDo(print());
    }


}
