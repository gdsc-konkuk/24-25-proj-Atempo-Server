package juton113.Avenir.domain.entity;

import jakarta.persistence.*;
import juton113.Avenir.domain.enums.CallResponseStatus;
import lombok.AllArgsConstructor;

@Entity
@AllArgsConstructor
@Table(name = "call_status")
public class CallStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admission_id", nullable = false)
    Admission admission;

    String hospitalName;

    String hospitalPhoneNumber;

    float distance;

    int travelTime;

    String detail; // TODO: 어떤 내용을 넣을 지 정하기

    @Enumerated(EnumType.STRING)
    CallStatus callStatus;

    @Enumerated(EnumType.STRING)
    CallResponseStatus callResponseStatus;

    int callAttempts;

    String ars_message;
}
