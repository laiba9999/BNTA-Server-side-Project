package com.teamname.allotments;

public class Allotment {
    private Integer id;
    private Integer x_coordinate;
    private String y_coordinate;

    public Allotment(Integer id, Integer x_coordinate, String y_coordinate) {
        this.id = id;
        this.x_coordinate = x_coordinate;
        this.y_coordinate = y_coordinate;
    }

    public Allotment() {
    }

    public Integer getId() {
        return id;
    }

    public Integer getX_coordinate() {
        return x_coordinate;
    }

    public void setX_coordinate(Integer x_coordinate) {
        this.x_coordinate = x_coordinate;
    }

    public String getY_coordinate() {
        return y_coordinate;
    }

    public void setY_coordinate(String y_coordinate) {
        this.y_coordinate = y_coordinate;
    }

    public void setId(Integer id) {
        this.id = id;
    }


}
