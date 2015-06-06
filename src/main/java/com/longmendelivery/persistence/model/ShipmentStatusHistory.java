package com.longmendelivery.persistence.model;

import javax.persistence.*;

/**
 * Created by  rabiddesireon 04/06/15.
 */
@Entity
@Table(name = "SHIPMENT_STATUS_HISTORY")
class ShipmentStatusHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String id;

    @ManyToOne
    @JoinColumn(name = "SHIPMENT_ID")
    Shipment shipmentId;

    @Column(name = "STATUS", nullable = false)
    String status;

    @Column(name = "STATUS_DESCRIPTION", nullable = false)
    String statusDescription;

    @Column(name = "HANDLER")
    String handler;

    public ShipmentStatusHistory(String id, Shipment shipmentId, String status, String statusDescription, String handler) {
        this.id = id;
        this.shipmentId = shipmentId;
        this.status = status;
        this.statusDescription = statusDescription;
        this.handler = handler;
    }

    public ShipmentStatusHistory() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Shipment getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Shipment shipment) {
        this.shipmentId = shipment;
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

        ShipmentStatusHistory that = (ShipmentStatusHistory) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (shipmentId != null ? !shipmentId.equals(that.shipmentId) : that.shipmentId != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (statusDescription != null ? !statusDescription.equals(that.statusDescription) : that.statusDescription != null)
            return false;
        return !(handler != null ? !handler.equals(that.handler) : that.handler != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (shipmentId != null ? shipmentId.hashCode() : 0);
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
