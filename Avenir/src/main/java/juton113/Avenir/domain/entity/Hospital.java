package juton113.Avenir.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "hospital")
public class Hospital {
    @Id
    private Long hospitalId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admission_id")
    private Admission admission;

    private String name;
    private String phoneNumber;
    private String address;
    private Float distance;
    private Integer travelTime;
    private String detail;

    @OneToOne(mappedBy = "hospital_call_status")
    private HospitalCallStatus hospitalCallStatus;
}
