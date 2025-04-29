package juton113.Atempo.domain.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HospitalResponseDto {
    private String name;
    private String phoneNumber;
    private String address;
    private double distance;
    private Integer travelTime;
    private String departments;
}

