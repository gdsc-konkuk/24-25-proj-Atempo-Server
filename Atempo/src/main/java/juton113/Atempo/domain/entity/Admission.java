package juton113.Atempo.domain.entity;

import jakarta.persistence.*;
import juton113.Atempo.auditing.BaseTimeEntity;
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
    Long admissionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    Member member;

    BigDecimal latitude;
    BigDecimal longitude;
    String patientCondition;

    @OneToMany(mappedBy = "admission")
    private List<Hospital> hospitalList;

    @OneToOne(mappedBy = "admission")
    private AdmissionMessage admissionMessage;
}
