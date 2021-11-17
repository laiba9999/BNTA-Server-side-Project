package com.teamname.citizens;

import java.util.Objects;

public class Citizen {
    private Integer id;
    private String fullName;
    private Integer house_id;
    private Integer workplace_id;

    public Citizen(Integer id, String fullName, Integer house_id, Integer workplace_id) {
        this.id = id;
        this.fullName = fullName;
        this.house_id = house_id;
        this.workplace_id = workplace_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getHouse_id() {
        return house_id;
    }

    public void setHouse_id(Integer house_id) {
        this.house_id = house_id;
    }

    public Integer getWorkplace_id() {
        return workplace_id;
    }

    public void setWorkplace_id(Integer workplace_id) {
        this.workplace_id = workplace_id;
    }

    @Override
    public String toString() {
        return "Citizen{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", house_id=" + house_id +
                ", workplace_id=" + workplace_id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Citizen citizen = (Citizen) o;
        return Objects.equals(id, citizen.id) && Objects.equals(fullName, citizen.fullName) && Objects.equals(house_id, citizen.house_id) && Objects.equals(workplace_id, citizen.workplace_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, house_id, workplace_id);
    }
}
