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

    @Enumerated(EnumType.STRING)
    CallStatus callStatus;

    @Enumerated(EnumType.STRING)
    CallResponseStatus callResponseStatus;

    int callAttempts;
}
