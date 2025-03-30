package juton113.Avenir.domain.entity;

import jakarta.persistence.*;
import juton113.Avenir.domain.enums.CallResponseStatus;
import juton113.Avenir.domain.enums.CallStatus;
import lombok.AllArgsConstructor;

@Entity
@AllArgsConstructor
@Table(name = "admission_hospital_response")
public class AdmissionHospitalResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admission_request_id", nullable = false)
    AdmissionRequest admissionRequest;

    @Column(nullable = false)
    String hospitalName;

    @Column(nullable = false)
    String hospitalPhoneNumber;

    @Enumerated(EnumType.STRING)
    CallStatus callStatus;

    @Enumerated(EnumType.STRING)
    CallResponseStatus callResponseStatus;

    @Column(nullable = false)
    int callAttempts;
}
