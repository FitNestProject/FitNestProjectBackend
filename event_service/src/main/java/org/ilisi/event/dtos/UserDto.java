package org.ilisi.event.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String gender;
    private LocalDate dateBirth;
    private String description;
    private String profilePicture;

    private String username;
    private String email;
    private Long phoneNumber;

    private String interests;  // Liste d'intérêts sous forme de chaîne, ou bien une liste si nécessaire
}
