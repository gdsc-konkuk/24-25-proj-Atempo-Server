package juton113.Atempo.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HospitalInfoResponse {
    private String name;

    @JsonProperty("phone_number")
    private String phoneNumber;

    private String address;

    private double distance;

    @JsonProperty("travel_time")
    private Integer travelTime;

    private String departments;
}

