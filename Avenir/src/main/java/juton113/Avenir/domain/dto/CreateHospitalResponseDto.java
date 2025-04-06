package juton113.Avenir.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateHospitalResponseDto {
    private String name;

    @JsonProperty("phone_number")
    private String phoneNumber;

    private String address;
    private double distance;

    @JsonProperty("travel_time")
    private Integer travelTime;

    private String detail;
}
