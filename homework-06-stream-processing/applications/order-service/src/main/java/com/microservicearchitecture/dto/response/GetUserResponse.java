package com.microservicearchitecture.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetUserResponse {
    private Long id;
    private String email;
}
