package juton113.Atempo.repository;

import juton113.Atempo.domain.entity.HospitalCallStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HospitalCallStatusRepository extends JpaRepository<HospitalCallStatus, Long> {
    Optional<HospitalCallStatus> findByCallId(String callId);

}
