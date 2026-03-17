package com.library.project.library.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WishBookDTO {
    private String applicantName; // 신청자 이름 (이미 명확해서 유지하거나 wishApplicantName으로 바꿔도 좋습니다)
    private String wishPhone;     // 신청자 연락처
    private String wishBookTitle; // 도서명
    private String wishAuthor;    // 저자명
    private String wishPublisher; // 출판사명
    private MultipartFile wishBookImage; // 첨부파일
}