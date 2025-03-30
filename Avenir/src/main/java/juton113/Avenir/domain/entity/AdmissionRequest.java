package juton113.Avenir.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@Table(name = "admission_request")
public class AdmissionRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    Member member;

    @Column(nullable = false)
    BigDecimal latitude;

    @Column(nullable = false)
    BigDecimal longitude;

    @Column(nullable = false)
    String patientCondition;
}
