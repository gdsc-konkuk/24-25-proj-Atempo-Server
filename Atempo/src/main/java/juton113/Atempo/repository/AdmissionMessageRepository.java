package juton113.Atempo.repository;

import juton113.Atempo.domain.entity.AdmissionMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdmissionMessageRepository extends JpaRepository<AdmissionMessage, Long>{
}
