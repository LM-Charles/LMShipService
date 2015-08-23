package com.longmendelivery.persistence.entity;

import com.longmendelivery.persistence.DAOEntity;
import com.longmendelivery.service.model.user.AppUserGroupType;
import com.longmendelivery.service.model.user.AppUserStatusType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by  rabiddesireon 04/06/15.
 */
@Entity(name = "AppUser")
@Data
@EqualsAndHashCode(exclude = {"orders"})
@AllArgsConstructor
@NoArgsConstructor
public class AppUserEntity implements DAOEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String phone;
    private String email;
    private byte[] password_md5;
    @Enumerated(EnumType.STRING)
    private AppUserGroupType userGroup;
    @Enumerated(EnumType.STRING)
    private AppUserStatusType userStatus;
    private String apiToken;
    private String verificationCode;
    private String firstName;
    private String lastName;
    @OneToOne
    private AddressEntity address;
    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private Set<ShipOrderEntity> orders;

    public AppUserEntity(String phone, String email, byte[] password_md5, AppUserGroupType userGroup, AppUserStatusType userStatus) {
        this.phone = phone;
        this.email = email;
        this.password_md5 = password_md5;
        this.userGroup = userGroup;
        this.userStatus = userStatus;
    }
}
