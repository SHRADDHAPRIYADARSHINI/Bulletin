package com.example.milkiminz.raddilo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class MasterChoose extends AppCompatActivity {

    private AdView mAdView;
    private Tracker mTracker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_choose);

        MyApplication application = (MyApplication) getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.setScreenName("ChooseScreen");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());


        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);

    }

    public void Recycling(View view) {
        startActivity(new Intent(MasterChoose.this, Choose.class));//for recycler
    }

    public void Ads(View view) {
        startActivity(new Intent(MasterChoose.this, ChooseAd.class));//for ads
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }
}
