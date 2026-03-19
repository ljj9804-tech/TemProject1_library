package com.library.project.library.repository;

import com.library.project.library.entity.Event;
import org.springframework.data.domain.Page; // 추가
import org.springframework.data.domain.Pageable; // 추가
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    // Pageable이 꼭 있어야 페이징 처리가 돼!
    Page<Event> findByCategory(String category, Pageable pageable);

    // 아까 만든 검색용 메서드도 잘 있는지 확인!
    Page<Event> findByTitleContainingAndCategory(String title, String category, Pageable pageable);

    // Pageable을 넣어야 페이징 처리가 유지되면서 'M'만 쏙 빠져!
    Page<Event> findByCategoryNot(String category, Pageable pageable);

    // 카테고리가 'M'이고 제목에 키워드가 포함된 데이터를 페이징 처리해서 가져오기
    Page<Event> findByCategoryAndTitleContaining(String category, String title, Pageable pageable);
}
