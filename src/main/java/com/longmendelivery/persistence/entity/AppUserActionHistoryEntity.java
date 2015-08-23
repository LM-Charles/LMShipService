package com.longmendelivery.persistence.entity;

import com.longmendelivery.persistence.DAOEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by  rabiddesireon 04/06/15.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "AppUserActionHistory")
class AppUserActionHistoryEntity implements DAOEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    AppUserEntity appUserId;

    String action;

    String actionDescription;
}
