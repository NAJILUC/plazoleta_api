package com.pragma.plazoleta.application.dto.response.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private String code;
    private String description;
    private Map<String, String> fields;

    public ErrorResponse(String code, String description) {
        this.code = code;
        this.description = description;
        this.fields = null;
    }
}
