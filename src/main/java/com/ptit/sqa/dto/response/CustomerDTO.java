package com.ptit.sqa.dto.response;

import com.ptit.sqa.model.Address;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerDTO {

    private int id;
    private String name;
    private String email;
    private String phoneNumber;
    private AddressDTO address;
}
