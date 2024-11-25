package org.ilisi.auth_service.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthEvent implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long idEvent;
    private Long idCoordinator;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private int maxParticipants;
    private int currentNumParticipants;
    private SportCategory sportCategory;  // Include this if you need it

    // Optionally add location as latitude and longitude, if relevant
    private String location;  // Modify based on your needs
}
