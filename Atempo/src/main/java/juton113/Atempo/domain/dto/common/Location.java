package juton113.Atempo.domain.dto.common;

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
    private BigDecimal latitude;
    private BigDecimal longitude;

    public static Location of(BigDecimal latitude, BigDecimal longitude) {
        return Location.builder()
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
}
