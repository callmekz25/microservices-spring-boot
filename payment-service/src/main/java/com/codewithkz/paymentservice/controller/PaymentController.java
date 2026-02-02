package com.codewithkz.paymentservice.controller;

import com.codewithkz.commoncore.controller.BaseController;
import com.codewithkz.commoncore.response.ApiResponse;
import com.codewithkz.paymentservice.dto.PaymentCreateUpdateRequestDTO;
import com.codewithkz.paymentservice.dto.PaymentCreateUpdateResponseDTO;
import com.codewithkz.paymentservice.mapper.PaymentMapper;
import com.codewithkz.paymentservice.model.Payment;
import com.codewithkz.paymentservice.service.impl.PaymentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController extends BaseController<Payment, PaymentCreateUpdateRequestDTO, PaymentCreateUpdateResponseDTO, String> {

    private final PaymentServiceImpl service;
    private final PaymentMapper mapper;

    public PaymentController(PaymentServiceImpl service, PaymentMapper mapper) {
        super(service, mapper);
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PaymentCreateUpdateResponseDTO>>> getAll() {
        return super.getAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<PaymentCreateUpdateResponseDTO>> getById(@PathVariable String id) {
        return super.getById(id);
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<PaymentCreateUpdateResponseDTO>> create(@RequestBody PaymentCreateUpdateRequestDTO dto) {
        return super.create(dto);
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<PaymentCreateUpdateResponseDTO>> update(@RequestBody PaymentCreateUpdateRequestDTO dto, @PathVariable String id) {
        return super.update(id, dto);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable String id) {
        return super.delete(id);
    }

    @GetMapping("orders/{id}")
    public ResponseEntity<ApiResponse<PaymentCreateUpdateResponseDTO>> getByOrderId(@PathVariable String id) {
        var result = service.getByOrderId(id);
        var dto = mapper.toDTO(result);
        return ResponseEntity.ok(ApiResponse.success(dto));
    }
}
