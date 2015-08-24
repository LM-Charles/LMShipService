package com.longmendelivery.service.model.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by desmond on 05/07/15.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressModel {
    private String address;
    private String address2;
    private String city;
    private String province;
    private String country;
    private String postal;
    private Boolean residential = false;
}
