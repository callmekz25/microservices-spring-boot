package com.codewithkz.commoncore.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class BaseDTO implements Serializable {
    protected String id;
    @CreatedDate
    protected Date createdAt;
    @LastModifiedDate
    protected Date updatedAt;
    protected boolean deleted;
}