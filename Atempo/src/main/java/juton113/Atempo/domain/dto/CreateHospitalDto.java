package juton113.Atempo.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import juton113.Atempo.domain.entity.Admission;
import lombok.*;

@Getter
@Setter
@Builder
public class CreateHospitalDto {
    private Admission admission;
    private String name;

    @JsonProperty("phone_number")
    private String phoneNumber;

    private String address;
    private double distance;

    @JsonProperty("travel_time")
    private Integer travelTime;

    private String detail;
}
