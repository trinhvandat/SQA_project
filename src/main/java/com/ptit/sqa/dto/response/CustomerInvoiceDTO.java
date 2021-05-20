package com.ptit.sqa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
public class CustomerInvoiceDTO {
    private int id;
    private Integer newWaterIndexUsed;
    private int oldWaterIndexUsed;
    private LocalDateTime timeUsing;
    private CustomerDTO customer;

    public CustomerInvoiceDTO(int id, Integer newWaterIndexUsed, int oldWaterIndexUsed, LocalDateTime timeUsing, CustomerDTO customer) {
        this.id = id;
        this.newWaterIndexUsed = newWaterIndexUsed;
        this.oldWaterIndexUsed = oldWaterIndexUsed;
        this.timeUsing = timeUsing;
        this.customer = customer;
    }
}
