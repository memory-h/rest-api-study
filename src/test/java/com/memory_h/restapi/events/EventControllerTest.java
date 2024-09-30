package com.memory_h.restapi.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class EventControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void createEvent() throws Exception {
        Event event = Event.builder()
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

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON) // 요청 또는 응답에서 전송되는 데이터의 타입, 즉 내가 보내는 데이터의 형식
                        .accept(MediaTypes.HAL_JSON_VALUE) // 클라이언트가 서버에게 어떤 형식의 응답 데이터를 받을 수 있는지, 즉 내가 받고 싶은 데이터의 형식
                        .content(objectMapper.writeValueAsString(event)) // json으로 바꾸고 본문에 넣어준다.
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists()); // id가 있는지
    }

}