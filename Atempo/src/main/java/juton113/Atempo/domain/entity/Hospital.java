package juton113.Atempo.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
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
    private String departments;

    @OneToOne(mappedBy = "hospital")
    private HospitalCallStatus hospitalCallStatus;
}
