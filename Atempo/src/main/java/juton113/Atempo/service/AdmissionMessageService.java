package juton113.Atempo.service;

import jakarta.transaction.Transactional;
import juton113.Atempo.domain.dto.CreateAdmissionMessageDto;
import juton113.Atempo.domain.entity.AdmissionMessage;
import juton113.Atempo.repository.AdmissionMessageRepository;
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
