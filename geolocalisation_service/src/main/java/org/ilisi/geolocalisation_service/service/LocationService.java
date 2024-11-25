package org.ilisi.geolocalisation_service.service;

import org.ilisi.geolocalisation_service.dtos.LocationDto;
import org.ilisi.geolocalisation_service.entities.Location;
import org.ilisi.geolocalisation_service.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    // Enregistrer une location à partir de LocationDto
    public LocationDto saveLocation(LocationDto locationDto) {
        Location location = new Location();
        location.setLocationName(locationDto.getLocationName());
        location.setCoordinates(locationDto.getLatitude(), locationDto.getLongitude());

        // Sauvegarder dans la base de données
        Location savedLocation = locationRepository.save(location);

        // Retourner le DTO pour éviter les problèmes de sérialisation
        return convertToDto(savedLocation);
    }

    // Enregistrer plusieurs locations à partir d'une liste de DTO
    public List<LocationDto> saveMultipleLocations(List<LocationDto> locationDTOs) {
        List<Location> locations = locationDTOs.stream().map(dto -> {
            Location location = new Location();
            location.setLocationName(dto.getLocationName());
            location.setCoordinates(dto.getLatitude(), dto.getLongitude());
            return location;
        }).collect(Collectors.toList());

        List<Location> savedLocations = locationRepository.saveAll(locations);

        // Convertir les entités sauvegardées en DTO
        return savedLocations.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Obtenir toutes les locations sous forme de DTO
    public List<LocationDto> getAllLocations() {
        return locationRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Obtenir une location par ID et la convertir en DTO
    public LocationDto getLocationById(Long id) {
        Optional<Location> location = locationRepository.findById(id);
        return location.map(this::convertToDto).orElse(null);
    }

    public LocationDto convertToDto(Location location) {
        Double latitude = location.getLatitude();
        Double longitude = location.getLongitude();
        return new LocationDto(location.getId(),location.getLocationName(), latitude, longitude);
    }

}
