package com.ptit.sqa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerInvoiceDTO {
    private int id;
    private Integer newWaterIndexUsed;
    private int oldWaterIndexUsed;
    private LocalDateTime timeUsing;
    private CustomerDTO customer;
}
