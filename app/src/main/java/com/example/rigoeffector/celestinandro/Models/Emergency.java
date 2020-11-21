package com.example.rigoeffector.celestinandro.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rigoeffector on 9/28/20.
 */

public class Emergency {
    @SerializedName("id")
    private String em_id;

    private String em_name,em_location,em_desc,em_phone,em_type;

    public String getEm_id() {
        return em_id;
    }

    public void setEm_id(String em_id) {
        this.em_id = em_id;
    }

    public String getEm_name() {
        return em_name;
    }

    public void setEm_name(String em_name) {
        this.em_name = em_name;
    }

    public String getEm_location() {
        return em_location;
    }

    public void setEm_location(String em_location) {
        this.em_location = em_location;
    }

    public String getEm_desc() {
        return em_desc;
    }

    public void setEm_desc(String em_desc) {
        this.em_desc = em_desc;
    }

    public String getEm_phone() {
        return em_phone;
    }

    public void setEm_phone(String em_phone) {
        this.em_phone = em_phone;
    }

    public String getEm_type() {
        return em_type;
    }

    public void setEm_type(String em_type) {
        this.em_type = em_type;
    }
}
