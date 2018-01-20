package com.example.lukakaic.equipmentmaintenance.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lukakaic on 1/18/18.
 */

public class ReservationResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("identifier")
    @Expose
    private String identifier;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("picture")
    @Expose
    private Object picture;
    @SerializedName("kit_id")
    @Expose
    private Integer kitId;
    @SerializedName("type_id")
    @Expose
    private Integer typeId;
    @SerializedName("subtype_id")
    @Expose
    private Integer subtypeId;
    @SerializedName("device_type_id")
    @Expose
    private Integer deviceTypeId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("working")
    @Expose
    private Integer working;
    @SerializedName("reservations")
    @Expose
    private List<Reservation> reservations;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getPicture() {
        return picture;
    }

    public void setPicture(Object picture) {
        this.picture = picture;
    }

    public Integer getKitId() {
        return kitId;
    }

    public void setKitId(Integer kitId) {
        this.kitId = kitId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getSubtypeId() {
        return subtypeId;
    }

    public void setSubtypeId(Integer subtypeId) {
        this.subtypeId = subtypeId;
    }

    public Integer getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(Integer deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getWorking() {
        return working;
    }

    public void setWorking(Integer working) {
        this.working = working;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}
