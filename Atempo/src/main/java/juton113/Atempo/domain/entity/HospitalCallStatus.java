package juton113.Atempo.domain.entity;

import jakarta.persistence.*;
import juton113.Atempo.auditing.BaseTimeEntity;
import juton113.Atempo.domain.enums.CallResponseStatus;
import juton113.Atempo.domain.enums.CallStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "hospital_call_status")
public class HospitalCallStatus extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long hospitalCallStatusId;

    String callId;

    @Getter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @Enumerated(EnumType.STRING)
    CallStatus callStatus;

    @Enumerated(EnumType.STRING)
    CallResponseStatus callResponseStatus;

    int callAttempts;

    public void update(
            CallStatus callStatus,
            CallResponseStatus responseStatus) {
        this.callStatus = callStatus;
        this.callResponseStatus = responseStatus;
    }
}
