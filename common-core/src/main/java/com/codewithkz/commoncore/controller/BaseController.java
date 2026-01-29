package com.codewithkz.commoncore.controller;

import com.codewithkz.commoncore.api.BaseApi;
import com.codewithkz.commoncore.dto.BaseDTO;
import com.codewithkz.commoncore.dto.BaseEntityDTO;
import com.codewithkz.commoncore.mapper.BaseMapper;
import com.codewithkz.commoncore.response.ApiResponse;
import com.codewithkz.commoncore.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseController<E extends BaseEntityDTO, Req extends BaseDTO, Res extends BaseDTO, ID extends Serializable>
        implements BaseApi<Req, Res, ID> {
    protected BaseService<E, ID> baseService;
    protected BaseMapper<E, Req, Res> baseMapper;


    public ResponseEntity<ApiResponse<List<Res>>> getAll() {
        List<E> entities = baseService.getAll();
        List<Res> resList = baseMapper.toDTOList(entities);
        return ResponseEntity.ok(ApiResponse.success(resList));
    };


    public ResponseEntity<ApiResponse<Res>> getById(@PathVariable ID id) {
        E entity = baseService.getById(id);
        Res res = baseMapper.toDTO(entity);
        return ResponseEntity.ok(ApiResponse.success(res));
    };


    public ResponseEntity<ApiResponse<Res>> create(@RequestBody Req req) {
        E entity = baseMapper.toEntity(req);
        E createdEntity = baseService.create(entity);
        Res res = baseMapper.toDTO(createdEntity);
        return ResponseEntity.ok(ApiResponse.success(res));
    };


    public ResponseEntity<ApiResponse<Res>> update(@PathVariable ID id, @RequestBody Req req) {
        E entity = baseService.getById(id);
        baseMapper.updateEntityFromDto(req, entity);
        E updated = baseService.update(id, entity);
        Res res = baseMapper.toDTO(updated);
        return ResponseEntity.ok(ApiResponse.success(res));
    };


    public ResponseEntity<ApiResponse> delete(@PathVariable ID id) {
        baseService.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    };


}
