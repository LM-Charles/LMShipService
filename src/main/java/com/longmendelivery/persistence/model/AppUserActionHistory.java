package com.longmendelivery.persistence.model;

import javax.persistence.*;

/**
 * Created by  rabiddesireon 04/06/15.
 */

@Entity
@Table(name = "APP_USER_ACTION_HISTORY")
class AppUserActionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "APP_USER_ID", nullable = false)
    AppUser appUserId;

    @Column(name = "ACTION", nullable = false)
    String action;

    @Column(name = "ACTION_DESCRIPTION", nullable = false)
    String actionDescription;

    public AppUserActionHistory() {
    }

    public AppUserActionHistory(Integer id, AppUser appUserId, String action, String actionDescription) {

        this.id = id;
        this.appUserId = appUserId;
        this.action = action;
        this.actionDescription = actionDescription;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppUserActionHistory that = (AppUserActionHistory) o;

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

    public void setId(Integer id) {
        this.id = id;
    }

    public AppUser getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(AppUser appUserId) {
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
