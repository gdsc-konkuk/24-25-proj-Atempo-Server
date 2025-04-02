package juton113.Avenir.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Builder
@Table(name = "admission")
public class Admission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private MessageHistory messageHistory;
}
