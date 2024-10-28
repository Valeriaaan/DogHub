package com.example.pethub.model;

public class Contact {
    private String name;
    private String contact;
    private String address;
    private String picture;
    private String vet;

    public Contact() {
    }

    public Contact(String name, String contact, String address, String picture) {
        this.name = name;
        this.contact = contact;
        this.address = address;
        this.picture = picture;
        this.vet = vet;
    }

    public String getVetName() {
        return name;
    }

    public String getVet() {
        return vet;
    }

    public String getVetContact() {
        return contact;
    }

    public String getVetAddress() {
        return address;
    }

    public String getVetPicture() {
        return picture;
    }

    public void setVetName(String name) {
        this.name = name;
    }

    public void setVet(String vet) {
        this.vet = vet;
    }

    public void setVetContact(String contact) {
        this.contact = contact;
    }

    public void setVetAddress(String address) {
        this.address = address;
    }

    public void setVetPicture(String picture) {
        this.picture = picture;
    }
}
