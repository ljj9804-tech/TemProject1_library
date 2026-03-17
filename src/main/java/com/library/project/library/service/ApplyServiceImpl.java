package com.library.project.library.service;


import com.library.project.library.dto.ApplyDTO;
import com.library.project.library.entity.ApplyEntity;
import com.library.project.library.repository.ApplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class ApplyServiceImpl implements ApplyService {

    private final ApplyRepository applyRepository;

    @Override
    public Long register(ApplyDTO applyDTO) {
        // DTO -> Entity 변환
        ApplyEntity applyEntity = ApplyEntity.builder()
                .applicantName(applyDTO.getApplicantName())
                .phone(applyDTO.getPhone())
                .eventName(applyDTO.getEventName())
                .facilityType(applyDTO.getFacilityType())
                .participants(applyDTO.getParticipants())
                .applyDate(applyDTO.getApplyDate())
                .applyTime(applyDTO.getApplyTime())
                .eventContent((applyDTO.getEventContent() == null || applyDTO.getEventContent().trim().isEmpty())
                        ? "상세 내용 없음" : applyDTO.getEventContent())
                .build();

        // DB 저장
        ApplyEntity result = applyRepository.save(applyEntity);

        return result.getAno(); // 저장된 번호 반환
    }
}