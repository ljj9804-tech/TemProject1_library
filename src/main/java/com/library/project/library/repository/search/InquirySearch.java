package com.library.project.library.repository.search;


import com.library.project.library.domain.Inquiry;
import com.library.project.library.dto.InquiryListReplyCountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InquirySearch {
    Page<Inquiry> searchAll(String[] types, String keyword, Pageable pageable);

    Page<InquiryListReplyCountDTO> searchWithReplyCount(String[] types, String keyword, Pageable pageable);

    // 💡 이 줄을 추가하세요! (내 문의 내역 검색용 설계도)
    Page<InquiryListReplyCountDTO> searchMyList(Pageable pageable, String writer);

}