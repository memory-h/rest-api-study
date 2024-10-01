package com.memory_h.restapi.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.memory_h.restapi.events.domain.Event;
import com.memory_h.restapi.events.domain.EventStatus;
import com.memory_h.restapi.events.dto.EventDto;
import com.memory_h.restapi.events.repository.EventRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WebMvcTest
@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    // 테스트 대상 객체에 해당 빈이 의존하고 있다면, 모의 객체가 주입
//    @MockBean
//    EventRepository eventRepository;

    @Test
    void createEvent() throws Exception {
        EventDto event = EventDto.builder()
                .name("Spring")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2024, 9, 30, 10, 37))
                .closeEnrollmentDateTime(LocalDateTime.of(2024, 10, 1, 10, 37))
                .beginEventDateTime(LocalDateTime.of(2024, 10, 2, 10, 37))
                .endEventDateTime(LocalDateTime.of(2024, 10, 3, 10, 37))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타텁 팩토리")
                .build();

//        Mockito.when(eventRepository.save(event)).thenReturn(event);

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON) // 요청 또는 응답에서 전송되는 데이터의 타입, 즉 내가 보내는 데이터의 형식
                        .accept(MediaTypes.HAL_JSON_VALUE) // 클라이언트가 서버에게 어떤 형식의 응답 데이터를 받을 수 있는지, 즉 내가 받고 싶은 데이터의 형식
                        .content(objectMapper.writeValueAsString(event)) // json으로 바꾸고 본문에 넣어준다.
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists()) // id가 있는지
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("id").value(Matchers.not(100)))
                .andExpect(jsonPath("free").value(Matchers.not(true)))
                .andExpect(jsonPath("eventStatus").value(EventStatus.DRAFT.name()));
    }

    @Test
    void createEvent_BAD_REQUEST() throws Exception {
        Event event = Event.builder()
                .id(100)
                .name("Spring")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2024, 9, 30, 10, 37))
                .closeEnrollmentDateTime(LocalDateTime.of(2024, 10, 1, 10, 37))
                .beginEventDateTime(LocalDateTime.of(2024, 10, 2, 10, 37))
                .endEventDateTime(LocalDateTime.of(2024, 10, 3, 10, 37))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타텁 팩토리")
                .free(true)
                .offline(false)
                .eventStatus(EventStatus.PUBLISHED)
                .build();

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON) // 요청 또는 응답에서 전송되는 데이터의 타입, 즉 내가 보내는 데이터의 형식
                        .accept(MediaTypes.HAL_JSON_VALUE) // 클라이언트가 서버에게 어떤 형식의 응답 데이터를 받을 수 있는지, 즉 내가 받고 싶은 데이터의 형식
                        .content(objectMapper.writeValueAsString(event)) // json으로 바꾸고 본문에 넣어준다.
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void createEvent_BAD_REQUEST_Empty_Input() throws Exception {
        EventDto eventDto = EventDto.builder().build();

        mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(objectMapper.writeValueAsString(eventDto))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void createEvent_BAD_REQUEST_Wrong_Input() throws Exception {
        EventDto eventDto = EventDto.builder()
                .name("Spring")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2024, 9, 26, 10, 37))
                .closeEnrollmentDateTime(LocalDateTime.of(2024, 10, 25, 10, 37))
                .beginEventDateTime(LocalDateTime.of(2024, 10, 24, 11, 37))
                .endEventDateTime(LocalDateTime.of(2024, 10, 23, 12, 37))
                .basePrice(10000)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타텁 팩토리")
                .build();

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaTypes.HAL_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(eventDto))
                )
                .andExpect(status().isBadRequest());
    }

}