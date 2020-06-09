package com.example.workmanagerandfirebasetesting.Networking;

import com.example.workmanagerandfirebasetesting.Model.FCMResponse;
import com.example.workmanagerandfirebasetesting.Model.RequestNotificaton;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {

    @Headers({"Authorization: key=AAAAXBLSzCw:APA91bH8MadO5DFbnpKhNJG0XVFRdWEG9cb0v6r-lZvPaQ4RWW7Gpq4Gwj7I3IgwFc2I68UmehAHxWHzwpn1V_nwYTvMexAbPY8aVPks5G8poaJrmb7Rn-1tmINNgkzGXLsPlqKJd1bE",
            "Content-Type:application/json"})
    @POST("fcm/send")
    Call<FCMResponse> sendChatNotification(@Body RequestNotificaton requestNotificaton);
}
