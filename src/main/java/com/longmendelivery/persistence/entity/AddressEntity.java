package com.longmendelivery.persistence.entity;

import com.longmendelivery.persistence.DAOEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by desmond on 04/06/15.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Address")
public class AddressEntity implements DAOEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String address;
    private String city;
    private String province;
    private String postal;
    private String country;
    private Boolean residential;
}
