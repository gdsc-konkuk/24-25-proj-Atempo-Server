package juton113.Atempo.domain.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.math.BigDecimal;

@Embeddable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    @Schema(example = "12.345")
    private BigDecimal latitude;

    @Schema(example = "12.345")
    private BigDecimal longitude;

    public static Location of(BigDecimal latitude, BigDecimal longitude) {
        return Location.builder()
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
}
