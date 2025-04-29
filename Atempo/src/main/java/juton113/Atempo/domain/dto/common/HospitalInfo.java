package juton113.Atempo.domain.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HospitalInfo {
    private String name;

    @JsonProperty("phone_number")
    private String phoneNumber;

    private String address;
    private double distance;

    @JsonProperty("travel_time")
    private Integer travelTime;

    private List<String> departments;
}
