package com.alfonso.cliente.dto;

public class ClientRequest {
    private String name;
    private String lastName;
    private String email;
    private String phone;
    private String documentNumber;
    private String address;

    public ClientRequest() {
    }

    public ClientRequest(String name, String lastName, String email, String phone, String documentNumber, String address) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.documentNumber = documentNumber;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
