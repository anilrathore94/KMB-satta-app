package com.kmbbooking.starline.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kmbbooking.starline.R;
import com.kmbbooking.starline.retro.ApiUtils;
import com.kmbbooking.starline.retro.RetrofitInterface;
import com.kmbbooking.starline.utils.Saurya;
import com.kmbbooking.starline.utils.SharedPrefData;
import com.google.gson.JsonObject;


import org.json.JSONException;
import org.json.JSONObject;

import dev.shreyaspatil.easyupipayment.EasyUpiPayment;
import dev.shreyaspatil.easyupipayment.listener.PaymentStatusListener;
import dev.shreyaspatil.easyupipayment.model.PaymentApp;
import dev.shreyaspatil.easyupipayment.model.TransactionDetails;
import retrofit2.Call;
import retrofit2.Callback;


public class Deposit extends AppCompatActivity implements PaymentStatusListener {
    private RadioGroup radioAppChoice;
    String payeeAddress;
    String payeeName;
    EditText oldPassword;
    TextView changepassword,phNo2, less, one, two, five, ten;
    ImageView llWp;
    String description = "add fund";

    private EasyUpiPayment easyUpiPayment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);
        llWp= (ImageView) findViewById(R.id.llWp);
        oldPassword = (EditText) findViewById(R.id.oldPassword);
        changepassword = (TextView) findViewById(R.id.changepassword);
        phNo2= (TextView) findViewById(R.id.phNo2);
        less = (TextView) findViewById(R.id.less);
        one = (TextView) findViewById(R.id.one);
        two = (TextView) findViewById(R.id.two);
        five = (TextView) findViewById(R.id.five);
        ten = (TextView) findViewById(R.id.ten);
        radioAppChoice = findViewById(R.id.radioAppChoice);
        less.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldPassword.setText("500");
            }
        });

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldPassword.setText("1000");
            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldPassword.setText("2000");
            }
        });

        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldPassword.setText("5000");
            }
        });

        ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldPassword.setText("10000");
            }
        });
        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                oldPassword.setError(null);
                String oldPwd = oldPassword.getText().toString().trim();
                View focusView = null;

                if (TextUtils.isEmpty(oldPwd)) {
                    oldPassword.setError("Required !");
                    focusView = oldPassword;
                    focusView.requestFocus();
                    return;
                }

                addFund(oldPwd);
            }
        });
        llWp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String contact = "+91 " + Saurya.ReadStringPreferences(SharedPrefData.PREF_WP); // use country code with your phone number
                String url = "https://api.whatsapp.com/send?phone=" + contact;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });



        phNo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Saurya.ReadStringPreferences(SharedPrefData.PREF_CALL)));
                startActivity(callIntent);
            }
        });
        getWallet();

    }



    private void getWallet() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.WHITE));
        dialog.setContentView(R.layout.layout_progress);
        dialog.setCancelable(false);

        RetrofitInterface mAPIService;
        mAPIService = ApiUtils.getAPIService();
        mAPIService.getWallet(Saurya.ReadStringPreferences(SharedPrefData.PREF_LOGINPHONE)).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response1) {
                dialog.dismiss();


                Log.d("Response", String.valueOf(response1));
                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response1.body()));


                    JSONObject json = jsonObject.getJSONObject("data");
                    Saurya.writeStringPreference(SharedPrefData.PREF_WP, json.optString("admin_wp"));

                    phNo2.setText("+91-" + Saurya.ReadStringPreferences(SharedPrefData.PREF_WP));

                    Saurya.writeStringPreference(SharedPrefData.PREF_MIN_DEPOSITE, json.optString("min_deposite"));
                    Saurya.writeStringPreference(SharedPrefData.PREF_MAX_DEPOSITE, json.optString("max_deposite"));

                    Saurya.writeStringPreference(SharedPrefData.PREF_UPI_PP, json.optString("upi_phone"));
                    Saurya.writeStringPreference(SharedPrefData.PREF_UPI_GP, json.optString("upi_google"));
                    Saurya.writeStringPreference(SharedPrefData.PREF_UPI_ID, json.optString("upi_payment_id"));

                    Saurya.writeStringPreference(SharedPrefData.PREF_UPI_NAME, json.optString("upi_name"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                dialog.dismiss();
            }


        });
        dialog.show();
    }

    private void addFund(String amount) {

        if (Integer.valueOf(amount) < Integer.parseInt(Saurya.ReadStringPreferences(SharedPrefData.PREF_MIN_DEPOSITE))) {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(Deposit.this);
            // Setting Dialog Title
            alertDialog.setTitle("Info");

            // Setting Dialog Message
            alertDialog.setMessage(Html.fromHtml("Amount shuld be greater then and equal to RS "+Saurya.ReadIntPreferences(SharedPrefData.PREF_MIN_DEPOSITE)+"/-"));

            // Setting Positive "Yes" Button
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    dialog.cancel();

                }
            });

            // Showing Alert Message
            alertDialog.show();

        } else {

            payeeAddress = Saurya.ReadStringPreferences(SharedPrefData.PREF_UPI_ID);
            payeeName = Saurya.ReadStringPreferences(SharedPrefData.PREF_UPI_NAME);


            payUsingUpi(amount, payeeAddress, payeeName, "Add Points to " + Deposit.this.getString(R.string.app_name));


        }
    }

    private void payUsingUpi(String amosunt, String upiId, String name, String note) {
        String payeeVpa = upiId;
        String payeeName = name;
        String transactionId = "TID" + System.currentTimeMillis();

        String transactionRefId = transactionId+"_"+ System.currentTimeMillis();

        String amount = amosunt+".00";
        RadioButton paymentAppChoice = findViewById(radioAppChoice.getCheckedRadioButtonId());

        PaymentApp paymentApp;

        switch (paymentAppChoice.getId()) {
            case R.id.app_default:
                payeeVpa =  Saurya.ReadStringPreferences(SharedPrefData.PREF_UPI_ID);
                paymentApp = PaymentApp.ALL;
                description = "OTHER";
                break;
            case R.id.app_google_pay:
                payeeVpa =  Saurya.ReadStringPreferences(SharedPrefData.PREF_UPI_GP);
                paymentApp = PaymentApp.GOOGLE_PAY;
                description = "GOOGLE_PAY";
                break;
            case R.id.app_phonepe:
                payeeVpa =  Saurya.ReadStringPreferences(SharedPrefData.PREF_UPI_PP);
                paymentApp = PaymentApp.PHONE_PE;
                description = "PHONE_PE";
                break;
            case R.id.app_paytm:
                payeeVpa =  Saurya.ReadStringPreferences(SharedPrefData.PREF_UPI_ID);
                paymentApp = PaymentApp.PAYTM;
                description = "OTHER";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + paymentAppChoice.getId());
        }


        // START PAYMENT INITIALIZATION
        EasyUpiPayment.Builder builder = new EasyUpiPayment.Builder(this)
                .with(paymentApp)
                .setPayeeVpa(payeeVpa)
                .setPayeeName(Deposit.this.getString(R.string.app_name))
                .setTransactionId(transactionId)
                .setTransactionRefId(transactionRefId)
                .setPayeeMerchantCode("")
                .setDescription(description)
                .setAmount(amount);
        // END INITIALIZATION

        try {
            // Build instance
            easyUpiPayment = builder.build();

            // Register Listener for Events
            easyUpiPayment.setPaymentStatusListener(this);

            // Start payment / transaction
            easyUpiPayment.startPayment();
        } catch (Exception exception) {
            exception.printStackTrace();
            toast("Error: " + exception.getMessage());
        }


    }



    private void saveOnServer(final String details) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.WHITE));
        dialog.setContentView(R.layout.layout_progress);
        dialog.setCancelable(false);

        RetrofitInterface mAPIService;
        mAPIService = ApiUtils.getAPIService();
        mAPIService.addAmount(Saurya.ReadStringPreferences(SharedPrefData.PREF_LOGINPHONE),
                oldPassword.getText().toString(), description).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                    if (jsonObject.optString("success").equals("1")) {

                        Toast.makeText(Deposit.this, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
                        finish();

                    } else {
                        Toast.makeText(Deposit.this, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                dialog.dismiss();
            }
        });
        dialog.show();





    }


    @Override
    public void onBackPressed() {
        finish();

    }

    @Override
    public void onTransactionCompleted(TransactionDetails transactionDetails) {
        // Transaction Completed
        Log.d("TransactionDetails", transactionDetails.toString());

        switch (transactionDetails.getTransactionStatus()) {
            case SUCCESS:
                saveOnServer(transactionDetails.getTransactionId());
                break;
            case FAILURE:
                onTransactionFailed();
                break;
            case SUBMITTED:
                onTransactionSubmitted();
                break;
        }
    }

    @Override
    public void onTransactionCancelled() {
        // Payment Cancelled by User
        toast("Cancelled by user");
    }

    private void onTransactionSuccess() {
        // Payment Success
        toast("Success");
    }

    private void onTransactionSubmitted() {
        // Payment Pending
        toast("Pending | Submitted");
    }

    private void onTransactionFailed() {
        // Payment Failed
        toast("Failed");
    }

    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}