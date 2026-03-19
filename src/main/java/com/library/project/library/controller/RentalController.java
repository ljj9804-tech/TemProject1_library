package com.library.project.library.controller;

import com.library.project.library.dto.rentalDto.RentalRequestDTO;
import com.library.project.library.dto.rentalDto.RentalResponseDTO;
import com.library.project.library.dto.rentalDto.ReturnRequestDTO;
import com.library.project.library.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalService rentalService;

    @GetMapping("/rental")
    public String rentalPage() {
        return "rental/rental";
    }

    /**
     * 📌 도서 대출
     */
    @PostMapping
    public ResponseEntity<String> rentBook(
            @RequestBody RentalRequestDTO dto) {

        rentalService.rentBook(dto);
        return ResponseEntity.ok("대출 완료");
    }


    /**
     * 📌 도서 반납
     */
    @PostMapping("/return")
    public ResponseEntity<String> returnBook(
            @RequestBody ReturnRequestDTO dto) {

        rentalService.returnBook(dto);
        return ResponseEntity.ok("반납 완료");
    }

    // 재대출
    @PostMapping("/renew/{id}")
    public ResponseEntity<String> renewBook(@PathVariable Long id){
        rentalService.renewBook(id);
        return ResponseEntity.ok("재대출 완료");
    }

    /**
     * 📌 사용자 대출 목록 조회
     */
    @GetMapping("/member/{MemberId}")
    public ResponseEntity<List<RentalResponseDTO>> getUserRentals(
            @PathVariable Long MemberId) {

        List<RentalResponseDTO> rentals =
                rentalService.getUserRentals(MemberId);

        return ResponseEntity.ok(rentals);
    }


    /**
     * 📌 인기 도서 (대출 많은 순)
     */
    @GetMapping("/stats")
    public ResponseEntity<List<Object[]>> getMostRentedBooks() {

        List<Object[]> result =
                rentalService.getMostRentedBooks();

        return ResponseEntity.ok(result);
    }

}
