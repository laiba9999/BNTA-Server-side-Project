package com.teamname.citizens;

public class Citizens {
    private Integer id;
    private String fullName;
    private Integer home_id;
    private Integer workplace_id;

    public Citizens(Integer id, String fullName, Integer home_id, Integer workplace_id) {
        this.id = id;
        this.fullName = fullName;
        this.home_id = home_id;
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

    public Integer getHome_id() {
        return home_id;
    }

    public void setHome_id(Integer home_id) {
        this.home_id = home_id;
    }

    public Integer getWorkplace_id() {
        return workplace_id;
    }

    public void setWorkplace_id(Integer workplace_id) {
        this.workplace_id = workplace_id;
    }
}
