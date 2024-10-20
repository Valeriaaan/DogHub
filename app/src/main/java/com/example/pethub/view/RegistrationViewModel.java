package com.example.pethub.view;

import androidx.lifecycle.ViewModel;

public class RegistrationViewModel extends ViewModel {
    private String dogName;
    private String dogBreed;
    private String profileImageUri;
    private String vaccinationDate;
    private String dueDate;
    private String vetClinic;
    // Add other fields as needed

    // Setters
    public void setDogName(String name) {
        this.dogName = name;
    }

    public void setDogBreed(String breed) {
        this.dogBreed = breed;
    }

    public void setProfileImageUri(String uri) {
        this.profileImageUri = uri;
    }

    public void setVaccinationDate(String date) {
        this.vaccinationDate = date;
    }

    public void setDueDate(String date) {
        this.dueDate = date;
    }

    public void setVetClinic(String clinic) {
        this.vetClinic = clinic;
    }

    // Getters
    public String getDogName() {
        return dogName;
    }

    public String getDogBreed() {
        return dogBreed;
    }

    public String getProfileImageUri() {
        return profileImageUri;
    }

    public String getVaccinationDate() {
        return vaccinationDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getVetClinic() {
        return vetClinic;
    }
}
