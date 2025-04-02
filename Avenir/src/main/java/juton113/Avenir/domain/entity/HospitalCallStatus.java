package juton113.Avenir.domain.entity;

import jakarta.persistence.*;
import juton113.Avenir.domain.enums.CallResponseStatus;
import juton113.Avenir.domain.enums.CallStatus;
import lombok.AllArgsConstructor;

@Entity
@AllArgsConstructor
@Table(name = "hospital_call_status")
public class HospitalCallStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long hospitalCallStatusId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @Enumerated(EnumType.STRING)
    CallStatus callStatus;

    @Enumerated(EnumType.STRING)
    CallResponseStatus callResponseStatus;

    int callAttempts;
}
