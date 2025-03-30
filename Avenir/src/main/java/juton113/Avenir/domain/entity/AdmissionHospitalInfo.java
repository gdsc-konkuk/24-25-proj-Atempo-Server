package juton113.Avenir.domain.entity;

import jakarta.persistence.*;
import juton113.Avenir.domain.enums.CallResponseStatus;
import juton113.Avenir.domain.enums.CallStatus;
import lombok.AllArgsConstructor;

@Entity
@AllArgsConstructor
@Table(name = "admission_hospital_response")
public class AdmissionHospitalInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admission_request_id", nullable = false)
    Admission admission;

    @Column(nullable = false)
    String hospitalName;

    @Column(nullable = false)
    String hospitalPhoneNumber;

    @Column(nullable = false)
    int distance; // TODO: 단위 정하기

    @Column(nullable = false)
    int travelTime; // TODO: 단위 정하기

    @Column(nullable = false)
    String detail; // TODO: 어떤 내용을 넣을 지 정하기

    @Enumerated(EnumType.STRING)
    CallStatus callStatus;

    @Enumerated(EnumType.STRING)
    CallResponseStatus callResponseStatus;

    @Column(nullable = false)
    int callAttempts;
}
