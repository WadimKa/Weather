package com.wadimkazak.weather;

/**
 * Created by Wadim on 13.06.2018.
 */

public class City {
    private String name;
    private String current;

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {

        this.current = current;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public City(String name, String current) {

        this.name = name;
        this.current = current;
    }
}
