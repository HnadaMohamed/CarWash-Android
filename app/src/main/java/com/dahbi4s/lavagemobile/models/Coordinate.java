package com.dahbi4s.lavagemobile.models;

import java.util.ArrayList;

public class Coordinate {
    private String type;
    private ArrayList<Double> coordinates;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Double> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(ArrayList<Double> coordinates) {
        this.coordinates = coordinates;
    }
}
