package juton113.Atempo.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import juton113.Atempo.domain.dto.common.Location;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MlCreateAdmissionRequest {
    private Location location;

    @JsonProperty("search_radius")
    private int searchRadius;

    @JsonProperty("patient_condition")
    private String patientCondition;
}
