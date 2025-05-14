package juton113.Atempo.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import juton113.Atempo.domain.enums.AdmissionStatus;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAdmissionResponse {
    @Schema(example = "1")
    private Long admissionId;

    @Schema(example = "SUCCESS or NO_HOSPITAL_FOUND or ERROR")
    private AdmissionStatus admissionStatus;
}
