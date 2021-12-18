package com.example.grocery.Model;

public class CartModel {
   String StoreName,TotalPrice,Location,AppointmentDate,documentId;

    public CartModel() {
    }


    public CartModel(String storeName, String totalPrice, String location, String appointmentDate) {
        StoreName = storeName;
        TotalPrice = totalPrice;
        Location = location;
        AppointmentDate = appointmentDate;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getAppointmentDate() {
        return AppointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        AppointmentDate = appointmentDate;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
}
