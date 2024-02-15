package com.booleanuk.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class BorrowedItemDto {
    private int id;
    private String item;
    private String user;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate borrowDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate returnDate;

    public BorrowedItemDto(int id, String item, String user, LocalDate borrowDate, LocalDate returnDate) {
        this.id = id;
        this.item = item;
        this.user = user;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }
}
