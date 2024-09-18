package com.booleanuk.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class LoanDTO {

    @NotNull(message = "userId is required.")
    private Integer userId;

    @NotNull(message = "itemId is required")
    private Integer itemId;

    @NotBlank(message = "itemType is required")
    private String itemType;
}
