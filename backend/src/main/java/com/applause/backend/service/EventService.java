package com.applause.backend.service;

import com.applause.backend.model.Event;
import com.applause.backend.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.lang.reflect.Field;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public Optional<Event> updateEvent(Long id, Map<String, Object> updates) {
        return eventRepository.findById(id).map(event -> {
            updates.forEach((key, value) -> {
                try {
                    Field field = Event.class.getDeclaredField(key);
                    field.setAccessible(true);

                    Object convertedValue = switch (field.getType().getSimpleName()) {
                        case "BigDecimal" -> new BigDecimal(value.toString()).setScale(2, RoundingMode.HALF_UP);
                        case "LocalDateTime" -> LocalDateTime.parse(value.toString());
                        default -> value;
                    };

                    field.set(event, convertedValue);
                } catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException e) {
                    System.out.println("Invalid field: " + key + " → " + e.getMessage());
                }
            });

            return eventRepository.save(event);
        });
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
}