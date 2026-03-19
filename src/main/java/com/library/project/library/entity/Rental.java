package com.library.project.library.entity;

import com.library.project.library.enums.RentalStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "rental")
public class Rental extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    private int renewCount;

    private LocalDate rentalDate;

    private LocalDate dueDate;

    private LocalDate returnDate;

    @Enumerated(EnumType.STRING)
    private RentalStatus status;

    public void returnBook() {
        this.returnDate = LocalDate.now();
        this.status = RentalStatus.RETURNED;
    }

    // 재대출
    public void increaseRenewCount(){
        this.renewCount++;
    }
}
