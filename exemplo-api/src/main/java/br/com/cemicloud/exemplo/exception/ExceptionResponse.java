package br.com.cemicloud.exemplo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionResponse {

    private Integer code;
    private String message;
}
