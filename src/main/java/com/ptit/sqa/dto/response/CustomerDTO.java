package com.ptit.sqa.dto.response;

import com.ptit.sqa.model.Address;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class CustomerDTO {

    private int id;
    private String name;
    private String email;
    private String phoneNumber;
    private AddressDTO address;

    public CustomerDTO(int id, String name, String email, String phoneNumber, AddressDTO address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}
