package org.ilisi.event.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ilisi.event.dtos.EventDto;
import org.ilisi.event.model.Location;
import org.ilisi.event.model.Route;
import org.ilisi.event.model.User;
import org.springframework.data.annotation.Id;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDate startDate;
    private LocalDate endDate;

    private int maxParticipants;
    private int currentNumParticipants;

    // Le format de l'heure peut être ajusté selon le besoin
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime startTime;

    @Column(columnDefinition = "TEXT")
    private String imagePath;

    // La relation ManyToOne avec SportCategory, il faudra peut-être gérer les exceptions ou valeurs nulles
    @ManyToOne
    @JoinColumn(name = "sport_category_id")
    private SportCategory sportCategory;

    // @Transient pour éviter la persistance en base de données de ces objets
    @Transient
    private Location location;

    @Transient
    private Route route;

    @Transient
    private User user;

    private Long organizerId;

    private Long locationId;
    private Long routeId;

    // Constructeur pour initialiser certaines propriétés sans avoir à créer un objet complet
    public Event(String name, String description, LocalDate startDate, LocalDate endDate,
                 LocalTime startTime, Long locationId, String imagePath,
                 Long routeId, SportCategory sportCategory, Long organizerId) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.locationId = locationId;
        this.routeId = routeId;
        this.sportCategory = sportCategory;
        this.imagePath = imagePath;
        this.organizerId = organizerId;
    }

    // Conversion de l'entité Event en DTO
    public EventDto toDto() {
        EventDto eventDto = new EventDto();
        eventDto.setId(this.id);
        eventDto.setName(this.name);
        eventDto.setDescription(this.description);
        eventDto.setStartDate(this.startDate);
        eventDto.setEndDate(this.endDate);
        eventDto.setStartTime(this.startTime);
        eventDto.setMaxParticipants(this.maxParticipants);
        eventDto.setCurrentNumParticipants(this.currentNumParticipants);
        eventDto.setImagePath(this.imagePath);
        eventDto.setLocationId(this.locationId);
        eventDto.setRouteId(this.routeId);

        if (this.sportCategory != null) {
            eventDto.setSportCategoryId(this.sportCategory.getId());
            eventDto.setSportCategoryName(this.sportCategory.getName());
        } else {
            eventDto.setSportCategoryId(null);
            eventDto.setSportCategoryName("Non spécifié");
        }

        eventDto.setOrganizerId(this.organizerId);
        return eventDto;
    }
}
