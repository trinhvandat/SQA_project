package com.ptit.sqa.service;

import com.ptit.sqa.model.Customer;

public interface EmailService {
    boolean noticePaymentBill(Customer customer);
}
