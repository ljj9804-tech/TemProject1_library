package com.library.project.library.controller;

import com.library.project.library.dto.MemberDTO;
import com.library.project.library.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
//@RestController
@RequestMapping("/member")
@Log4j2
@RequiredArgsConstructor
@Tag(name = "Member Controller", description = "회원 관련 화면 및 로그인/로그아웃 처리") // Swagger 노출용
public class MemberController {

    private final MemberService memberService;

    // 1. 회원가입 화면 (GET) - join.html 연결
//    @Tag(name = "회원가입 화면 (GET) 테스트",
//            description = "회원가입 화면")
    @GetMapping("/join")
    public void joinGet() {
        log.info("MemberController - joinGet() 진입 (join.html 호출)");
    }

    // 2. 회원가입 처리 (POST)
//    @Tag(name = "회원가입 처리 (POST) 테스트",
//            description = "회원가입 처리")
    @Operation(summary = "회원가입 처리 (POST) 테스트", description = "회원가입 처리 (POST) 테스트")
    @PostMapping("/join")
    public String joinPost(@Valid MemberDTO memberDTO,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {

        log.info("MemberController - joinPost() 처리 중: " + memberDTO);

        if(bindingResult.hasErrors()) {
            log.info("유효성 검사 에러 발생");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/member/join";
        }

        try {
            memberService.register(memberDTO);
        } catch (Exception e) {
            log.error("중복 아이디 에러: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", "mid");
            return "redirect:/member/join";
        }

        redirectAttributes.addFlashAttribute("result", "success");
        return "redirect:/member/login";
    }

    // 3. 로그인 화면 (GET)
//    @Tag(name = "로그인 화면 (GET) 테스트",
//            description = "로그인 화면")
    @GetMapping("/login")
    public void loginGet() {
        log.info("MemberController - loginGet() 진입");
    }

    // 4. 로그인 처리 (POST) - 세션 방식
//    @Tag(name = "로그인 처리 (POST)",
//            description = "로그인 처리")
    @Operation(summary = "로그인 처리 (POST) 테스트", description = "로그인 처리 (POST) 테스트")
    @PostMapping("/login")
    public String loginPost(String mid, String mpw, HttpSession session, RedirectAttributes redirectAttributes) {
        log.info("로그인 시도 아이디: " + mid);

        try {
            MemberDTO memberDTO = memberService.readOne(mid);

            // 1. 비밀번호가 일치하면 (성공)
            if(memberDTO.getMpw().equals(mpw)) {
                session.setAttribute("loginInfo", memberDTO); // 세션에 저장
                log.info("로그인 성공! 마이페이지로 이동합니다.");

                // [중요] 성공 시 리턴 경로는 마이페이지입니다!
                return "redirect:/member/mypage?mid=" + mid;
            }
            // 2. 비밀번호가 틀리면 (실패)
            else {
                redirectAttributes.addFlashAttribute("error", "password");
                return "redirect:/member/login"; // 다시 로그인 화면으로
            }
        }
        // 3. 아이디가 아예 없으면 (실패)
        catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "id");
            return "redirect:/member/login"; // 다시 로그인 화면으로
        }
    }

    // 로그아웃 화면 (GET)
    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        log.info("MemberController - logout() 실행. 로그아웃 되었습니다.");

        // 세션 무효화 (모든 로그인 정보 삭제)
        session.invalidate();

        // 로그아웃 성공 메시지 전달 (선택사항)
        redirectAttributes.addFlashAttribute("logout", "success");

        return "redirect:/member/login";
    }



    // 5. 내 서재 (마이페이지)
//    @Tag(name = "내 서재 (마이페이지) (GET)",
//            description = "내 서재 (마이페이지)")
    @Operation(summary = "내 서재 (마이페이지) (GET) 테스트", description = "내 서재 (마이페이지) (GET) 테스트")
    @GetMapping("/mypage")
    public void myPage(String mid, Model model) {
        MemberDTO memberDTO = memberService.readOne(mid);
        model.addAttribute("dto", memberDTO);
    }

    // 정보 수정 화면 (GET)
//    @Tag(name = "회원 정보 수정 화면 (GET)",
//            description = "회원 정보 수정 화면")
    @GetMapping("/modify")
    public void modifyGet(String mid, Model model) {
        log.info("MemberController - modifyGet() 호출: " + mid);
        MemberDTO memberDTO = memberService.readOne(mid);
        model.addAttribute("dto", memberDTO);
    }

    // 정보 수정 처리 (POST)
//    @Tag(name = "회원 정보 수정 처리 (POST)",
//            description = "회원 정보 수정 처리")
    @Operation(summary = "회원 정보 수정 처리 (POST) 테스트", description = "회원 정보 수정 처리 (POST) 테스트")
    @PostMapping("/modify")
    public String modifyPost(@Valid MemberDTO memberDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {

        log.info("MemberController - modifyPost() 진행: " + memberDTO);

        if(bindingResult.hasErrors()) {
            // 로그에 어떤 필드에서 에러가 났는지 찍어줍니다.
            log.info("수정 유효성 에러 상세내용: " + bindingResult.getAllErrors());

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addAttribute("mid", memberDTO.getMid());
            return "redirect:/member/modify";
        }

        try {
            memberService.modify(memberDTO);
        } catch (Exception e) {
            log.error("수정 실패: " + e.getMessage());
            return "redirect:/member/modify?mid=" + memberDTO.getMid();
        }

        redirectAttributes.addFlashAttribute("result", "modified");
        return "redirect:/member/mypage?mid=" + memberDTO.getMid();
    }

    // 아이디 중복 체크
    @GetMapping("/checkId")
    @ResponseBody
    public String checkId(String mid) {
        boolean exists = memberService.checkId(mid);
        // [체크!] exists가 true(있다)이면 "exist", false(없다)이면 "ok"가 맞나요?
        return exists ? "exist" : "ok";
    }
    // 이메일 중복 체크
    @GetMapping("/checkEmail")
    @ResponseBody
    public String checkEmail(String email) {
        return memberService.checkEmail(email) ? "exist" : "ok";
    }




}