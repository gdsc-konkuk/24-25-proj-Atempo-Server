package juton113.Avenir.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAdmissionRequestDto {
    BigDecimal latitude;
    BigDecimal longitude;

    @JsonProperty("patient_condition")
    String patientCondition;
}
