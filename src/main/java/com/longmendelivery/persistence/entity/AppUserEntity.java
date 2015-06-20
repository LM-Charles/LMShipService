package com.longmendelivery.persistence.entity;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by desmond on 04/06/15.
 */
@Entity
@Table(name = "APP_USER")
public class AppUserEntity {
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

    @OneToMany(mappedBy = "client")
    private Set<OrderEntity> orders;

    public AppUserEntity(String phone, String email, String password_md5, AppUserGroupEntity userGroup, AppUserStatusEntity userStatus) {
        this.phone = phone;
        this.email = email;
        this.password_md5 = password_md5;
        this.userGroup = userGroup;
        this.userStatus = userStatus;
    }


    public AppUserEntity(Integer id, String phone, String email, String password_md5, AppUserGroupEntity userGroup, AppUserStatusEntity userStatus, String apiToken, String verificationString, String firstName, String lastName, String address, String city, String province, String country, Set<OrderEntity> orders) {
        this.id = id;
        this.phone = phone;
        this.email = email;
        this.password_md5 = password_md5;
        this.userGroup = userGroup;
        this.userStatus = userStatus;
        this.apiToken = apiToken;
        this.verificationString = verificationString;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.province = province;
        this.country = country;
        this.orders = orders;
    }

    public AppUserEntity() {

    }

    @Override
    public String
    toString() {
        return "AppUserEntity{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", password_md5='" + password_md5 + '\'' +
                ", userGroup=" + userGroup +
                ", userStatus=" + userStatus +
                ", apiToken='" + apiToken + '\'' +
                ", verificationString='" + verificationString + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", country='" + country + '\'' +
                ", orders=" + orders +
                '}';
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppUserEntity appUser = (AppUserEntity) o;

        if (id != null ? !id.equals(appUser.id) : appUser.id != null) return false;
        if (phone != null ? !phone.equals(appUser.phone) : appUser.phone != null) return false;
        if (email != null ? !email.equals(appUser.email) : appUser.email != null) return false;
        if (password_md5 != null ? !password_md5.equals(appUser.password_md5) : appUser.password_md5 != null)
            return false;
        if (userGroup != appUser.userGroup) return false;
        if (userStatus != appUser.userStatus) return false;
        if (apiToken != null ? !apiToken.equals(appUser.apiToken) : appUser.apiToken != null) return false;
        if (verificationString != null ? !verificationString.equals(appUser.verificationString) : appUser.verificationString != null)
            return false;
        if (firstName != null ? !firstName.equals(appUser.firstName) : appUser.firstName != null) return false;
        if (lastName != null ? !lastName.equals(appUser.lastName) : appUser.lastName != null) return false;
        if (address != null ? !address.equals(appUser.address) : appUser.address != null) return false;
        if (city != null ? !city.equals(appUser.city) : appUser.city != null) return false;
        if (province != null ? !province.equals(appUser.province) : appUser.province != null) return false;
        if (country != null ? !country.equals(appUser.country) : appUser.country != null) return false;
        return !(orders != null ? !orders.equals(appUser.orders) : appUser.orders != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password_md5 != null ? password_md5.hashCode() : 0);
        result = 31 * result + (userGroup != null ? userGroup.hashCode() : 0);
        result = 31 * result + (userStatus != null ? userStatus.hashCode() : 0);
        result = 31 * result + (apiToken != null ? apiToken.hashCode() : 0);
        result = 31 * result + (verificationString != null ? verificationString.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (province != null ? province.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (orders != null ? orders.hashCode() : 0);
        return result;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword_md5() {
        return password_md5;
    }

    public void setPassword_md5(String password_md5) {
        this.password_md5 = password_md5;
    }

    public AppUserGroupEntity getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(AppUserGroupEntity userGroup) {
        this.userGroup = userGroup;
    }

    public AppUserStatusEntity getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(AppUserStatusEntity userStatus) {
        this.userStatus = userStatus;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public String getVerificationString() {
        return verificationString;
    }

    public void setVerificationString(String verificationString) {
        this.verificationString = verificationString;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Set<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(Set<OrderEntity> orders) {
        this.orders = orders;
    }
}
