package com.ptit.sqa.controller;

import com.ptit.sqa.dto.response.CustomerInvoiceDTO;
import com.ptit.sqa.service.CustomerInvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/invoices")
@RequiredArgsConstructor
public class CustomerInvoiceController {

    private final CustomerInvoiceService customerInvoiceService;

    @GetMapping
    public ResponseEntity<?> listCustomerInvoicesThisMonth(){
        List<CustomerInvoiceDTO> invoices = customerInvoiceService.listInvoiceThisMonth();
        return new ResponseEntity<>(invoices,  HttpStatus.OK);
    }

}
