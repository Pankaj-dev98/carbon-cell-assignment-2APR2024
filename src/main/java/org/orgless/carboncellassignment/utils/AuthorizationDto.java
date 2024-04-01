package org.orgless.carboncellassignment.utils;

import lombok.Data;

@Data
public class AuthorizationDto {
    private final String accessToken;
    private final String tokenType = "Bearer: ";
}
