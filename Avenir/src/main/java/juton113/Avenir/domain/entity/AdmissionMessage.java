package juton113.Avenir.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "admission_message")
public class AdmissionMessage {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admission_id")
    private Admission admission;

    @Column(columnDefinition = "TEXT")
    private String message;
}
