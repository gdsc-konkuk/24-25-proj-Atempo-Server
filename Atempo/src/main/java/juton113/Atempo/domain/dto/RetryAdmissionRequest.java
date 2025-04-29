package juton113.Atempo.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import juton113.Atempo.domain.dto.common.Location;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RetryAdmissionRequest {
    private Location location;

    @JsonProperty("search_radius")
    private int searchRadius;
}
