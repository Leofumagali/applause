package com.applause.backend.repository;

import com.applause.backend.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    // você pode definir métodos custom aqui, ex:
    // List<Event> findByTitleContaining(String keyword);
}