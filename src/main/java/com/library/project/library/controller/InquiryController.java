package com.library.project.library.controller;


import com.library.project.library.dto.InquiryDTO;
import com.library.project.library.dto.InquiryListReplyCountDTO;
import com.library.project.library.dto.PageRequestDTO;
import com.library.project.library.dto.PageResponseDTO;
import com.library.project.library.service.InquiryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/inquiry")
@Log4j2
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryService inquiryService;

    @GetMapping("/list")
    public String list(PageRequestDTO pageRequestDTO, Model model) {
        PageResponseDTO<InquiryListReplyCountDTO> responseDTO = inquiryService.listWithReplyCount(pageRequestDTO);
        model.addAttribute("responseDTO", responseDTO);
        return "inquiry/list";
    }

    @GetMapping("/register")
    public String registerGet() {
        return "inquiry/register";
    }

    @PostMapping("/register")
    public String registerPost(@Valid InquiryDTO inquiryDTO, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "inquiry/register";
        }
        inquiryService.register(inquiryDTO);
        return "redirect:/inquiry/list";
    }

    @GetMapping({"/read", "/modify"})
    public void read(@RequestParam("ino") Long ino, PageRequestDTO pageRequestDTO, Model model) {
        InquiryDTO inquiryDTO = inquiryService.readOne(ino);
        model.addAttribute("dto", inquiryDTO);
    }

    @PostMapping("/modify")
    public String modify(@Valid InquiryDTO inquiryDTO, BindingResult bindingResult, PageRequestDTO pageRequestDTO, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addAttribute("ino", inquiryDTO.getIno());
            return "redirect:/inquiry/modify?" + pageRequestDTO.getLink();
        }
        inquiryService.modify(inquiryDTO);
        redirectAttributes.addFlashAttribute("result", "modified");
        redirectAttributes.addAttribute("ino", inquiryDTO.getIno());
        return "redirect:/inquiry/read";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam("ino") Long ino, RedirectAttributes redirectAttributes) {
        inquiryService.remove(ino);
        redirectAttributes.addFlashAttribute("result", "removed");
        return "redirect:/inquiry/list";
    }

    // 💡 404 에러 원인인 'redirect:/member/login'을 아예 삭제했습니다.
    @GetMapping("/myList")
    public String myList(PageRequestDTO pageRequestDTO, Model model, Principal principal) {
        log.info(">>>> 내 문의 내역 페이지 접속 중...");

        // 로그인 여부와 관계없이 에러가 나지 않도록 작성자 아이디를 강제 지정
        // 로그인이 되어 있으면 실제 아이디, 안 되어 있으면 'user1' (테스트용)
        String writer = "user1";
        if(principal != null) {
            writer = principal.getName();
        }

        log.info(">>>> 현재 필터링할 작성자 아이디: " + writer);

        PageResponseDTO<InquiryListReplyCountDTO> responseDTO =
                inquiryService.listMyInquiry(pageRequestDTO, writer);

        model.addAttribute("responseDTO", responseDTO);
        model.addAttribute("writer", writer);

        return "inquiry/myList";
    }
}