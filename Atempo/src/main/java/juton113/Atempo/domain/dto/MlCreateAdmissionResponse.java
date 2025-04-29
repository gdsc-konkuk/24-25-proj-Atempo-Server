package juton113.Atempo.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import juton113.Atempo.domain.dto.common.HospitalInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MlCreateAdmissionResponse {
    @JsonProperty("hospital_list")
    private List<HospitalInfo> hospitalList;

    @JsonProperty("ars_message")
    private String arsMessage;

}
