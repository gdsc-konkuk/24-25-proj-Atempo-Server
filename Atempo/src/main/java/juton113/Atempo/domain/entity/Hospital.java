package juton113.Atempo.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "hospital")
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hospitalId;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admission_id")
    private Admission admission;

    @Getter
    private String name;
    @Getter
    private String phoneNumber;
    @Getter
    private String address;
    @Getter
    private double distance;
    @Getter
    private Integer travelTime;
    @Getter
    private String departments;

    @OneToOne(mappedBy = "hospital")
    private HospitalCallStatus hospitalCallStatus;
}
