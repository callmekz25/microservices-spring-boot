package com.codewithkz.paymentservice.controller;

import com.codewithkz.commoncore.response.ApiResponse;
import com.codewithkz.paymentservice.dto.PaymentDto;
import com.codewithkz.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;


    @GetMapping
    public ResponseEntity<ApiResponse<List<PaymentDto>>> findAll() {
        List<PaymentDto> result = service.findAll();

        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<PaymentDto>> findByOrderId(@PathVariable Long id) {
        PaymentDto result = service.findByOrderId(id);

        return ResponseEntity.ok(ApiResponse.success(result));
    }
}
