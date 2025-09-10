package com.fintech.cms.card_management_system.dto;

import com.fintech.cms.card_management_system.model.Status;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateAccountRequest {

    @NotNull(message = "Status is required")
    private Status status;

}
