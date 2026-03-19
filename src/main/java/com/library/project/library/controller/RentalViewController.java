package com.library.project.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RentalViewController {

    // 관리자용 대출 관리 화면
    // 주소: http://localhost:8080/rentals
    @GetMapping("/rentals")
    public String adminRentals() {
        return "rental/rentals";  // templates/rental/rentals.html
    }

    // 회원용 내 대출 현황 화면
    // 주소: http://localhost:8080/user_rentals
    @GetMapping("/user_rentals")
    public String userRentals() {
        return "rental/user_rentals";  // templates/rental/user_rentals.html
    }
}
