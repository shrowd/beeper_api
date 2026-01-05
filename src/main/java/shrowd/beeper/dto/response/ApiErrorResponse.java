package shrowd.beeper.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ApiErrorResponse(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        @JacksonXmlProperty(localName = "createdAt")
        LocalDateTime timestamp,

        int code,
        HttpStatus status,
        String message
) {
    public ApiErrorResponse(int code, HttpStatus status, String message) {
        this(LocalDateTime.now(), code, status, message);
    }
}