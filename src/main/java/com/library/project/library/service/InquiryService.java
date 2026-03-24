package com.library.project.library.service;


import com.library.project.library.dto.InquiryDTO;
import com.library.project.library.dto.InquiryListReplyCountDTO;
import com.library.project.library.dto.PageRequestDTO;
import com.library.project.library.dto.PageResponseDTO;

public interface InquiryService {
    Long register(InquiryDTO inquiryDTO);
    InquiryDTO readOne(Long ino);
    void modify(InquiryDTO inquiryDTO);
    void remove(Long ino);

    // 일반 목록 조회
    PageResponseDTO<InquiryDTO> list(PageRequestDTO pageRequestDTO);

    // 문의사항 목록 + 답변(댓글) 갯수 포함
    PageResponseDTO<InquiryListReplyCountDTO> listWithReplyCount(PageRequestDTO pageRequestDTO);

    PageResponseDTO<InquiryListReplyCountDTO> listMyInquiry(PageRequestDTO pageRequestDTO, String writer);

}