package shrowd.beeper.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.time.LocalDateTime;

public record CommentResponse(
        Long id,
        String content,
        String authorEmail,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        @JacksonXmlProperty(localName = "createdAt")
        LocalDateTime createdAt
) {
}

