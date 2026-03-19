package com.library.project.library.repository;

import com.library.project.library.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 로그인 아이디(mid)로 회원 한 명을 조회하는 메서드
    Optional<Member> findByMid(String mid);

    // 회원가입 시 아이디 중복 확인을 위한 메서드
    boolean existsByMid(String mid);

    // 이메일 존재 여부를 위해 추가
    boolean existsByEmail(String email);

}