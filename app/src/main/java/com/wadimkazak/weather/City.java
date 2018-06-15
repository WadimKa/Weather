package com.wadimkazak.weather;

/**
 * Created by Wadim on 13.06.2018.
 */

public class City {
    private String name;
    private String degree;
    private String humidity;
    private String description;

    public City() {
    }


    public City(String name, String degree, String humidity, String description) {
        this.name = name;
        this.degree = degree;
        this.humidity = humidity;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
