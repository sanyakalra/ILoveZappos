package com.example.sanya.challenge2;

import com.example.sanya.challenge2.POJO.Model;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by sanya on 10/2/17.
 */

public interface RestInterface {

    @GET("/Search?term=&key=b743e26728e16b81da139182bb2094357c31d331")
    void getProductDetails(Callback<Model>cb);


}
