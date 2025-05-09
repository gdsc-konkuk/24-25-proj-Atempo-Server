package juton113.Atempo.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import juton113.Atempo.domain.enums.CertificationType;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMemberCertificationInfoRequest {
    @Schema(example = "KOREA")
    @JsonProperty("certification_type")
    CertificationType certificationType;

    @Schema(example = "MED911,123456,123456789012")
    @JsonProperty("certification_number")
    String certificationNumber;
}
