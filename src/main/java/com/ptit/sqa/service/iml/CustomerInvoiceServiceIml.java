package com.ptit.sqa.service.iml;

import com.ptit.sqa.conponent.MappingHelper;
import com.ptit.sqa.dto.response.CustomerInvoiceDTO;
import com.ptit.sqa.model.Customer;
import com.ptit.sqa.model.CustomerInvoice;
import com.ptit.sqa.repository.CustomerInvoiceRepository;
import com.ptit.sqa.repository.CustomerRepository;
import com.ptit.sqa.service.CustomerInvoiceService;
import com.ptit.sqa.service.kafka.KafkaPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerInvoiceServiceIml implements CustomerInvoiceService {

    private final CustomerInvoiceRepository customerInvoiceRepository;
    private final CustomerRepository customerRepository;
    private final KafkaPublisher kafkaPublisher;

    /*
    This is a http status code in result of api add new water index
     */
    private final int OK = 200;
    private final int BAD_REQUEST = 400;
    private final int NOT_FOUND = 404;

    @Override
    public List<CustomerInvoiceDTO> listInvoiceThisMonth() {
        List<CustomerInvoice> invoices = customerInvoiceRepository.findByNewWaterIndexUsedIsNull();
        return MappingHelper.mapList(invoices, CustomerInvoiceDTO.class);
    }

    /*
    add new water index: add new index use water for customer.
     */
    @Override
    public int addNewWaterIndex(int customerId, Integer newWaterIndex) {
        return customerRepository.findById(customerId)
                .map(customer -> {
                    int status = updateNewWaterIndex(customer, newWaterIndex);
                    return status;
                })
                .orElse(NOT_FOUND);
    }

    /*
    Update new index in database
     */
    private int updateNewWaterIndex(Customer customer, Integer newWaterIndex){
        CustomerInvoice customerInvoice = customerInvoiceRepository.findByNewWaterIndexUsedIsNullAndCustomerId(customer.getId()).orElse(null);
        if (Objects.isNull(customerInvoice)){
            return NOT_FOUND;
        }
        else {
            if (newWaterIndex < customerInvoice.getOldWaterIndexUsed()){
                return BAD_REQUEST;
            }
            else {
                customerInvoice.setNewWaterIndexUsed(newWaterIndex);
                customerInvoice = customerInvoiceRepository.save(customerInvoice);
                CustomerInvoiceDTO customerInvoiceDTO = MappingHelper.map(customerInvoice, CustomerInvoiceDTO.class);
                kafkaPublisher.sendMessage(customerInvoiceDTO); // publish message to kafka server
                return OK;
            }
        }
    }
}
