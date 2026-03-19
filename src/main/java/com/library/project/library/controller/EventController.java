package com.library.project.library.controller;

import com.library.project.library.dto.EventDTO;
import com.library.project.library.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/event")
@Log4j2
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    // 1. 이달의 행사 (캘린더) - 전체 데이터 사용
    @GetMapping("/list")
    public void list(Model model) {
        log.info("캘린더용 전체 데이터 조회...");
        List<EventDTO> dtoList = eventService.getAllEvents(); // 여기서 전체를 가져옴!
        model.addAttribute("dtoList", dtoList);
    }

    // 행사 및 강좌 상세 안내
    @GetMapping("/read")
    public void read(Long id, Model model) {
        log.info("행사 상세 정보 조회 : " + id);
        EventDTO eventDTO = eventService.readOne(id);
        model.addAttribute("dto", eventDTO);
    }

    // 2. 강좌 리스트 - 페이징 유지
    @GetMapping("/lecture")
    public String lectureList(Model model, @PageableDefault(size = 10) Pageable pageable) {
        Page<EventDTO> lecturePage = eventService.getLecturesWithSearch(null, "G", pageable);
        model.addAttribute("lectureList", lecturePage.getContent());
        model.addAttribute("page", lecturePage); // 페이징 객체 그대로 전달
        return "event/lecture";
    }

    // 3. 주말 극장 - 검색과 페이징 통합
    @GetMapping("/cinema") // 경로 중복 방지를 위해 하나만 남기기!
    public String cinemaList(
            @RequestParam(value = "keyword", required = false) String keyword,
            Model model,
            @PageableDefault(size = 8, sort = "eventDate", direction = Sort.Direction.DESC) Pageable pageable) {

        log.info("주말 극장 목록 조회 중... 검색어: " + keyword);

        // [핵심] 영화 검색 기능이 포함된 서비스 호출!
        Page<EventDTO> moviePage = eventService.getCinemaWithSearch(keyword, pageable);

        model.addAttribute("movieList", moviePage.getContent());
        model.addAttribute("page", moviePage);
        model.addAttribute("keyword", keyword); // 검색창에 검색어 유지용

        return "event/cinema"; // 리턴 경로 명시해서 디자인 깨짐 방지!
    }
}