package com.example.pethub.model;

public class Dog {
    private String documentId;
    private String name;
    private String breed;
    private String picture;
    private String sex;
    private int age;

    private String lastVaccinationDate;
    private String nextVaccinationDate;
    private String clinic;

    private String allergies;
    private String medication;
    private String surgery;

    public Dog() {}

    // Updated constructor with all parameters
    public Dog(String name, int age, String sex, String breed, String allergies,
               String lastVaccinationDate, String nextVaccinationDate, String clinic, String picture, String surgery, String medication) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.breed = breed;
        this.allergies = allergies;
        this.lastVaccinationDate = lastVaccinationDate;
        this.nextVaccinationDate = nextVaccinationDate;
        this.clinic = clinic;
        this.picture = picture;
        this.medication = medication;
        this.surgery = surgery;

    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    // Getters and Setters...

    public String getDogName() {
        return name;
    }

    public String getDogBreed() {
        return breed;
    }

    public String getDogPicture() {
        return picture;
    }

    public String getDogSex() {
        return sex;
    }

    public int getDogAge() {
        return age;
    }

    public String getLastVaccinationDate() {
        return lastVaccinationDate;
    }

    public String getNextVaccinationDate() {
        return nextVaccinationDate;
    }

    public String getClinic() {
        return clinic;
    }

    public String getAllergies() {
        return allergies;
    }

    public String getMedication() {
        return medication;
    }

    public String getSurgery() {
        return surgery;
    }

    public void setDogName(String name) {
        this.name = name;
    }

    public void setDogBreed(String breed) {
        this.breed = breed;
    }

    public void setDogPicture(String picture) {
        this.picture = picture;
    }

    public void setDogSex(String sex) {
        this.sex = sex;
    }

    public void setDogAge(int age) {
        this.age = age;
    }

    public void setLastVaccinationDate(String lastVaccinationDate) {
        this.lastVaccinationDate = lastVaccinationDate;
    }

    public void setNextVaccinationDate(String nextVaccinationDate) {
        this.nextVaccinationDate = nextVaccinationDate;
    }

    public void setClinic(String clinic) {
        this.clinic = clinic;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public void setSurgery(String surgery) {
        this.surgery = surgery;
    }
}
