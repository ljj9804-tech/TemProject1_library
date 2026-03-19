package com.library.project.library.service;

import com.library.project.library.dto.EventDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EventService {
    Long register(EventDTO eventDTO); // 등록
    EventDTO readOne(Long id);       // 상세 조회

    // 1. 전체 목록 페이징 (컨트롤러의 list 메서드용)
    Page<EventDTO> getList(Pageable pageable);

    // 2. 카테고리별 목록 페이징 (컨트롤러의 lectureList 메서드용)
    Page<EventDTO> getLecturesByCategory(String category, Pageable pageable);

    // keyword 파라미터 추가
    Page<EventDTO> getLecturesWithSearch(String keyword, String category, Pageable pageable);

    List<EventDTO> getAllEvents();

    Page<EventDTO> getCinemaWithSearch(String keyword, Pageable pageable);
}