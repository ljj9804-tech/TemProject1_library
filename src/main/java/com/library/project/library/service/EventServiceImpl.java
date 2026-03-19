package com.library.project.library.service;

import com.library.project.library.dto.EventDTO;
import com.library.project.library.entity.Event;
import com.library.project.library.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class EventServiceImpl implements com.library.project.library.service.EventService {

    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;

    @Override
    public Long register(EventDTO eventDTO) {
        Event event = modelMapper.map(eventDTO, Event.class);
        return eventRepository.save(event).getId();
    }

    @Override
    public EventDTO readOne(Long id) {
        Event event = eventRepository.findById(id).orElseThrow();
        return modelMapper.map(event, EventDTO.class);
    }

    @Override
    public Page<EventDTO> getList(Pageable pageable) {
        // 다시 'M' 포함해서 다 가져오게 원상복구!
        // 그래야 캘린더가 데이터를 제대로 받아서 그리기 시작해.
        Page<Event> result = eventRepository.findAll(pageable);
        return result.map(event -> modelMapper.map(event, EventDTO.class));
    }

    @Override
    public Page<EventDTO> getLecturesByCategory(String category, Pageable pageable) {
        // [확인] 주말 극장 페이지에서 "M"을 던지면 "M"만 가져오는 로직!
        // 레포지토리에 findByCategory가 있어야 해.
        Page<Event> result = eventRepository.findByCategory(category, pageable);
        return result.map(event -> modelMapper.map(event, EventDTO.class));
    }

    @Override
    public Page<EventDTO> getLecturesWithSearch(String keyword, String category, Pageable pageable) {
        Page<Event> result;
        // 검색어가 있든 없든 '카테고리'는 무조건 유지하게!
        if (keyword == null || keyword.isEmpty()) {
            result = eventRepository.findByCategory(category, pageable);
        } else {
            result = eventRepository.findByTitleContainingAndCategory(keyword, category, pageable);
        }
        return result.map(event -> modelMapper.map(event, EventDTO.class));
    }

    // 캘린더 전용: 페이징 없이 몽땅 가져오기!
    @Override
    public List<EventDTO> getAllEvents() {
        List<Event> result = eventRepository.findAll();
        return result.stream()
                .map(event -> modelMapper.map(event, EventDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<EventDTO> getCinemaWithSearch(String keyword, Pageable pageable) {
        String category = "M"; // 주말 극장 카테고리 고정
        Page<Event> result;

        if (keyword == null || keyword.isEmpty()) {
            result = eventRepository.findByCategory(category, pageable);
        } else {
            result = eventRepository.findByCategoryAndTitleContaining(category, keyword, pageable);
        }

        return result.map(event -> modelMapper.map(event, EventDTO.class));
    }
}