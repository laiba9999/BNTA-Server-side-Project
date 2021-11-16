package com.teamname.buildings;

import java.util.Objects;

public abstract class Building {
    private Integer id;
    private String buildingName;
    private Integer capacity;
    private Integer allotment_id;

    public Building(Integer id, String buildingName, Integer capacity, Integer allotment_id) {
        this.id = id;
        this.buildingName = buildingName;
        this.capacity = capacity;
        this.allotment_id = allotment_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getAllotment_id() {
        return allotment_id;
    }

    public void setAllotment_id(Integer allotment_id) {
        this.allotment_id = allotment_id;
    }

    @Override
    public String toString() {
        return "Building{" +
                "id=" + id +
                ", buildingName='" + buildingName + '\'' +
                ", capacity=" + capacity +
                ", allotment_id=" + allotment_id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Building building = (Building) o;
        return Objects.equals(id, building.id) && Objects.equals(buildingName, building.buildingName) && Objects.equals(capacity, building.capacity) && Objects.equals(allotment_id, building.allotment_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, buildingName, capacity, allotment_id);
    }
}
