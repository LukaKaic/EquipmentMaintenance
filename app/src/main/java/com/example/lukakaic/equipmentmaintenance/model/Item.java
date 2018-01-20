package com.example.lukakaic.equipmentmaintenance.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lukakaic on 1/11/18.
 */

public class Item {


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
    private String picture;
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
    @SerializedName("kit")
    @Expose
    private Kit kit;
    @SerializedName("type")
    @Expose
    private Type type;
    @SerializedName("subtype")
    @Expose
    private SubType subtype;
    @SerializedName("device_type")
    @Expose
    private DeviceType deviceType;

    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
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

    public Kit getKit() {
        return kit;
    }

    public void setKit(Kit kit) {
        this.kit = kit;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public SubType getSubtype() {
        return subtype;
    }

    public void setSubtype(SubType subtype) {
        this.subtype = subtype;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

}
