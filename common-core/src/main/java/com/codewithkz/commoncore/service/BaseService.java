package com.codewithkz.commoncore.service;

import com.codewithkz.commoncore.dto.BaseEntityDTO;

import java.io.Serializable;
import java.util.List;

public interface BaseService<E, ID extends Serializable>{
    List<E> getAll();
    E getById(ID id);
    E create(E dto);
    E update(ID id, E dto);
    void delete(ID id);
}
