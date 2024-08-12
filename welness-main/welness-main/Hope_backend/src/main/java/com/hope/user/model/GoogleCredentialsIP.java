package com.hope.user.model;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoogleCredentialsIP {
    private String accessToken;
    private String tokenType;
    private Integer expiresIn;
    private String scope;
}
