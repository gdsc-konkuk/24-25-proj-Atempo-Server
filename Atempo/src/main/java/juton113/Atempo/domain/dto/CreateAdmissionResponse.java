package juton113.Atempo.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAdmissionResponse {
    @Schema(example = "1")
    private Long admissionId;
}
