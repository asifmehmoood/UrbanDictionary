package com.asif.urbandictionary.network;


import com.asif.urbandictionary.module.JSONResponse;
import com.asif.urbandictionary.utils.AppConstants;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Url;

public interface APIService {

    @Headers("x-rapidapi-key: "+ AppConstants.API_KEY +"")
    @GET
    Observable<JSONResponse> getWordDefinition(@Url String url);
}
