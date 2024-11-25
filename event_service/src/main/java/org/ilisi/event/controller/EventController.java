package org.ilisi.event.controller;

import org.ilisi.event.entities.Event;
import org.ilisi.event.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }
    @PostMapping("/createEvent")
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        try {
            Event createdEvent = eventService.createEvent(event);
            return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all-details")
    public ResponseEntity<List<Event>> getAllEventsWithDetails() {
        List<Event> events = eventService.getAllEventsWithDetails();
        return ResponseEntity.ok(events);
    }

    // Fetch event with additional details
    @GetMapping("/{id}/details")
    public ResponseEntity<Event> getEventWithDetails(@PathVariable Long id) {
        Event event = eventService.getEventWithDetails(id);
        System.out.println(event);
        return ResponseEntity.ok(event);
    }

    // Fetch event with basic details
    @GetMapping("/{id}/basic")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Optional<Event> event = eventService.getEventById(id);
        return event.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        if (eventService.getEventById(id).isPresent()) {
            eventService.deleteEvent(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/filterByDate/{filter}")
    public ResponseEntity<List<Event>> getEventsByDateFilter(@PathVariable String filter) {
        List<Event> events;
        switch (filter.toLowerCase()) {
            case "today":
                events = eventService.getEventsForToday();
                break;
            case "tomorrow":
                events = eventService.getEventsForTomorrow();
                break;
            case "thisweek":
                events = eventService.getEventsThisWeek();
                break;
            case "afterthisweek":
                events = eventService.getEventsAfterThisWeek();
                break;
            default:
                return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(events);
    }

    @GetMapping("/filterByCategoryAndDate/{categoryName}/{filter}")
    public ResponseEntity<List<Event>> getEventsByCategoryAndDateFilter(
            @PathVariable("categoryName") String categoryName,
            @PathVariable("filter") String filter) {

        List<Event> eventsByCategory = eventService.getEventsByCategoryName(categoryName);
        List<Event> filteredEvents;
        switch (filter.toLowerCase()) {
            case "today":
                filteredEvents = eventService.getEventsForToday().stream()
                        .filter(eventsByCategory::contains)
                        .collect(Collectors.toList());
                break;
            case "tomorrow":
                filteredEvents = eventService.getEventsForTomorrow().stream()
                        .filter(eventsByCategory::contains)
                        .collect(Collectors.toList());
                break;
            case "thisweek":
                filteredEvents = eventService.getEventsThisWeek().stream()
                        .filter(eventsByCategory::contains)
                        .collect(Collectors.toList());
                break;
            case "afterthisweek":
                filteredEvents = eventService.getEventsAfterThisWeek().stream()
                        .filter(eventsByCategory::contains)
                        .collect(Collectors.toList());
                break;
            default:
                return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(filteredEvents);
    }
    @GetMapping("/byPartOfDay/{partOfDay}")
    public ResponseEntity<List<Event>> getEventsByPartOfDay(@PathVariable String partOfDay) {
        try {
            List<Event> events = eventService.findEventsByPartOfDay(partOfDay);
            return ResponseEntity.ok(events);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
        @GetMapping("/user/{userId}/events")
        public List<Event> getEventsByUserId(@PathVariable Long userId) {
            return eventService.getEventsByUserId(userId);
        }
}

