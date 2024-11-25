package org.ilisi.event.entities;

import jakarta.persistence.*;
import lombok.*;
@Entity @Getter @NoArgsConstructor
@Setter
public class SportCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "boolean default false")
    private boolean requiresRoute = false;
    private String iconName;



}
