package juton113.Avenir.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;

@Builder
@Entity
@Table(name = "hospital")
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hospitalId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admission_id")
    private Admission admission;

    private String name;
    private String phoneNumber;
    private String address;
    private double distance;
    private Integer travelTime;
    private String detail;

    @OneToOne(mappedBy = "hospital")
    private HospitalCallStatus hospitalCallStatus;
}
