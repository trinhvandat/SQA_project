package com.ptit.sqa.service;

import com.ptit.sqa.dto.response.CustomerInvoiceDTO;

public interface EmailService {
    boolean noticePaymentBill(CustomerInvoiceDTO invoice);
}
