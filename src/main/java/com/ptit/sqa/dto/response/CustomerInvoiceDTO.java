package com.ptit.sqa.dto.response;

import com.ptit.sqa.model.Customer;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CustomerInvoiceDTO {
    private int id;
    private Integer newWaterIndexUsed;
    private int oldWaterIndexUsed;
    private LocalDateTime timeUsing;
    private Customer customer;
}
