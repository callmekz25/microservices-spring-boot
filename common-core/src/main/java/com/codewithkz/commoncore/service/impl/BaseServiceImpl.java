package com.codewithkz.commoncore.service.impl;

import com.codewithkz.commoncore.dto.BaseEntityDTO;
import com.codewithkz.commoncore.exception.NotFoundException;
import com.codewithkz.commoncore.repository.BaseRepository;
import com.codewithkz.commoncore.service.BaseService;

import java.io.Serializable;
import java.util.List;

public abstract class BaseServiceImpl<E extends BaseEntityDTO, ID extends Serializable> implements BaseService<E, ID> {
    protected BaseRepository<E, ID> repository;

    public BaseServiceImpl(BaseRepository<E, ID> repository) {
        this.repository = repository;
    }

    @Override
    public List<E> getAll() {
        return  repository.findAll();
    }

    @Override
    public E getById(ID id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Entity not found"));
    }

    @Override
    public E create(E entity) {
        entity.setDeleted(false);
        return repository.save(entity);
    }

    @Override
    public E update(ID id, E entity) {
        E e = getById(id);
        entity.setId(e.getId());

        return repository.save(entity);
    }

    @Override
    public void delete(ID id) {
        E entity = getById(id);
        entity.setDeleted(true);
        repository.save(entity);
    }
}
