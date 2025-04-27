package juton113.Atempo.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class MlServerRequestDto {
    BigDecimal latitude;
    BigDecimal longitude;

    @JsonProperty("patient_condition")
    String patientCondition;
}
