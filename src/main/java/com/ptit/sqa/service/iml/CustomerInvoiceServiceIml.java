package com.ptit.sqa.service.iml;

import com.ptit.sqa.conponent.MappingHelper;
import com.ptit.sqa.dto.response.CustomerInvoiceDTO;
import com.ptit.sqa.model.Customer;
import com.ptit.sqa.model.CustomerInvoice;
import com.ptit.sqa.repository.CustomerInvoiceRepository;
import com.ptit.sqa.repository.CustomerRepository;
import com.ptit.sqa.service.CustomerInvoiceService;
import com.ptit.sqa.service.EmailService;
import com.ptit.sqa.service.kafka.KafkaPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerInvoiceServiceIml implements CustomerInvoiceService {

    private final CustomerInvoiceRepository customerInvoiceRepository;
    private final CustomerRepository customerRepository;
    private final KafkaPublisher kafkaPublisher;

    @Override
    public List<CustomerInvoiceDTO> listInvoiceThisMonth() {
        List<CustomerInvoice> invoices = customerInvoiceRepository.findByNewWaterIndexUsedIsNull();
        return MappingHelper.mapList(invoices, CustomerInvoiceDTO.class);
    }

    @Override
    public void addNewWaterIndex(int customerId, Integer newWaterIndex) {
        customerRepository.findById(customerId)
                .map(customer -> {
                    updateNewWaterIndex(customer, newWaterIndex);
                    return customer;
                })
                .orElseThrow(() -> {
                    throw  new RuntimeException("Customer not found");
                });
    }

    private void updateNewWaterIndex(Customer customer, Integer newWaterIndex){
        customerInvoiceRepository.findByNewWaterIndexUsedIsNullAndCustomerId(customer.getId())
                .map(customerInvoice -> {
                    customerInvoice.setNewWaterIndexUsed(newWaterIndex);
                    return customerInvoiceRepository.save(customerInvoice);
                })
                .map(customerInvoice -> {
                    CustomerInvoiceDTO customerInvoiceDTO = MappingHelper.map(customerInvoice, CustomerInvoiceDTO.class);
                    kafkaPublisher.sendMessage(customerInvoiceDTO);
                    return customerInvoice;
                })
                .orElseThrow(() -> {
                    throw new RuntimeException("customer invoices with newIndex is null not found");
                });
    }
}
