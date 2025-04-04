package com.cymbaleats.notification;
import com.google.cloud.spring.data.spanner.core.mapping.Interleaved;
import com.google.cloud.spring.data.spanner.core.mapping.PrimaryKey;
import com.google.cloud.spring.data.spanner.core.mapping.Table;
@Table(name = "Address")
public class Address {
  @PrimaryKey
  private  String userId;
  @PrimaryKey (keyOrder = 2)
  private long orderId;
  @PrimaryKey (keyOrder = 3)
  private long addressId;
  private String city;
  private String street;
  private String buildingNumber;
  private String apartmentNumber;
  private String zipCode;

  public Address() {

  }

  public Address(String street, String userId, long orderId, long addressId, String city,
      String buildingNumber, String apartmentNumber, String zipCode) {
    this.street = street;
    this.userId = userId;
    this.orderId = orderId;
    this.addressId = addressId;
    this.city = city;
    this.buildingNumber = buildingNumber;
    this.apartmentNumber = apartmentNumber;
    this.zipCode = zipCode;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public long getOrderId() {
    return orderId;
  }

  public void setOrderId(long orderId) {
    this.orderId = orderId;
  }

  public long getAddressId() {
    return addressId;
  }

  public void setAddressId(long addressId) {
    this.addressId = addressId;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getBuildingNumber() {
    return buildingNumber;
  }

  public void setBuildingNumber(String buildingNumber) {
    this.buildingNumber = buildingNumber;
  }

  public String getApartmentNumber() {
    return apartmentNumber;
  }

  public void setApartmentNumber(String apartmentNumber) {
    this.apartmentNumber = apartmentNumber;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("Address{");
    sb.append("userId='").append(userId).append('\'');
    sb.append(", orderId='").append(orderId).append('\'');
    sb.append(", addressId='").append(addressId).append('\'');
    sb.append(", city='").append(city).append('\'');
    sb.append(", street='").append(street).append('\'');
    sb.append(", buildingNumber='").append(buildingNumber).append('\'');
    sb.append(", apartmentNumber='").append(apartmentNumber).append('\'');
    sb.append(", zipCode='").append(zipCode).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
