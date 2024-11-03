package com.example.pethub.model;

public class AlarmItem {

    private String id;
    private String label;
    private String time;
    private boolean isEnable;


    // No-argument constructor required for Firestore deserialization
    public AlarmItem() {
    }

    // Constructor for creating AlarmItem instances
    // Constructor
    public AlarmItem(String id, String label, String time, boolean isEnabled) {
        this.id = id;  // Make sure this is set correctly
        this.label = label;
        this.time = time;
        this.isEnable = isEnabled;
    }

    // Getters and Setters
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isEnabled() {
        return isEnable;
    }

    public void setEnabled(boolean isEnable) {
        this.isEnable = isEnable;
    }
}
