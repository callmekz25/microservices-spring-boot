package com.codewithkz.commoncore.api;

import com.codewithkz.commoncore.dto.BaseDTO;
import com.codewithkz.commoncore.dto.BaseEntityDTO;
import com.codewithkz.commoncore.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;
import java.util.List;

public interface BaseApi<Req extends BaseDTO, Res extends BaseDTO, ID extends Serializable> {
    ResponseEntity<ApiResponse<List<Res>>> getAll();
    ResponseEntity<ApiResponse<Res>> getById(@PathVariable ID id);
    ResponseEntity<ApiResponse<Res>> create(@RequestBody Req req);
    ResponseEntity<ApiResponse<Res>> update(@PathVariable ID id, @RequestBody Req req);
    ResponseEntity<ApiResponse> delete(@PathVariable ID id);

}
