package shrowd.beeper.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdatePostRequest(
        @NotBlank
        @Size(max = 150)
        String title,

        @NotBlank
        String content
) {
}
