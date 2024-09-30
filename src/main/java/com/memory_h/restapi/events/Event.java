package com.memory_h.restapi.events;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Event {

    private Integer id;

    private String name;

    private String description;

    private LocalDateTime beginEnrollmentDateTime;

    private LocalDateTime closeEnrollmentDateTime;

    private LocalDateTime beginEventDateTime;

    private LocalDateTime endEventDateTime;

    private String location; // (optional) 없으면 온라인 모임

    private int basePrice; // (optional) 등록비

    private int maxPrice; // (optional) 등록비

    private int limitOfEnrollment; // 참가 인원 제한

    private boolean offline;

    private boolean free;

    private EventStatus eventStatus;

}