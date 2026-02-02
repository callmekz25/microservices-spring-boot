package com.codewithkz.orderservice.service.client;

import com.codewithkz.commoncore.response.ApiResponse;
import com.codewithkz.orderservice.dto.PaymentCreateUpdateRequestDTO;
import com.codewithkz.orderservice.dto.PaymentCreateUpdateResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service")
public interface PaymentServiceIntegration {
    @PostMapping("/api/payments")
    ApiResponse<PaymentCreateUpdateResponseDTO> create(@RequestBody PaymentCreateUpdateRequestDTO dto);
}
