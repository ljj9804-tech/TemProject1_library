package com.library.project.library.service;

import com.library.project.library.domain.Notice;
import com.library.project.library.dto.*;
import com.library.project.library.repository.NoticeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class NoticeServiceImpl implements NoticeService {

    private final ModelMapper modelMapper;
    private final NoticeRepository noticeRepository;
    // 📍 ReplyRepository 주입 제거 (공지사항에만 집중)

    @Override
    public Long register(NoticeDTO noticeDTO) {
        // 인터페이스의 default 메서드인 dtoToEntity 사용 (이미지 변환 포함)
        Notice notice = dtoToEntity(noticeDTO);
        Long nno = noticeRepository.save(notice).getNno();
        return nno;
    }

    @Override
    public NoticeDTO readOne(Long nno) {
        // 이미지까지 한 번에 가져오도록 Repository의 메서드 호출
        Optional<Notice> result = noticeRepository.findByIdWithImages(nno);
        Notice notice = result.orElseThrow();

        // 인터페이스의 default 메서드인 entityToDto 사용 (이미지 리스트 변환 포함)
        return entityToDto(notice);
    }

    @Override
    public void modify(NoticeDTO noticeDTO) {
        Optional<Notice> result = noticeRepository.findById(noticeDTO.getNno());
        Notice notice = result.orElseThrow();

        // 1) 제목, 내용 변경
        notice.change(noticeDTO.getTitle(), noticeDTO.getContent());

        // 2) 기존 이미지 삭제 (고아 객체 처리)
        notice.clearImages();

        // 3) 새로운 이미지 추가
        if(noticeDTO.getFileNames() != null) {
            for(String fileName : noticeDTO.getFileNames()) {
                // '_' 기준으로 UUID와 파일명을 2개로만 안전하게 분리
                String[] arr = fileName.split("_", 2);
                notice.addImage(arr[0], arr[1]);
            }
        }
        noticeRepository.save(notice);
    }

    @Override
    public void remove(Long nno) {
        // 📍 댓글 삭제 로직 제거! 공지사항만 깔끔하게 삭제합니다.
        // (영속성 전이 설정이 되어 있다면 이미지는 자동으로 삭제됩니다.)
        noticeRepository.deleteById(nno);
    }

    @Override
    public PageResponseDTO<NoticeDTO> list(PageRequestDTO pageRequestDTO) {
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("nno");

        // NoticeRepository가 NoticeSearch를 상속받고 있어야 에러가 안 납니다.
        Page<Notice> result = noticeRepository.searchAll(types, keyword, pageable);

        List<NoticeDTO> dtoList = result.getContent().stream()
                .map(notice -> modelMapper.map(notice, NoticeDTO.class))
                .collect(Collectors.toList());

        return PageResponseDTO.<NoticeDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }

    @Override
    public PageResponseDTO<NoticeListAllDTO> listWithAll(PageRequestDTO pageRequestDTO) {
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("nno");

        // Querydsl을 이용한 이미지+댓글수 포함 목록 조회
        Page<NoticeListAllDTO> result = noticeRepository.searchWithAll(types, keyword, pageable);

        return PageResponseDTO.<NoticeListAllDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(result.getContent())
                .total((int) result.getTotalElements())
                .build();
    }

}
