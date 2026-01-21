package com.codewithkz.authservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountCredential {
    private String username;
    private String password;
}
