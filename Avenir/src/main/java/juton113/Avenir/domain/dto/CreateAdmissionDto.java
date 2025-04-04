package juton113.Avenir.domain.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class CreateAdmissionDto {
    Long memberId;
    BigDecimal latitude;
    BigDecimal longitude;
    String patientCondition;
}
