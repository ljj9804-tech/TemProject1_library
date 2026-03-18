package com.library.project.library.service;

import com.library.project.library.dto.MemberDTO;

public interface MemberService {
    // 회원가입 (반환값은 생성된 id)
    Long register(MemberDTO memberDTO);

    // 회원 상세 조회 (아이디로 조회)
    MemberDTO readOne(String mid);

    // 회원 정보 수정
    void modify(MemberDTO memberDTO);

    // 회원 탈퇴
    void remove(String mid);

    // 아이디 중복 체크
    boolean checkId(String mid);

    // 이메일 중복 체크
    boolean checkEmail(String email);
}