package com.example.lukakaic.equipmentmaintenance.rest;

import com.example.lukakaic.equipmentmaintenance.model.Item;
import com.example.lukakaic.equipmentmaintenance.model.ReservationPostResponse;
import com.example.lukakaic.equipmentmaintenance.model.ReservationResponse;
import com.example.lukakaic.equipmentmaintenance.model.User;
import com.example.lukakaic.equipmentmaintenance.model.UserReservationsResponse;
import com.example.lukakaic.equipmentmaintenance.model.UserResponse;
import com.example.lukakaic.equipmentmaintenance.model.Users;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by lukakaic on 1/11/18.
 */

public interface ApiInterface {


    @GET("items")
    Call<List<Item>> getItems(@Header("Authorization") String token);

    @GET("items/status/{id}")
    Call<ResponseBody> getItemReservationInfo(@Header("Authorization") String token, @Path("id") Integer id);

    @POST("reservations/request")
    @FormUrlEncoded
    Call<ResponseBody> reservation(@Header("Authorization") String token,
                                   @Field("start_date") String start_date,
                                   @Field("return_date") String return_date,
                                   @Field("item_id") String item_id,
                                   @Field("remark") String remark);
    @POST("reservations/request")
    Call<Integer> reservation1(@Header("Authorization") String token,
                               @Body ReservationPostResponse friendModel);

    @GET("reservations")
    Call<List<Item>> getReservations(@Header("Authorization") String token);

    @GET("reservations/item/{id}")
    Call<List<ReservationResponse>> getReresrvationsOfItem(@Header("Authorization") String token,
                                                           @Path("id") Integer id);

    @GET("reservations/user/{id}")
    Call<List<UserReservationsResponse>> getReservationsOfUser(@Header("Authorization") String token,
                                                               @Path("id") Integer id);

    @POST("auth/register")
    @FormUrlEncoded
    Call<String> saveUser(@Field("email") String email,
                        @Field("password") String password,
                        @Field("first_name") String first_name,
                        @Field("last_name") String last_name);

    @POST("auth/login")
    @FormUrlEncoded
    Call<UserResponse> loginUser(@Field("email") String email,
                                 @Field("password") String password);


    @GET("users/")
    Call<Users> getUsers(@Header("Authorization") String token);

    @GET("users/current")
    Call<User> getUser(@Header("Authorization") String token);


    @POST("auth/register")
    @FormUrlEncoded
    Call<User> registration(@Field("email") String email,
                            @Field("password") String password);

    @POST("reservations/extend")
    @FormUrlEncoded
    Call<ResponseBody> extendReservation(@Header("Authorization") String token,
                                         @Field("new_return_date") String return_date,
                                         @Field("item_id") String item_id,
                                         @Field("reason") String reason);

    @POST("reservations/delete/{id}")
    Call<ResponseBody> cancelReservation(@Header("Authorization") String token,
                                         @Path("id") Integer id);

}
