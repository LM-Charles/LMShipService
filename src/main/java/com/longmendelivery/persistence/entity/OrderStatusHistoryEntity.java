package com.longmendelivery.persistence.entity;

import javax.persistence.*;

/**
 * Created by desmond on 04/06/15.
 */
@Entity
@Table(name = "ORDER_STATUS_HISTORY")
class OrderStatusHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    OrderEntity orderId;

    @Column(name = "STATUS", nullable = false)
    String status;

    @Column(name = "STATUS_DESCRIPTION", nullable = false)
    String statusDescription;

    @Column(name = "HANDLER")
    String handler;

    public OrderStatusHistoryEntity(Integer id, OrderEntity orderId, String status, String statusDescription, String handler) {
        this.id = id;
        this.orderId = orderId;
        this.status = status;
        this.statusDescription = statusDescription;
        this.handler = handler;
    }

    public OrderStatusHistoryEntity() {
    }

    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public OrderEntity getOrderId() {
        return orderId;
    }

    public void setOrderId(OrderEntity order) {
        this.orderId = order;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderStatusHistoryEntity that = (OrderStatusHistoryEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (orderId != null ? !orderId.equals(that.orderId) : that.orderId != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (statusDescription != null ? !statusDescription.equals(that.statusDescription) : that.statusDescription != null)
            return false;
        return !(handler != null ? !handler.equals(that.handler) : that.handler != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (orderId != null ? orderId.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (statusDescription != null ? statusDescription.hashCode() : 0);
        result = 31 * result + (handler != null ? handler.hashCode() : 0);
        return result;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }
}
