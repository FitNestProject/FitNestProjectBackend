package org.ilisi.geolocalisation_service.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ilisi.geolocalisation_service.dtos.RouteDto;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import jakarta.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @Column(columnDefinition = "GEOGRAPHY(LineString, 4326)")
    private LineString path;

    // Factory pour créer des géométries
    private static final GeometryFactory geometryFactory = new GeometryFactory();

    // Méthode pour définir un itinéraire en tant que LineString à partir d'une liste de paires de coordonnées
    public void setPathFromCoordinates(List<List<Double>> coordinatesList) {
        if (coordinatesList == null || coordinatesList.isEmpty()) {
            this.path = null;
            return;
        }

        Coordinate[] coordinates = coordinatesList.stream()
                .map(coord -> new Coordinate(coord.get(1), coord.get(0))) // longitude (x), latitude (y)
                .toArray(Coordinate[]::new);

        this.path = geometryFactory.createLineString(coordinates);
        this.path.setSRID(4326); // Définit le SRID pour WGS84
    }

    // Méthode pour obtenir les paires de coordonnées de la ligne sous forme de List<List<Double>>
    public List<List<Double>> getCoordinatesFromPath() {
        if (this.path == null) {
            return null;
        }

        return List.of(this.path.getCoordinates()).stream()
                .map(coord -> List.of(coord.getY(), coord.getX())) // latitude (y), longitude (x)
                .collect(Collectors.toList());
    }
    public RouteDto toDto() {
        RouteDto dto = new RouteDto();
        dto.setId(this.id);
        dto.setCoordinates(this.getCoordinatesFromPath());
        return dto;
    }

    public static Route fromDto(RouteDto dto) {
        Route route = new Route();
        route.setId(dto.getId());
        route.setPathFromCoordinates(dto.getCoordinates());
        return route;
    }

}
