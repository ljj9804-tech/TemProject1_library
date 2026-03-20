package com.library.project.library.controller;


import com.library.project.library.dto.LibraryStatsDTO;
import com.library.project.library.service.InfoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/info")
@Log4j2
@RequiredArgsConstructor
public class InfoController {

    private final InfoService infoService;

    // 1. [GET] 홈 / 자료현황
    @GetMapping("/basic")
    public String getLibraryInfo(Model model) {
        log.info("자료현황 페이지 이동...");
        model.addAttribute("info", infoService.getStaticLibraryInfo());
        model.addAttribute("stats", infoService.getLibraryStatistics());
        return "info/basic"; // templates/info/basic.html 호출
    }

    // 2. [GET] 조직 및 업무
    @GetMapping("/organization")
    public String organizationPage(Model model) {
        model.addAttribute("info", infoService.getStaticLibraryInfo());
        return "info/organization"; // templates/info/organization.html 호출
    }

    // 3. [GET] 시설 현황
    @GetMapping("/facilities")
    public String facilitiesPage(Model model) {
        model.addAttribute("info", infoService.getStaticLibraryInfo());
        return "info/facilities"; // templates/info/facilities.html 호출
    }

    // 4. [GET] 찾아오시는 길
    @GetMapping("/map")
    public String mapPage(Model model) {
        model.addAttribute("info", infoService.getStaticLibraryInfo());
        return "info/map"; // templates/info/map.html 호출
    }

    // 5. [GET] 기증·납본 안내
    @GetMapping("/donation")
    public String donationPage(Model model) {
        model.addAttribute("info", infoService.getStaticLibraryInfo());
        return "info/donation"; // templates/info/donation.html 호출
    }

    // 6. [GET] 등록 페이지 이동
    @GetMapping("/register")
    public String registerGET() {
        return "info/register"; // templates/info/register.html 호출
    }

    // 7. [POST] 자료 등록 실행
    @PostMapping("/register")
    public String registerPOST(LibraryStatsDTO dto) {
        infoService.registerStat(dto);
        return "redirect:/info/basic";
    }

    // 8. [POST] 자료 수정 실행 (basic.html에서 바로 호출)
    @PostMapping("/modify")
    public String modifyPOST(LibraryStatsDTO dto) {
        infoService.modifyStat(dto);
        return "redirect:/info/basic";
    }

    // 9. [POST] 자료 삭제 실행
    @PostMapping("/remove/{id}")
    public String remove(@PathVariable("id") Long id) {
        log.info("삭제 ID: " + id);
        infoService.removeStat(id);
        return "redirect:/info/basic";
    }
}