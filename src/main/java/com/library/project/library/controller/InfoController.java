package com.library.project.library.controller;


import com.library.project.library.dto.LibraryStatsDTO;
import com.library.project.library.service.InfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/info")
@Log4j2
@RequiredArgsConstructor
public class InfoController {

    private final InfoService infoService;

    // 1. [GET] 도서관 안내 메인 홈 (접속 주소: /info)
    @GetMapping("")
    public String mainHome() {
        // 주소창이 /info 이더라도, 진짜 메인인 / 로 강제 이동시킵니다.
        return "redirect:/";
    }

    // 2. [GET] 자료 현황 목록 페이지 (접속 주소: /info/basic)
    @GetMapping("/basic")
    public String basic(Model model) {
        log.info("자료 현황 페이지 접속...");
        model.addAttribute("stats", infoService.getLibraryStatistics());
        model.addAttribute("info", infoService.getStaticLibraryInfo());
        return "info/basic";
    }

    // 3. [GET] 자료 등록 페이지 이동
    @GetMapping("/register")
    public void registerGET() {
        log.info("자료 등록 페이지 이동...");
    }

    // 4. [POST] 자료 등록 처리
    @PostMapping("/register")
    public String registerPOST(LibraryStatsDTO dto) {
        log.info("자료 등록 실행: " + dto);
        infoService.registerStat(dto);
        // 등록 후에는 다시 목록으로 보냅니다.
        return "redirect:/info/basic";
    }

    // 5. [GET] 자료 수정 페이지 이동 (ID값 필수)
    @GetMapping("/modify")
    public String modifyGET(@RequestParam("id") Long id, Model model) {
        log.info("수정 페이지 이동 ID: " + id);
        model.addAttribute("stat", infoService.getStat(id));
        model.addAttribute("info", infoService.getStaticLibraryInfo());
        return "info/modify";
    }

    // 6. [POST] 자료 수정 처리
    @PostMapping("/modify")
    public String modifyPOST(LibraryStatsDTO dto) {
        log.info("자료 수정 실행: " + dto);
        infoService.modifyStat(dto);
        // 수정 완료 후 목록으로 리다이렉트
        return "redirect:/info/basic";
    }

    // 7. [POST] 자료 삭제 처리
    @PostMapping("/remove")
    public String removePOST(@RequestParam("statId") Long statId) {
        log.info("자료 삭제 실행 ID: " + statId);
        infoService.removeStat(statId);
        // 삭제 후 목록으로 리다이렉트
        return "redirect:/info/basic";
    }
}

/*
 * ========== InfoController 설명 ==========
 * - 역할: 도서관 정보(소개) 관련 화면 + 자료 현황 CRUD를 처리하는 컨트롤러
 * - URL 패턴: /info/**
 *
 * [메서드]
 * - getLibraryInfo(): GET /info/basic → 자료현황 페이지 (도서관 정보 + 통계 데이터)
 * - organizationPage(): GET /info/organization → 조직 및 업무 페이지
 * - facilitiesPage(): GET /info/facilities → 시설 현황 페이지
 * - mapPage(): GET /info/map → 찾아오시는 길 페이지
 * - donationPage(): GET /info/donation → 기증/납본 안내 페이지
 * - registerGET(): GET /info/register → 자료 등록 페이지
 * - registerPOST(): POST /info/register → 자료 현황 항목 등록 처리
 * - modifyPOST(): POST /info/modify → 자료 현황 항목 수정 처리
 * - remove(): POST /info/remove/{id} → 자료 현황 항목 삭제 처리
 */