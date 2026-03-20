package com.library.project.library.service;

import com.library.project.library.dto.MemberDTO;
import com.library.project.library.entity.Member;

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

    // 20260320 아이디/비밀번호 찾기 추가
    String findId(String mname, String email);           // 1. 아이디 찾기
    boolean checkMemberForPw(String mid, String email);    // 2. 비번 찾기 전 확인
    void updatePassword(String mid, String newPw);         // 3. 비번 변경 (암호화 없이)

}