package juton113.Avenir.repository;

import juton113.Avenir.domain.entity.HospitalCallStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HospitalCallStatusRepository extends JpaRepository<HospitalCallStatus, Long> {
    Optional<HospitalCallStatus> findByCallId(String callId);

}
