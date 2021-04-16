package com.microservicearchitecture.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DtoHealthResponse {
    private final String status;
}
