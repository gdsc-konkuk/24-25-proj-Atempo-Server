package juton113.Atempo.domain.dto;

import juton113.Atempo.domain.dto.common.Location;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RetryAdmissionDto {
    private Long memberId;
    private Location location;
    private int searchRadius;
    private Long originalAdmissionId;
}
