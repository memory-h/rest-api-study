package com.memory_h.restapi.events.validator;

import com.memory_h.restapi.events.dto.EventDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDateTime;

@Component
public class EventValidator {

    public void validate(EventDto eventDto, BindingResult bindingResult) {
        if (eventDto.getBasePrice() > eventDto.getMaxPrice() && eventDto.getMaxPrice() != 0) {
            bindingResult.rejectValue("basePrice", "wrongValue", "basePrice is wrong.");
            bindingResult.rejectValue("maxPrice", "wrongValue", "maxPrice is wrong.");
        }

        LocalDateTime endEventDateTime = eventDto.getEndEventDateTime();

        if (endEventDateTime.isBefore(eventDto.getBeginEventDateTime()) ||
                endEventDateTime.isBefore(eventDto.getCloseEnrollmentDateTime()) ||
                endEventDateTime.isBefore(eventDto.getBeginEnrollmentDateTime())) {

            bindingResult.rejectValue("endEventDateTime", "wrongValue", "endEventDateTime is wrong.");
        }

        // TODO beginEvent
        // TODO CloseEnrollmentDateTime
    }

}