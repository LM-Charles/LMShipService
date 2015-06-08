package com.longmendelivery.persistence.model;

import javax.persistence.*;

/**
 * Created by  rabiddesireon 04/06/15.
 */
@Entity
@Table(name = "SHIPMENT_SERVICE")
public class ShipmentService {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "COURIER_NAME")
    String courierName;

    @Column(name = "SERVICE_NAME")
    String serviceName;

    public Integer getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShipmentService that = (ShipmentService) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (courierName != null ? !courierName.equals(that.courierName) : that.courierName != null) return false;
        if (serviceName != null ? !serviceName.equals(that.serviceName) : that.serviceName != null) return false;
        if (serviceCode != null ? !serviceCode.equals(that.serviceCode) : that.serviceCode != null) return false;
        if (apiProvider != null ? !apiProvider.equals(that.apiProvider) : that.apiProvider != null) return false;
        return !(lmCategoryCode != null ? !lmCategoryCode.equals(that.lmCategoryCode) : that.lmCategoryCode != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (courierName != null ? courierName.hashCode() : 0);
        result = 31 * result + (serviceName != null ? serviceName.hashCode() : 0);
        result = 31 * result + (serviceCode != null ? serviceCode.hashCode() : 0);
        result = 31 * result + (apiProvider != null ? apiProvider.hashCode() : 0);
        result = 31 * result + (lmCategoryCode != null ? lmCategoryCode.hashCode() : 0);
        return result;
    }

    public String getCourierName() {

        return courierName;
    }

    public void setCourierName(String courierName) {
        this.courierName = courierName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getApiProvider() {
        return apiProvider;
    }

    public void setApiProvider(String apiProvider) {
        this.apiProvider = apiProvider;
    }

    public String getLmCategoryCode() {
        return lmCategoryCode;
    }

    public void setLmCategoryCode(String lmCategoryCode) {
        this.lmCategoryCode = lmCategoryCode;
    }

    public void setId(Integer id) {

        this.id = id;
    }

    @Column(name = "SERVICE_CODE", nullable = false)
    String serviceCode;

    @Column(name="API_PROVIDER", nullable = false)
    String apiProvider;

    @Column(name="LM_CATEGORY_CODE", nullable = false)
    String lmCategoryCode;

    public ShipmentService() {
    }

    public ShipmentService(String courierName, String serviceName, String serviceCode, String apiProvider, String lmCategoryCode) {

        this.courierName = courierName;
        this.serviceName = serviceName;
        this.serviceCode = serviceCode;
        this.apiProvider = apiProvider;
        this.lmCategoryCode = lmCategoryCode;
    }
}
