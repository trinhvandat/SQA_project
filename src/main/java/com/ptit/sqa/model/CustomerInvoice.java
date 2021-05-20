package com.ptit.sqa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "customer_invoice")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerInvoice extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id")
    private int id;

    @Column(name = "new_water_index_used")
    private Integer newWaterIndexUsed;

    @Column(name = "old_water_index_used")
    private int oldWaterIndexUsed;

    @Column(name = "time_using")
    private LocalDateTime timeUsing = LocalDateTime.now();

    @ManyToOne
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
