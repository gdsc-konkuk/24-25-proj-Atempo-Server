package juton113.Avenir.service;

import jakarta.transaction.Transactional;
import juton113.Avenir.domain.dto.CreateAdmissionMessageDto;
import juton113.Avenir.domain.entity.AdmissionMessage;
import juton113.Avenir.repository.AdmissionMessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdmissionMessageService {
    private final AdmissionMessageRepository admissionMessageRepository;

    @Transactional
    public void createAdmissionMessage(CreateAdmissionMessageDto createAdmissionMessageDto) {

        AdmissionMessage admissionMessage = AdmissionMessage.builder()
                .admission(createAdmissionMessageDto.getAdmission())
                .message(createAdmissionMessageDto.getMessage())
                .build();

        admissionMessageRepository.save(admissionMessage);
    }

}
