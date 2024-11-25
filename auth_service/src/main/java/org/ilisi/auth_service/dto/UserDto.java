package org.ilisi.auth_service.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String firstName;
    private String lastName;
    private Long phoneNumber;
    private LocalDate dateBirth;
    private String gender;
    private String description;
    private String idFace;  // Changer en String
    private String idBack;  // Changer en String
    private String profilePicture;  // Changer en String
    private String userName;
    private List<String> interests;
}
