package com.memory_h.restapi.events.repository;

import com.memory_h.restapi.events.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {

}