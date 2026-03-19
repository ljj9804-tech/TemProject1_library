package com.library.project.library.dto.rentalDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RentalRequestDTO {

    private Long memberId;
    private Long bookId;

}

