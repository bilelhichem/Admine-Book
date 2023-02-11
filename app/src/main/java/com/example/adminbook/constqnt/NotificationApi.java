package com.example.adminbook.constqnt;

import com.example.adminbook.Model.PushNotification;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface NotificationApi {
    @Headers({
            "Authorization: key="+constant.SERVER_KEY,
            "Content-Type:"+constant.CONETNT_TYPE
    })
    @POST("fcm/send")
    Call<ResponseBody> postNotification(@Body PushNotification data);
}
