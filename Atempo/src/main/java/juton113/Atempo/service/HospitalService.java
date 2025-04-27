package juton113.Atempo.service;

import jakarta.transaction.Transactional;
import juton113.Atempo.domain.dto.CreateHospitalDto;
import juton113.Atempo.domain.entity.Hospital;
import juton113.Atempo.repository.HospitalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class HospitalService {
    private final HospitalRepository hospitalRepository;

    @Transactional
    public Hospital createHospital(CreateHospitalDto createHospitalDto) {
        Hospital hospital = Hospital.builder()
                .admission(createHospitalDto.getAdmission())
                .phoneNumber(createHospitalDto.getPhoneNumber())
                .address(createHospitalDto.getAddress())
                .distance(createHospitalDto.getDistance())
                .travelTime(createHospitalDto.getTravelTime())
                .departments(createHospitalDto.getDepartments())
                .build();

        return hospitalRepository.save(hospital);
    }
}
