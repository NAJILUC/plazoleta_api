package com.pragma.plazoleta.application.dto.response.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BasicResponse<T> {

    private T data;

    public static <T> BasicResponse<T> of(T data) {
        return new BasicResponse<>(data);
    }
}