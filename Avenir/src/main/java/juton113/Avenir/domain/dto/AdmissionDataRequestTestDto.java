package juton113.Avenir.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdmissionDataRequestTestDto {
    BigDecimal latitude;
    BigDecimal longitude;

    @JsonProperty("patient_condition")
    String patientCondition;

    @JsonProperty("hospital_list")
    private List<CreateHospitalResponseDto> hospitalList;

    @JsonProperty("ars_message")
    private String arsMessage;
}
