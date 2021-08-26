package com.example.retrofit2_practice;

public class Weather {

    float id;
    String main;
    String description;
    String icon;

    public float getId() {
        return id;
    }

    public void setId(float id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
