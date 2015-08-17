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
@Table(name = "APP_USER_ACTION_HISTORY")
@Data
@AllArgsConstructor
@NoArgsConstructor
class AppUserActionHistoryEntity implements DAOEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "APP_USER_ID", nullable = false)
    AppUserEntity appUserId;

    @Column(name = "ACTION", nullable = false)
    String action;

    @Column(name = "ACTION_DESCRIPTION", nullable = false)
    String actionDescription;
}
