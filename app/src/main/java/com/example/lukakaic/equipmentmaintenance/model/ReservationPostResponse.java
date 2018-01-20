package com.example.lukakaic.equipmentmaintenance.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lukakaic on 1/19/18.
 */

public class ReservationPostResponse {

    @SerializedName("start_date")
    @Expose
    private String start_date;
    @SerializedName("return_date")
    @Expose
    private String return_date;
    @SerializedName("item_id")
    @Expose
    private List<String> item_id;
    @SerializedName("remark")
    @Expose
    private String remark;

    public ReservationPostResponse(String start_date, String return_date, List<String> item_id, String remark) {
        this.start_date = start_date;
        this.return_date = return_date;
        this.item_id = item_id;
        this.remark = remark;
    }
}
