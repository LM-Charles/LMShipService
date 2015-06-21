package com.longmendelivery.persistence.entity;

import com.longmendelivery.lib.conversion.DAOEntity;

import javax.persistence.*;

/**
 * Created by desmond on 04/06/15.
 */

@Entity
@Table(name = "APP_USER_ACTION_HISTORY")
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

    public AppUserActionHistoryEntity() {
    }

    public AppUserActionHistoryEntity(Integer id, AppUserEntity appUserId, String action, String actionDescription) {

        this.id = id;
        this.appUserId = appUserId;
        this.action = action;
        this.actionDescription = actionDescription;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppUserActionHistoryEntity that = (AppUserActionHistoryEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (appUserId != null ? !appUserId.equals(that.appUserId) : that.appUserId != null) return false;
        if (action != null ? !action.equals(that.action) : that.action != null) return false;
        return !(actionDescription != null ? !actionDescription.equals(that.actionDescription) : that.actionDescription != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (appUserId != null ? appUserId.hashCode() : 0);
        result = 31 * result + (action != null ? action.hashCode() : 0);
        result = 31 * result + (actionDescription != null ? actionDescription.hashCode() : 0);
        return result;
    }

    public AppUserEntity getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(AppUserEntity appUserId) {
        this.appUserId = appUserId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getActionDescription() {
        return actionDescription;
    }

    public void setActionDescription(String actionDescription) {
        this.actionDescription = actionDescription;
    }
}
