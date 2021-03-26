package com.ptit.sqa.repository;

import com.ptit.sqa.model.CustomerInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerInvoiceRepository extends JpaRepository<CustomerInvoice, Integer> {

    List<CustomerInvoice> findByNewWaterIndexUsedIsNull();

    Optional<CustomerInvoice> findByNewWaterIndexUsedIsNullAndCustomerId(int customerId);

}
