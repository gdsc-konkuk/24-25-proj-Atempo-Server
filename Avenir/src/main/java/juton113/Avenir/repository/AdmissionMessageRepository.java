package juton113.Avenir.repository;

import juton113.Avenir.domain.entity.AdmissionMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdmissionMessageRepository extends JpaRepository<AdmissionMessage, Long>{
}
