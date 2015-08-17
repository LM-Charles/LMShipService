package com.longmendelivery.persistence.entity;

import com.longmendelivery.lib.conversion.DAOEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by  rabiddesireon 04/06/15.
 */
@Entity
@Table(name = "ADDRESS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressEntity implements DAOEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ADDRESS", nullable = false)
    private String address;
    @Column(name = "CITY", nullable = false)
    private String city;
    @Column(name = "PROVINCE", nullable = false)
    private String province;
    @Column(name = "CODE", nullable = false)
    private String postal;
    @Column(name = "COUNTRY", nullable = false)
    private String country;
    @Column(name = "RESIDENTIAL", nullable = false)
    private Boolean residential;
}
