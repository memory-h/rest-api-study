package com.memory_h.restapi.events.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.validation.BindingResult;

import java.io.IOException;

@JsonComponent  // 이 클래스가 Jackson의 JSON 직렬화 및 역직렬화 컴포넌트로 사용됨을 나타냄
public class ErrorSerializer extends JsonSerializer<BindingResult> { // BindingResult를 JSON으로 직렬화하는 클래스

    @Override
    public void serialize(BindingResult bindingResult, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        // JSON 직렬화 시작, BindingResult는 유효성 검증 결과를 담고 있음
        jsonGenerator.writeStartArray(); // JSON 배열 시작 (여러 오류가 있을 수 있으므로 배열로 시작)

        // 각 필드 오류(FieldError)를 직렬화
        bindingResult.getFieldErrors().forEach(e -> { // 폼 필드에서 발생한 모든 유효성 검증 오류 가져오기
            try {
                jsonGenerator.writeStartObject(); // 각 필드 오류를 JSON 객체로 표현
                jsonGenerator.writeStringField("field", e.getField()); // 필드 이름 추가
                jsonGenerator.writeStringField("objectName", e.getObjectName()); // 객체 이름 추가
                jsonGenerator.writeStringField("code", e.getCode()); // 오류 코드 추가
                jsonGenerator.writeStringField("defaultMessage", e.getDefaultMessage()); // 기본 오류 메시지 추가

                Object rejectedValue = e.getRejectedValue();  // 거부된 값 가져오기
                if (rejectedValue != null) {
                    jsonGenerator.writeStringField("rejectedValue", rejectedValue.toString()); // 거부된 값이 있으면 문자열로 변환하여 추가
                }

                jsonGenerator.writeEndObject(); // 필드 오류에 대한 JSON 객체 종료
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        // 각 전역 오류(ObjectError)를 직렬화
        bindingResult.getGlobalErrors().forEach(e -> { // 전역 오류를 가져와서 처리
            try {
                jsonGenerator.writeStartObject(); // 전역 오류를 JSON 객체로 표현
                jsonGenerator.writeStringField("objectName", e.getObjectName()); // 객체 이름 추가
                jsonGenerator.writeStringField("code", e.getCode()); // 오류 코드 추가
                jsonGenerator.writeStringField("defaultMessage", e.getDefaultMessage()); // 기본 오류 메시지 추가
                jsonGenerator.writeEndObject(); // 전역 오류에 대한 JSON 객체 종료
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        jsonGenerator.writeEndArray();  // JSON 배열 종료
    }
}