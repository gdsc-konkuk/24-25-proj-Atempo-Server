package juton113.Atempo.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import juton113.Atempo.domain.dto.common.Location;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MlCreateAdmissionRequest {
    private Location location;
    private int searchRadius;

    @JsonProperty("patient_condition")
    private String patientCondition;
}
