package org.ilisi.geolocalisation_service.controller;

import org.ilisi.geolocalisation_service.dtos.LocationDto;
import org.ilisi.geolocalisation_service.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    // Créer une nouvelle location
    @PostMapping("/create")
    public ResponseEntity<LocationDto> createLocation(@RequestBody LocationDto locationDto) {
        LocationDto savedLocation = locationService.saveLocation(locationDto);
        return ResponseEntity.ok(savedLocation);
    }
    // Créer plusieurs locations
    @PostMapping("/multiple")
    public ResponseEntity<List<LocationDto>> createMultipleLocations(@RequestBody List<LocationDto> locationDTOs) {
        List<LocationDto> locations = locationService.saveMultipleLocations(locationDTOs);
        return ResponseEntity.ok(locations);
    }

    // Obtenir toutes les locations
    @GetMapping("/getLocations")
    public ResponseEntity<List<LocationDto>> getLocations() {
        try {
            List<LocationDto> locations = locationService.getAllLocations();
            return ResponseEntity.ok(locations);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Obtenir une location par ID
    @GetMapping("getLocation/{id}")
    public ResponseEntity<LocationDto> getLocationById(@PathVariable Long id) {
        LocationDto locationDto = locationService.getLocationById(id);
        if (locationDto != null) {
            return ResponseEntity.ok(locationDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
