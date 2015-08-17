package com.longmendelivery.persistence.entity;

import com.longmendelivery.lib.conversion.DAOEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by desmond on 04/06/15.
 */
@Entity
@Table(name = "APP_USER")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserEntity implements DAOEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "PHONE", nullable = false)
    private String phone;
    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;
    @Column(name = "PASSWORD_MD5", nullable = false)
    private String password_md5;
    @Column(name = "USER_GROUP", nullable = false)
    @Enumerated(EnumType.STRING)
    private AppUserGroupEntity userGroup;
    @Column(name = "USER_STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private AppUserStatusEntity userStatus;
    @Column(name = "API_TOKEN")
    private String apiToken;
    @Column(name = "VERIFICATION_STRING")
    private String verificationString;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "CITY")
    private String city;
    @Column(name = "PROVINCE")
    private String province;
    @Column(name = "COUNTRY")
    private String country;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<OrderEntity> orders;

    public AppUserEntity(String phone, String email, String password_md5, AppUserGroupEntity userGroup, AppUserStatusEntity userStatus) {
        this.phone = phone;
        this.email = email;
        this.password_md5 = password_md5;
        this.userGroup = userGroup;
        this.userStatus = userStatus;
    }
}
