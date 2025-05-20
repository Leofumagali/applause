package com.applause.backend.controller;

import com.applause.backend.model.Event;
import com.applause.backend.service.EventService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        return eventService.getEventById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@Valid @RequestBody Event event) {
        Event saved = eventService.createEvent(event);
        return ResponseEntity.ok(saved);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return eventService.updateEvent(id, updates)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        if (eventService.getEventById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}