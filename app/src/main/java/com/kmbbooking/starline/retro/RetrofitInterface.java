package com.kmbbooking.starline.retro;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface RetrofitInterface {

    @GET
    Call<JsonObject> sendOTP(@Url String url);

    @GET("application_api/get_starline_rates.php")
    Call<JsonObject> getStarRates();

    @GET("application_api/get_slider.php")
    Call<JsonObject> getSlider();

    @GET("application_api/get_notice.php")
    Call<JsonObject> getNotice();

    @GET("application_api/get_rates.php")
    Call<JsonObject> getRates();

    @POST("application_api/signup.php")
    Call<JsonObject> signUp(@Query("user_name") String user_name, @Query("user_phone") String user_phone,
                            @Query("user_password") String user_password, @Query("user_email") String user_email,
                            @Query("user_mpin") String user_mpin, @Query("token_id") String token_id);

    @POST("application_api/edit_profile.php")
    Call<JsonObject> updateProfile(@Query("phone_number") String phone_number, @Query("phonpe") String phonpe,
                                   @Query("gpay") String gpay, @Query("paytm") String paytm);

    @POST("application_api/login.php")
    Call<JsonObject> login(@Query("phone_number") String phone_number, @Query("password") String password, @Query("token_id")
            String token_id);

    @POST("application_api/set_enquiry.php")
    Call<JsonObject> setEnquiry(@Query("phone") String phone, @Query("name") String name, @Query("enquiry")
            String enquiry);

    @POST("application_api/forgot_password.php")
    Call<JsonObject> forgetPass(@Query("phone_number") String phone_number);

    @POST("application_api/get_request_history.php")
    Call<JsonObject> withReqHustory(@Query("phone_number") String phone_number);

    @POST("application_api/update_password.php")
    Call<JsonObject> forgetUpdatePass(@Query("phone_number") String phone_number, @Query("new_password") String new_password);

    @POST("application_api/change_password.php")
    Call<JsonObject> changePass(@Query("old_password") String old_password, @Query("new_password") String new_password,
                                @Query("phone_number") String phone_number);

    @POST("application_api/get_star_chart.php")
    Call<JsonObject> GET_CHART_STAR();

    @POST("application_api/get_games.php")
    Call<JsonObject> GET_GAMES();

    @POST("application_api/get_starline_games.php")
    Call<JsonObject> GET_GAME_STAR();

    @POST("application_api/get_game_chart.php")
    Call<JsonObject> GET_GAME_CHART();

    @POST("application_api/get_win_report.php")
    Call<JsonObject> getWinReport(@Query("phone_number") String phone_number, @Query("date1") String date1, @Query("date2")
            String date2);

    @POST("application_api/get_profile.php")
    Call<JsonObject> getProfile(@Query("phone_number") String phone_number);

    @POST("application_api/get_wallet.php")
    Call<JsonObject> getWallet(@Query("phone_number") String phone_number);

    @POST("application_api/get_wallet_history.php")
    Call<JsonObject> getWalletHist(@Query("phone_number") String phone_number, @Query("date1") String date1, @Query("date2")
            String date2);

    @POST("application_api/get_bid_history.php")
    Call<JsonObject> getBidReport(@Query("phone_number") String phone_number, @Query("date1") String date1, @Query("date2")
            String date2);

    @POST("application_api/get_starline_bid_history.php")
    Call<JsonObject> getBidStarReport(@Query("phone_number") String phone_number, @Query("date1") String date1, @Query("date2")
            String date2);

    @POST("application_api/get_starline_winning.php")
    Call<JsonObject> getWinStarReport(@Query("phone_number") String phone_number);

    @POST("application_api/fund-transfer.php")
    Call<JsonObject> setTransfer(@Query("sender") String sender, @Query("amount") String amount, @Query("receiver")
            String receiver);

    @POST("application_api/add_amount.php")
    Call<JsonObject> addAmount(@Query("phone_number") String phone_number, @Query("amount") String amount, @Query("trans_detail")
            String trans_detail);


    @POST("application_api/withdraw_request.php")
    Call<JsonObject> Withdraw(@Query("phone_number") String phone_number, @Query("amount") String amount, @Query("remark")
            String remark);

    @FormUrlEncoded
    @POST("application_api/set_bid.php")
    Call<JsonObject> newBid(@Field("bids") String bids);

    @FormUrlEncoded
    @POST("application_api/set_starline_bid.php")
    Call<JsonObject> newStarBid(@Field("bids") String bids);

    @FormUrlEncoded
    @POST("application_api/set_sangam_bid.php")
    Call<JsonObject> newStarBidSANGAM(@Field("bids") String bid);
}


