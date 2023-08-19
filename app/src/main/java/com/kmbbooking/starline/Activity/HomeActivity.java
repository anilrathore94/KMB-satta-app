package com.kmbbooking.starline.Activity;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.kmbbooking.starline.R;
import com.kmbbooking.starline.Starline.HomeStarLineActivity;
import com.kmbbooking.starline.adapters.GameDataAdapter;
import com.kmbbooking.starline.model.GameData;
import com.kmbbooking.starline.retro.ApiUtils;
import com.kmbbooking.starline.retro.RetrofitInterface;
import com.kmbbooking.starline.utils.Cofig;
import com.kmbbooking.starline.utils.Saurya;
import com.kmbbooking.starline.utils.SharedPrefData;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class HomeActivity extends AppCompatActivity {

    private final DrawerLayout.DrawerListener mDrawerListener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerStateChanged(int status) {
        }

        @Override
        public void onDrawerSlide(View view, float slideArg) {
        }

        @Override
        public void onDrawerOpened(View view) {
        }

        @Override
        public void onDrawerClosed(View view) {
        }
    };
    LinearLayout ivWallet, llWp;
    RecyclerView recyclerView;
    List<GameData> list = new ArrayList<>();
    TextView tvWallet;
    TextView tvNameNew;
    TextView phNo2;
    TextView tvName, tvPh;
    String tvUrl;

    String walletDate = "show";
    String bankData = "show";
    Toolbar toolbar;
    ViewFlipper flipper;
    LinearLayout llHome, llProfile,  llAddFund, llWithdraw, llStatemant, llTransfer,
            llPaytm, llPhonePe, llGoogle, winhistory, bidhistory, gamerate, llPlayVideo, shareApp,
            rateApp, changepassword, enquiry, logut, notice;


    LinearLayout llwalletNew;
    LinearLayout llOtheres, llShowNew;
    LinearLayout llStarLineSelect;
    LinearLayout drawerLayoutLiner;
    private SwipeRefreshLayout swipeContainer;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    View view1,view2,view3;


    LinearLayout llWalletDetails,llBankRecords, llAcDetails, llWDetails;
    ImageView ivWalletArrow, ivBankArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view1 = findViewById(R.id.view1);
        view2 = findViewById(R.id.view2);
        view3 = findViewById(R.id.view3);

        llwalletNew = findViewById(R.id.llwalletNew);
        llShowNew = findViewById(R.id.llShowNew);
        llOtheres = findViewById(R.id.llOtheres);
        //llShowData.setVisibility(View.GONE);
        llShowNew.setVisibility(View.GONE);
        llWp = findViewById(R.id.llWp);

        phNo2 = findViewById(R.id.phNo2);
        flipper = findViewById(R.id.flipper);

        flipper.setAutoStart(true);
        flipper.setFlipInterval(5000);
        llStarLineSelect = findViewById(R.id.llStarLineSelect);


        tvWallet = findViewById(R.id.tvWallet);
        if (!checkIfAlreadyhavePermission()) {
            requestForSpecificPermission();
        }



        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ivWallet = findViewById(R.id.ivWallet);


        llwalletNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Saurya.ReadStringPreferences(SharedPrefData.PREF_STATUS).equalsIgnoreCase("0")) {

                    return;
                }

                Intent intent8 = new Intent(HomeActivity.this, Deposit.class);
                startActivity(intent8);
                overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
            }
        });

//        llWithdraw.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (Saurya.ReadStringPreferences(SharedPrefData.PREF_STATUS).equalsIgnoreCase("0")) {
//
//                    return;
//                }
//
//                Intent intent8 = new Intent(HomeActivity.this, WithdrawActivity.class);
//                startActivity(intent8);
//                overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
//            }
//        });


        ivWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Saurya.ReadStringPreferences(SharedPrefData.PREF_STATUS).equalsIgnoreCase("0")) {

                    return;
                }

                Intent intent8 = new Intent(HomeActivity.this, Wallet.class);
                startActivity(intent8);
                overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
            }
        });


        llStarLineSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Saurya.ReadStringPreferences(SharedPrefData.PREF_STATUS).equalsIgnoreCase("0")) {

                    return;
                }


                Intent intent2 = new Intent(HomeActivity.this, HomeStarLineActivity.class);
                startActivity(intent2);
                overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
            }
        });


        recyclerView = findViewById(R.id.recyclerview);

        getWallet();
        Regist();
        getData();


        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getWallet();
                Regist();
                getData();

                setUpDrawer();

                mDrawerToggle = new ActionBarDrawerToggle(HomeActivity.this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                mDrawerToggle.syncState();
                mDrawerLayout.setDrawerListener(mDrawerToggle);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mDrawerToggle.getDrawerArrowDrawable().setColor(getColor(R.color.white));
                } else {
                    mDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
                }

                getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                    @Override
                    public void onBackStackChanged() {
                        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // show back button
                            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mDrawerLayout.openDrawer(GravityCompat.START);
                                }
                            });
                        } else {
                            //show hamburger
                            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                            mDrawerToggle.syncState();
                            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mDrawerLayout.openDrawer(GravityCompat.START);
                                }
                            });
                        }
                    }
                });


                if (Saurya.ReadStringPreferences(SharedPrefData.PREF_STATUS).equalsIgnoreCase("1")) {
                    llOtheres.setVisibility(View.VISIBLE);
                    view1.setVisibility(View.VISIBLE);
                    view2.setVisibility(View.VISIBLE);
                    view3.setVisibility(View.VISIBLE);

                    walletDate = "show";
                    llWalletDetails.setVisibility(View.VISIBLE);
                    bankData = "show";
                    llBankRecords.setVisibility(View.VISIBLE);
                    llProfile.setVisibility(View.VISIBLE);
                    llPlayVideo.setVisibility(View.VISIBLE);
                    notice.setVisibility(View.GONE);
                    bidhistory.setVisibility(View.VISIBLE);
                    shareApp.setVisibility(View.VISIBLE);
                    winhistory.setVisibility(View.VISIBLE);
                    llWDetails.setVisibility(View.VISIBLE);
                    llAcDetails.setVisibility(View.VISIBLE);
                    gamerate.setVisibility(View.VISIBLE);
                    enquiry.setVisibility(View.GONE);
                    ivWallet.setVisibility(View.VISIBLE);
                    tvNameNew.setText("Starline");
                    llShowNew.setVisibility(View.VISIBLE);


                } else {

                    walletDate = "hide";
                    llWalletDetails.setVisibility(View.GONE);
                    bankData = "hide";
                    llBankRecords.setVisibility(View.GONE);
                    llProfile.setVisibility(View.GONE);
                    llPlayVideo.setVisibility(View.GONE);
                    notice.setVisibility(View.GONE);
                    bidhistory.setVisibility(View.GONE);
                    winhistory.setVisibility(View.GONE);
                    llWDetails.setVisibility(View.GONE);
                    llAcDetails.setVisibility(View.GONE);
                    gamerate.setVisibility(View.GONE);
                    enquiry.setVisibility(View.GONE);
                    ivWallet.setVisibility(View.GONE);
                    shareApp.setVisibility(View.GONE);
                    tvNameNew.setText("Hi, " + Saurya.ReadStringPreferences(SharedPrefData.PREF_LOGINNAME));
                    llShowNew.setVisibility(View.GONE);

                    llOtheres.setVisibility(View.GONE);
                    view1.setVisibility(View.GONE);
                    view2.setVisibility(View.GONE);
                    view3.setVisibility(View.GONE);
                }
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


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


        setUpDrawer();

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mDrawerToggle.getDrawerArrowDrawable().setColor(getColor(R.color.white));
        } else {
            mDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        }

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true); // show back button
                    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mDrawerLayout.openDrawer(GravityCompat.START);
                        }
                    });
                } else {
                    //show hamburger
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    mDrawerToggle.syncState();
                    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mDrawerLayout.openDrawer(GravityCompat.START);
                        }
                    });
                }
            }
        });


//
        tvPh = findViewById(R.id.tvPh);
        tvName = findViewById(R.id.tvName);
        tvNameNew = findViewById(R.id.tvNameNew);
        tvPh.setText(Saurya.ReadStringPreferences(SharedPrefData.PREF_LOGINPHONE));
        tvName.setText(Saurya.ReadStringPreferences(SharedPrefData.PREF_LOGINNAME));

        notice = findViewById(R.id.notice);
        llAcDetails = findViewById(R.id.llAcDetails);
        llWDetails = findViewById(R.id.llWDetails);

        llHome = findViewById(R.id.llHome);
        llProfile = findViewById(R.id.llProfile);
        llWalletDetails = findViewById(R.id.llWalletDetails);
        llAddFund = findViewById(R.id.llAddFund);

        llWithdraw = findViewById(R.id.llWithdraw);
        llStatemant = findViewById(R.id.llStatemant);
        llTransfer = findViewById(R.id.llTransfer);
        llBankRecords = findViewById(R.id.llBankRecords);

        llPaytm = findViewById(R.id.llPaytm);
        llPhonePe = findViewById(R.id.llPhonePe);
        llGoogle = findViewById(R.id.llGoogle);

        winhistory = findViewById(R.id.winhistory);
        bidhistory = findViewById(R.id.bidhistory);
        gamerate = findViewById(R.id.gamerate);
        llPlayVideo = findViewById(R.id.llPlayVideo);
        shareApp = findViewById(R.id.shareApp);
        rateApp = findViewById(R.id.rateApp);
        changepassword = findViewById(R.id.changepassword);
        enquiry = findViewById(R.id.enquiry);
        logut = findViewById(R.id.logut);

        ivWalletArrow = findViewById(R.id.ivWalletArrow);
        ivBankArrow = findViewById(R.id.ivBankArrow);

        llBankRecords.setVisibility(View.VISIBLE);
        llWalletDetails.setVisibility(View.VISIBLE);
        walletDate = "show";
        bankData = "show";


        enquiry.setVisibility(View.GONE);
        rateApp.setVisibility(View.GONE);


        walletDate = "hide";
        ivWalletArrow.setRotation(0);
        llWalletDetails.setVisibility(View.GONE);


        bankData = "hide";
        ivBankArrow.setRotation(0);
        llBankRecords.setVisibility(View.GONE);

        llWDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Saurya.ReadStringPreferences(SharedPrefData.PREF_STATUS).equalsIgnoreCase("0")) {

                    return;
                }


                if (walletDate.equalsIgnoreCase("hide")) {
                    walletDate = "show";
                    ivWalletArrow.setRotation(180);
                    llWalletDetails.setVisibility(View.VISIBLE);
                } else {
                    walletDate = "hide";
                    ivWalletArrow.setRotation(0);
                    llWalletDetails.setVisibility(View.GONE);
                }
            }
        });

        llAcDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Saurya.ReadStringPreferences(SharedPrefData.PREF_STATUS).equalsIgnoreCase("0")) {

                    return;
                }

                if (bankData.equalsIgnoreCase("hide")) {
                    bankData = "show";
                    ivBankArrow.setRotation(180);
                    llBankRecords.setVisibility(View.VISIBLE);
                } else {
                    bankData = "hide";
                    ivBankArrow.setRotation(0);
                    llBankRecords.setVisibility(View.GONE);
                }
            }
        });


        if (Saurya.ReadStringPreferences(SharedPrefData.PREF_STATUS).equalsIgnoreCase("1")) {
            llOtheres.setVisibility(View.VISIBLE);
            view1.setVisibility(View.VISIBLE);
            view2.setVisibility(View.VISIBLE);
            view3.setVisibility(View.VISIBLE);

            walletDate = "show";
            llWalletDetails.setVisibility(View.VISIBLE);
            bankData = "show";
            llBankRecords.setVisibility(View.VISIBLE);
            llProfile.setVisibility(View.VISIBLE);
            llPlayVideo.setVisibility(View.VISIBLE);
            notice.setVisibility(View.GONE);
            bidhistory.setVisibility(View.VISIBLE);
            shareApp.setVisibility(View.VISIBLE);
            winhistory.setVisibility(View.VISIBLE);
            llWDetails.setVisibility(View.VISIBLE);
            llAcDetails.setVisibility(View.VISIBLE);
            gamerate.setVisibility(View.VISIBLE);
            enquiry.setVisibility(View.GONE);
            ivWallet.setVisibility(View.VISIBLE);
            tvNameNew.setText("Starline");
            llShowNew.setVisibility(View.VISIBLE);





        } else {

            walletDate = "hide";
            llWalletDetails.setVisibility(View.GONE);
            bankData = "hide";
            llBankRecords.setVisibility(View.GONE);
            llProfile.setVisibility(View.GONE);
            llPlayVideo.setVisibility(View.GONE);
            notice.setVisibility(View.GONE);
            bidhistory.setVisibility(View.GONE);
            winhistory.setVisibility(View.GONE);
            llWDetails.setVisibility(View.GONE);
            llAcDetails.setVisibility(View.GONE);
            gamerate.setVisibility(View.GONE);
            enquiry.setVisibility(View.GONE);
            ivWallet.setVisibility(View.GONE);
            shareApp.setVisibility(View.GONE);
            tvNameNew.setText("Hi, "+Saurya.ReadStringPreferences(SharedPrefData.PREF_LOGINNAME));
            llShowNew.setVisibility(View.GONE);

            llOtheres.setVisibility(View.GONE);
            view1.setVisibility(View.GONE);
            view2.setVisibility(View.GONE);
            view3.setVisibility(View.GONE);
        }


        llHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawer(drawerLayoutLiner);
            }
        });


        llProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Saurya.ReadStringPreferences(SharedPrefData.PREF_STATUS).equalsIgnoreCase("0")) {

                    return;
                }
                mDrawerLayout.closeDrawer(drawerLayoutLiner);

                Intent intent2 = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent2);
                overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
            }
        });


        llAddFund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Saurya.ReadStringPreferences(SharedPrefData.PREF_STATUS).equalsIgnoreCase("0")) {

                    return;
                }
                mDrawerLayout.closeDrawer(drawerLayoutLiner);

                Intent intent2 = new Intent(HomeActivity.this, Deposit.class);
                startActivity(intent2);
                overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
            }
        });


        llWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Saurya.ReadStringPreferences(SharedPrefData.PREF_STATUS).equalsIgnoreCase("0")) {

                    return;
                }
                mDrawerLayout.closeDrawer(drawerLayoutLiner);

                Intent intent2 = new Intent(HomeActivity.this, WithdrawActivity.class);
                startActivity(intent2);
                overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
            }
        });


        llStatemant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Saurya.ReadStringPreferences(SharedPrefData.PREF_STATUS).equalsIgnoreCase("0")) {

                    return;
                }
                mDrawerLayout.closeDrawer(drawerLayoutLiner);

                Intent intent2 = new Intent(HomeActivity.this, WalletHistory.class);
                startActivity(intent2);
                overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
            }
        });

        llTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Saurya.ReadStringPreferences(SharedPrefData.PREF_STATUS).equalsIgnoreCase("0")) {

                    return;
                }
                mDrawerLayout.closeDrawer(drawerLayoutLiner);

                Intent intent2 = new Intent(HomeActivity.this, FundTransferActivity.class);
                startActivity(intent2);
                overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
            }
        });

        gamerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Saurya.ReadStringPreferences(SharedPrefData.PREF_STATUS).equalsIgnoreCase("0")) {

                    return;
                }
                mDrawerLayout.closeDrawer(drawerLayoutLiner);

                Intent intent2 = new Intent(HomeActivity.this, GameRates.class);
                startActivity(intent2);
                overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
            }
        });


        llPaytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Saurya.ReadStringPreferences(SharedPrefData.PREF_STATUS).equalsIgnoreCase("0")) {

                    return;
                }
                mDrawerLayout.closeDrawer(drawerLayoutLiner);

                Intent intent2 = new Intent(HomeActivity.this, PaytmActivity.class);
                startActivity(intent2);
                overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
            }
        });

        llPhonePe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Saurya.ReadStringPreferences(SharedPrefData.PREF_STATUS).equalsIgnoreCase("0")) {

                    return;
                }
                mDrawerLayout.closeDrawer(drawerLayoutLiner);

                Intent intent2 = new Intent(HomeActivity.this, PhonePayActivity.class);
                startActivity(intent2);
                overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
            }
        });

        llGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Saurya.ReadStringPreferences(SharedPrefData.PREF_STATUS).equalsIgnoreCase("0")) {

                    return;
                }
                mDrawerLayout.closeDrawer(drawerLayoutLiner);

                Intent intent2 = new Intent(HomeActivity.this, GooglePayActivity.class);
                startActivity(intent2);
                overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
            }
        });


        winhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Saurya.ReadStringPreferences(SharedPrefData.PREF_STATUS).equalsIgnoreCase("0")) {

                    return;
                }
                mDrawerLayout.closeDrawer(drawerLayoutLiner);

                Intent intent2 = new Intent(HomeActivity.this, WinHistory.class);
                startActivity(intent2);
                overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
            }
        });

        bidhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Saurya.ReadStringPreferences(SharedPrefData.PREF_STATUS).equalsIgnoreCase("0")) {

                    return;
                }
                mDrawerLayout.closeDrawer(drawerLayoutLiner);

                Intent intent2 = new Intent(HomeActivity.this, BidHistory.class);
                startActivity(intent2);
                overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
            }
        });


        logut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog =
                        new AlertDialog.Builder(HomeActivity.this);
                alertDialog.setTitle("Logout");
                alertDialog.setMessage("Are You Want to Logout From " + HomeActivity.this.getString(R.string.app_name) + " App?");
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Saurya.logout_clean_data();
                        Saurya.ClearPriferences();

                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(i);
                        finish();
                        dialog.cancel();

                    }
                });
                alertDialog.show();
            }
        });

        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDrawerLayout.closeDrawer(drawerLayoutLiner);

                Intent intent2 = new Intent(HomeActivity.this, ChangePassword.class);
                startActivity(intent2);
                overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
            }
        });

        enquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDrawerLayout.closeDrawer(drawerLayoutLiner);

                Intent intent2 = new Intent(HomeActivity.this, EnqueryActivity.class);
                startActivity(intent2);
                overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
            }
        });


        llPlayVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Saurya.ReadStringPreferences(SharedPrefData.PREF_STATUS).equalsIgnoreCase("0")) {

                    return;
                }
                mDrawerLayout.closeDrawer(drawerLayoutLiner);

                if (tvUrl.equalsIgnoreCase("https://www.youtube.com/")) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeActivity.this);
                    alertDialog.setTitle("Info");
                    alertDialog.setMessage("Invalid URL.");
                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();

                        }
                    });

                    alertDialog.show();
                } else {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(tvUrl)));
                }
            }
        });


        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Saurya.ReadStringPreferences(SharedPrefData.PREF_STATUS).equalsIgnoreCase("0")) {

                    return;
                }
                mDrawerLayout.closeDrawer(drawerLayoutLiner);

                Intent intent2 = new Intent(HomeActivity.this, NoticeActivity.class);
                startActivity(intent2);
                overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
            }
        });

        bidhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Saurya.ReadStringPreferences(SharedPrefData.PREF_STATUS).equalsIgnoreCase("0")) {

                    return;
                }
                mDrawerLayout.closeDrawer(drawerLayoutLiner);

                Intent intent2 = new Intent(HomeActivity.this, BidHistory.class);
                startActivity(intent2);
                overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
            }
        });


        shareApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawer(drawerLayoutLiner);

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, HomeActivity.this.getString(R.string.app_name) + " GAME");

                shareIntent.putExtra(Intent.EXTRA_TEXT, Saurya.ReadStringPreferences(SharedPrefData.PREF_SHARE));
                startActivity(Intent.createChooser(shareIntent, "Share via"));

            }
        });

        rateApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawer(drawerLayoutLiner);
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + Saurya.ReadStringPreferences(SharedPrefData.PREF_RATE))));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + Saurya.ReadStringPreferences(SharedPrefData.PREF_RATE))));
                }


            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
    }


    private void setUpDrawer() {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerLayout.setScrimColor(getResources().getColor(android.R.color.transparent));
        mDrawerLayout.setDrawerListener(mDrawerListener);
        drawerLayoutLiner = findViewById(R.id.drawerLayoutLiner);
        mDrawerLayout.closeDrawer(drawerLayoutLiner);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    private void getData() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.setContentView(R.layout.layout_progress);
        dialog.setCancelable(false);

        RetrofitInterface mAPIService;
        mAPIService = ApiUtils.getAPIService();

        mAPIService.getSlider().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                 dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                    JSONArray array = jsonObject.getJSONArray("result");
                    for (int i = 0; i < array.length(); i++) {
                        // create dynamic image view and add them to ViewFlipper
                        String ur = Cofig.IMAGE_URL + array.getJSONObject(i).getString("image");
                        ImageView image = new ImageView(getApplicationContext());
                        image.setScaleType(ImageView.ScaleType.FIT_XY);
                        Picasso.get().load(ur).into(image);
                        flipper.addView(image);
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

    private void Regist() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.WHITE));
        dialog.setContentView(R.layout.layout_progress);
        dialog.setCancelable(false);


        RetrofitInterface mAPIService;
        mAPIService = ApiUtils.getAPIService();

        mAPIService.GET_GAMES().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                 dialog.dismiss();
                swipeContainer.setRefreshing(false);
                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));

                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    list = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        GameData gameData = new GameData();
                        gameData.setGameName(jsonObject1.optString("games_name"));
                        gameData.setOpenTime(jsonObject1.optString("open_time"));
                        gameData.setCloseTime(jsonObject1.optString("close_time"));
                        gameData.setOpenDigit(jsonObject1.optString("open_digit"));
                        gameData.setCloseDigit(jsonObject1.optString("close_digit"));
                        gameData.setOpenPana(jsonObject1.optString("open_pana"));
                        gameData.setClosePana(jsonObject1.optString("close_pana"));
                        gameData.setGameStatus(jsonObject1.optString("game_status"));
                        gameData.setMarketStatus(jsonObject1.optString("market_status"));

                        list.add(gameData);
                    }

                    GameDataAdapter gameDataAdapter = new GameDataAdapter(list, HomeActivity.this);
                    recyclerView.setAdapter(gameDataAdapter);

                    recyclerView.setLayoutManager(new GridLayoutManager(HomeActivity.this, 2));

                     dialog.dismiss();


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

                    Saurya.writeStringPreference(SharedPrefData.PREF_STATUS, json.optString("status"));
                    Saurya.writeStringPreference(SharedPrefData.PREF_WALLET, json.optString("wallet"));
                    Saurya.writeStringPreference(SharedPrefData.PREF_CALL, json.optString("admin_mobile"));
                    Saurya.writeStringPreference(SharedPrefData.PREF_WP, json.optString("admin_wp"));

                    phNo2.setText("+91-" + Saurya.ReadStringPreferences(SharedPrefData.PREF_WP));
                    Saurya.writeStringPreference(SharedPrefData.PREF_MIN_DEPOSITE, json.optString("min_deposite"));

                    Saurya.writeStringPreference(SharedPrefData.PREF_START_TIME, json.optString("start_time"));
                    Saurya.writeStringPreference(SharedPrefData.URL, json.optString("how_to_play"));
                    Saurya.writeStringPreference(SharedPrefData.MPIN, json.optString("m_pin"));

                    Saurya.writeStringPreference(SharedPrefData.PREF_RATE, json.optString("share_url"));
                    Saurya.writeStringPreference(SharedPrefData.PREF_SHARE, json.optString("share_message"));

                    Saurya.writeStringPreference(SharedPrefData.WITH_OPEN, json.optString("min_withdraw"));
                    Saurya.writeStringPreference(SharedPrefData.WITH_CLOSE, json.optString("max_withdraw"));


                    Saurya.writeStringPreference(SharedPrefData.PREF_MIN_DEPOSITE, json.optString("min_deposite"));
                    Saurya.writeStringPreference(SharedPrefData.PREF_MAX_DEPOSITE, json.optString("max_deposite"));

                    Saurya.writeStringPreference(SharedPrefData.MIN_BID, json.optString("min_limit"));
                    Saurya.writeStringPreference(SharedPrefData.MAX_BID, json.optString("max_limit"));

                    if (json.optString("how_to_play").equalsIgnoreCase("")) {
                        tvUrl = "https://www.youtube.com/";
                    } else {
                        tvUrl = json.optString("how_to_play");
                    }

                    tvWallet.setText(json.optString("wallet"));

                    if (json.optString("status").equalsIgnoreCase("1")) {


                        llOtheres.setVisibility(View.VISIBLE);
                        view1.setVisibility(View.VISIBLE);
                        view2.setVisibility(View.VISIBLE);
                        view3.setVisibility(View.VISIBLE);

                        walletDate = "show";
                        llWalletDetails.setVisibility(View.VISIBLE);
                        bankData = "show";
                        llBankRecords.setVisibility(View.VISIBLE);
                        llProfile.setVisibility(View.VISIBLE);
                        llPlayVideo.setVisibility(View.VISIBLE);
                        notice.setVisibility(View.GONE);
                        bidhistory.setVisibility(View.VISIBLE);
                        shareApp.setVisibility(View.VISIBLE);
                        winhistory.setVisibility(View.VISIBLE);
                        llWDetails.setVisibility(View.VISIBLE);
                        llAcDetails.setVisibility(View.VISIBLE);
                        gamerate.setVisibility(View.VISIBLE);
                        enquiry.setVisibility(View.GONE);
                        ivWallet.setVisibility(View.VISIBLE);
                        tvNameNew.setText("Starline");
                        llShowNew.setVisibility(View.VISIBLE);





                    } else {

                        walletDate = "hide";
                        llWalletDetails.setVisibility(View.GONE);
                        bankData = "hide";
                        llBankRecords.setVisibility(View.GONE);
                        llProfile.setVisibility(View.GONE);
                        llPlayVideo.setVisibility(View.GONE);
                        notice.setVisibility(View.GONE);
                        bidhistory.setVisibility(View.GONE);
                        winhistory.setVisibility(View.GONE);
                        llWDetails.setVisibility(View.GONE);
                        llAcDetails.setVisibility(View.GONE);
                        gamerate.setVisibility(View.GONE);
                        enquiry.setVisibility(View.GONE);
                        ivWallet.setVisibility(View.GONE);
                        shareApp.setVisibility(View.GONE);
                        tvNameNew.setText("Hi, "+Saurya.ReadStringPreferences(SharedPrefData.PREF_LOGINNAME));
                        llShowNew.setVisibility(View.GONE);

                        llOtheres.setVisibility(View.GONE);
                        view1.setVisibility(View.GONE);
                        view2.setVisibility(View.GONE);
                        view3.setVisibility(View.GONE);
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




    private boolean checkIfAlreadyhavePermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestForSpecificPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.GET_TASKS, Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.CAMERA, Manifest.permission.CALL_PHONE}, 101);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder alertDialog =
                new AlertDialog.Builder(HomeActivity.this);
        alertDialog.setTitle("Close");
        alertDialog.setMessage("Are You Want to close " + HomeActivity.this.getString(R.string.app_name) + " App?");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
                finish();
                dialog.cancel();

            }
        });
        alertDialog.show();

    }
}