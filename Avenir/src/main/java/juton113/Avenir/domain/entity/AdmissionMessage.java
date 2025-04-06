package juton113.Avenir.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;

@Builder
@Entity
@Table(name = "admission_message")
public class AdmissionMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long admissionMessageId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admission_id")
    private Admission admission;

    @Column(columnDefinition = "TEXT")
    private String message;
}
