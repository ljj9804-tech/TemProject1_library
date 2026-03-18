package com.library.project.library.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {

    private Long id; // 내부 관리용 PK (id)

    @NotEmpty
    @Size(min = 4, max = 20)
    private String mid; // 로그인 아이디

    @NotEmpty
    private String mpw; // 비밀번호

    @NotEmpty
    private String mname; // 이름

    @Email
    @NotEmpty
    private String email; // 이메일

    private String region; // 지역

    private String role; // Role (USER, ADMIN)

    private LocalDateTime regDate;
    private LocalDateTime modDate;
}