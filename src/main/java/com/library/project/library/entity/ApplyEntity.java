package com.library.project.library.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "apply")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ApplyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ano;

    @Column(length = 50, nullable = false)
    private String applicantName;

    @Column(length = 20, nullable = false)
    private String phone;

    @Column(length = 200, nullable = false)
    private String eventName;

    @Column(length = 50, nullable = false)
    private String facilityType;

    @Column(nullable = false)
    private Integer participants;

    @Column(nullable = false)
    private LocalDate applyDate;

    @Column(length = 20, nullable = false)
    private String applyTime;

    // 수정된 부분: 기본값을 설정하고 NULL이 들어오지 못하게 막습니다.
    @Builder.Default
    @Column(columnDefinition = "TEXT", nullable = false)
    private String eventContent = "상세 내용 없음";

    @Column(updatable = false)
    private LocalDateTime regDate;

    @PrePersist
    public void prePersist() {
        this.regDate = LocalDateTime.now();
        // 혹시라도 서비스 단에서 null을 넘겼을 경우를 대비한 안전장치
        if (this.eventContent == null || this.eventContent.trim().isEmpty()) {
            this.eventContent = "상세 내용 없음";
        }
    }
}
