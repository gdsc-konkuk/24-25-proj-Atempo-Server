package juton113.Avenir.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class AdmissionDataRequestDto {
    BigDecimal latitude;
    BigDecimal longitude;

    @JsonProperty("patient_condition")
    String patientCondition;
}
