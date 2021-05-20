package com.ptit.sqa;

import com.ptit.sqa.dto.response.AddressDTO;
import com.ptit.sqa.dto.response.CustomerDTO;
import com.ptit.sqa.dto.response.CustomerInvoiceDTO;
import com.ptit.sqa.model.CustomerInvoice;
import com.ptit.sqa.repository.CustomerInvoiceRepository;
import com.ptit.sqa.service.CustomerInvoiceService;
import com.ptit.sqa.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@Slf4j
class SqaWaterMoneyManagementApplicationTests {

    @Autowired
    private EmailService emailService;

    @Autowired
    private CustomerInvoiceService customerInvoiceService;

    @Autowired
    private CustomerInvoiceRepository customerInvoiceRepository;

    @Test
    void contextLoads() {
    }

    /*
    This class have a mission to test the task send email automation to customer's email
     */
    @Test
    public void testSendEmail(){
        AddressDTO address = AddressDTO.builder().id(1).city("Thai Binh").district("kien Xuong").numberHouse("12A").street("Vu An").build();
        CustomerDTO customer = new CustomerDTO(1, "Dat", "trinhvandat90399@gmail.com", "asdfsdaf", address);
        CustomerInvoiceDTO customerInvoiceDTO = new CustomerInvoiceDTO(1, 200, 0, LocalDateTime.now(), customer);
        boolean result = emailService.noticePaymentBill(customerInvoiceDTO);
        assertEquals(result, true);
    }

    /*
    This method have a mission to test the task add new index water
     */
    @Test
    public void testAddNewIndexWater(){
        final int customerId = 1;
        final int newWaterIndex = 200;
        CustomerInvoice invoiceBeforeAddNewIndex = customerInvoiceRepository.findByNewWaterIndexUsedIsNullAndCustomerId(customerId).orElse(null);
        CustomerInvoice invoiceBeforeTest = customerInvoiceRepository.findByNewWaterIndexUsedIsNullAndCustomerId(customerId).orElse(null);;
        int result = customerInvoiceService.addNewWaterIndex(customerId, newWaterIndex);
        final CustomerInvoice invoiceAfterAddNewWaterIndex = customerInvoiceRepository.findById(invoiceBeforeAddNewIndex.getId()).orElse(null);
        invoiceBeforeAddNewIndex.setNewWaterIndexUsed(newWaterIndex);
        assertEquals(invoiceAfterAddNewWaterIndex.toString(), invoiceBeforeAddNewIndex.toString());
        assertEquals(result, 200);
        customerInvoiceRepository.save(invoiceBeforeTest);
    }

    /*
    test function add new index when new index less than old index
     */
    @Test
    public void testApiAddNewWaterIndexBad() throws Exception {
        final int newWaterIndex = -1;
        final int customerId = 2;
        int result = customerInvoiceService.addNewWaterIndex(customerId, newWaterIndex);
        assertEquals(result, 400);
    }

    /*
    test function add new index when don't have a customer or customer's invoices
    */
    @Test
    public void testApiAddNewWaterIndexNotFound() throws Exception {
        final int newWaterIndex = 200;
        final int customerId = 5;
        int result = customerInvoiceService.addNewWaterIndex(customerId, newWaterIndex);
        assertEquals(result, 404);
    }
}
