package org.ilisi.geolocalisation_service.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.data.annotation.Id;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String locationName;

    @JsonIgnore
    @Column(columnDefinition = "geometry(Point, 4326)")
    private Point coordinates;

    public void setCoordinates(double latitude, double longitude) {
        GeometryFactory geometryFactory = new GeometryFactory();
        this.coordinates = geometryFactory.createPoint(new Coordinate(longitude, latitude));
        this.coordinates.setSRID(4326); // Set the SRID explicitly
    }

    public Double getLatitude() {
        return (coordinates != null) ? coordinates.getY() : null;
    }

    public Double getLongitude() {
        return (coordinates != null) ? coordinates.getX() : null;
    }


    // Custom method to return coordinates as a map
    public Map<String, Double> getCoordinatesAsMap() {
        Map<String, Double> coords = new HashMap<>();
        coords.put("latitude", getLatitude());
        coords.put("longitude", getLongitude());
        return coords;
    }
}
