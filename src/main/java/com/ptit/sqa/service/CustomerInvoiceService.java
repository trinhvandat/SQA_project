package com.ptit.sqa.service;

import com.ptit.sqa.dto.response.CustomerInvoiceDTO;

import java.util.List;

public interface CustomerInvoiceService {
    List<CustomerInvoiceDTO> listInvoiceThisMonth();
    int addNewWaterIndex(int customerId, Integer newWaterIndex);
}
