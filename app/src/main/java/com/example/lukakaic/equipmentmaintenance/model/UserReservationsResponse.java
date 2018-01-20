package com.example.lukakaic.equipmentmaintenance.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lukakaic on 1/19/18.
 */

public class UserReservationsResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("return_date")
    @Expose
    private String returnDate;
    @SerializedName("returned_date")
    @Expose
    private Object returnedDate;
    @SerializedName("remark")
    @Expose
    private String remark;
    @SerializedName("status_id")
    @Expose
    private Integer statusId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("items")
    @Expose
    private List<ItemDetails> items = null;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("extends")
    @Expose
    private List<Object> _extends = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public Object getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(Object returnedDate) {
        this.returnedDate = returnedDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
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

    public List<ItemDetails> getItems() {
        return items;
    }

    public void setItems(List<ItemDetails> items) {
        this.items = items;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Object> getExtends() {
        return _extends;
    }

    public void setExtends(List<Object> _extends) {
        this._extends = _extends;
    }
}