package org.ilisi.geolocalisation_service.repository;

import org.ilisi.geolocalisation_service.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}
