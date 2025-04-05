package juton113.Avenir.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AdmissionDataResponseDto {
    @JsonProperty("hospital_list")
    private List<CreateHospitalResponseDto> hospitalList;

    @JsonProperty("ars_message")
    private String arsMessage;

}
