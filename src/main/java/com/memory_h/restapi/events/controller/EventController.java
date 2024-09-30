package com.memory_h.restapi.events.controller;

import com.memory_h.restapi.events.domain.Event;
import com.memory_h.restapi.events.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_VALUE) // 해당 타입으로 응답을 보낸다.
public class EventController {

    private final EventRepository eventRepository;

    /**
     * linkTo(EventController.class): EventController 클래스의 경로(URI)를 기준으로 링크를 생성하는 역할을 한다.
     * EventController 클래스가 @RequestMapping 어노테이션을 통해 매핑된 기본 URL 경로를 기반으로 한다.
     * 즉, 기본 경로는 /api/events 이다.
     *
     * slash("{id}")는 기본 경로에 추가적인 세그먼트를 붙이는 작업을 한다. 예: /api/events/10
     * toUri(): 실제 URI 객체로 변환한다.
     */
    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        Event newEvent = eventRepository.save(event);

        URI createUri = linkTo(EventController.class).slash(newEvent.getId()).toUri();
        event.setId(10);

        return ResponseEntity.created(createUri).body(event);
    }

}