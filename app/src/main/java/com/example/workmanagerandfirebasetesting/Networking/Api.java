package com.example.workmanagerandfirebasetesting.Networking;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface Api {

    @GET()
    Call<Response> JustTest(
            @Url String a
    );

}
