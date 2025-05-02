package juton113.Atempo.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import juton113.Atempo.domain.dto.common.Location;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAdmissionRequest {
    private Location location;

    @Schema(example = "5")
    @JsonProperty("search_radius")
    private int searchRadius;

    @Schema(example = "test_patient_condition")
    @JsonProperty("patient_condition")
    private String patientCondition;
}
