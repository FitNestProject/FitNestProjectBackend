package org.ilisi.geolocalisation_service.controller;

import org.ilisi.geolocalisation_service.dtos.RouteDto;
import org.ilisi.geolocalisation_service.entities.Route;
import org.ilisi.geolocalisation_service.service.RouteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/routes")
public class RouteRestController {

    private final RouteService routeService;

    // Injection par constructeur
    public RouteRestController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping("/getRoutes")
    public List<RouteDto> getAllRoutes() {
        return routeService.getAllRoutes()
                .stream()
                .map(Route::toDto)
                .collect(Collectors.toList());
    }



    @PostMapping("/create")
    public ResponseEntity<RouteDto> createRoute(@RequestBody RouteDto routeDto) {
        Route route = Route.fromDto(routeDto);
        Route savedRoute = routeService.saveRoute(route);
        return ResponseEntity.ok(savedRoute.toDto());
    }


    @GetMapping("/{id}")
    public ResponseEntity<RouteDto> getRouteById(@PathVariable Long id) {
        Optional<Route> route = routeService.getRouteById(id);
        return route.map(r -> ResponseEntity.ok(r.toDto())).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoute(@PathVariable Long id) {
        if (routeService.getRouteById(id).isPresent()) {
            routeService.deleteRoute(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
