package com.ptit.sqa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    private int id;
    private String numberHouse;
    private String street;
    private String district;
    private String city;
}
