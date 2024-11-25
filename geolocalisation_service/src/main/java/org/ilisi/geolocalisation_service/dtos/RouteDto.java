package org.ilisi.geolocalisation_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteDto {

    private Long id;
    private List<List<Double>> coordinates;

}
