package com.ptit.sqa.model;


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "level")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Level extends BaseEntity implements Serializable {

    @Id
    @Column(name = "level_id")
    private int id;
    @Column(name = "max_value")
    private Integer maxValue;
    @Column(name = "price")
    private float price;


}

