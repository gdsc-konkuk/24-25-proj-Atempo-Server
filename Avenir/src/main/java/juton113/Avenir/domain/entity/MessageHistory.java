package juton113.Avenir.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "message_history")
public class MessageHistory {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admission_id")
    private Admission admission;

    @Column(columnDefinition = "TEXT")
    private String arsMessage;
}
