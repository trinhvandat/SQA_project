package com.ptit.sqa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "customer")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerInvoice extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id")
    private int id;

    @Column(name = "new_water_index_used")
    private int newWaterIndexUsed;

    @Column(name = "old_water_index_used")
    private int oldWaterIndexUsed;

    @Column(name = "time_using")
    private LocalDateTime timeUsing = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerInvoice)) return false;
        if (!super.equals(o)) return false;
        CustomerInvoice that = (CustomerInvoice) o;
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
