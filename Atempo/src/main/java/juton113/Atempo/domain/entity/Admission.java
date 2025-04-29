package juton113.Atempo.domain.entity;

import jakarta.persistence.*;
import juton113.Atempo.auditing.BaseTimeEntity;
import juton113.Atempo.domain.dto.common.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "admission")
public class Admission extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long admissionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Getter
    @Embedded
    private Location location;

    @Getter
    private int searchRadius;

    @Getter
    private String patientCondition;

    private Long originalAdmissionId;

    @OneToMany(mappedBy = "admission")
    private List<Hospital> hospitalList;

    @OneToOne(mappedBy = "admission")
    private AdmissionMessage admissionMessage;

    public static Admission of(Member member,
                               Location location,
                               int searchRadius,
                               String patientCondition,
                               Long originalAdmissionId) {
        return Admission.builder()
                .member(member)
                .location(location)
                .searchRadius(searchRadius)
                .patientCondition(patientCondition)
                .originalAdmissionId(originalAdmissionId)
                .build();
    }
}
