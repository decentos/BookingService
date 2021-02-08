package me.decentos.error;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Data
public class ErrorResponseDto {

    private final HttpStatus status;
    private final String summary;
    private final String detail;

    public int getCode() {
        return status.value();
    }

}
