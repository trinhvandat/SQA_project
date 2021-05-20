package com.ptit.sqa.controller;

import com.ptit.sqa.service.CustomerInvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerInvoiceService customerInvoiceService;

    @PostMapping("/{customerId}/invoices")
    public ResponseEntity<?> addNewWaterIndex(@PathVariable("customerId") int customerId,
                                              @RequestParam("newWaterIndex") Integer newWaterIndex){
        int addedResult =  customerInvoiceService.addNewWaterIndex(customerId, newWaterIndex);
        return new ResponseEntity<>(HttpStatus.valueOf(addedResult));
    }
}
