package juton113.Atempo.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateHospitalCallStatusDto {
    String callId;
    String responseDigit;
}
