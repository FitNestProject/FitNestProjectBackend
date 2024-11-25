package org.ilisi.auth_service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCheckRequest {

    private String email;
    private String username;
}