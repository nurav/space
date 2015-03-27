package com.spitspce.space;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.gitonway.lee.niftynotification.lib.Configuration;
import com.gitonway.lee.niftynotification.lib.Effects;
import com.gitonway.lee.niftynotification.lib.NiftyNotificationView;
import com.xgc1986.parallaxPagerTransformer.ParallaxPagerTransformer;


public class ParallaxActivity extends ActionBarActivity {
    ViewPager mPager;
    HighlightsAdapter mAdapter;

    private SharedPreferences spaceapp;

    private int toast2Flag=1;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parallax);

        spaceapp = getSharedPreferences("space",0);
        toast2Flag = spaceapp.getInt("toast2Flag",1);

        if(toast2Flag!=0)
            FirstToast();

        final ActionBar abar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.title_view, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.title);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/berlin-sans-fb-demi-bold.ttf");
        textviewTitle.setTypeface(tf);
        textviewTitle.setText(this.getTitle());
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setHomeButtonEnabled(true);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setBackgroundColor(0xFF212121);

        ParallaxPagerTransformer pt = new ParallaxPagerTransformer((R.id.image));
        pt.setBorder(15);
        pt.setSpeed(0.5f);
        mPager.setPageTransformer(false, pt);

        mAdapter = new HighlightsAdapter(this, getSupportFragmentManager());
        mAdapter.setPager(mPager);

        Bundle zygnema = new Bundle();
        zygnema.putInt("image", R.drawable.zygnema);
        zygnema.putString("name", "Zygnema + High Octane");
        zygnema.putString("desc", "27th February 2015 || Evening 5pm Onwards");
        ParallaxFragment zygnema_frag = new ParallaxFragment();
        zygnema_frag.setArguments(zygnema);

        Bundle holi_reloaded= new Bundle();
        holi_reloaded.putInt("image", R.drawable.holi1);
        holi_reloaded.putString("name", "Holi Reloaded | Pronite 2015");
        holi_reloaded.putString("desc", "28th February 2015 || Evening 5pm Onwards");
        ParallaxFragment holi_frag = new ParallaxFragment();
        holi_frag.setArguments(holi_reloaded);

        Bundle daniel = new Bundle();
        daniel.putInt("image", R.drawable.daniel);
        daniel.putString("name", "Stand-Up Comedy \nby Daniel Fernandes");
        daniel.putString("desc", "1st March 2015 || Evening 5pm - 7pm");
        ParallaxFragment daniel_frag = new ParallaxFragment();
        daniel_frag.setArguments(daniel);

        mAdapter.add(zygnema_frag);
        mAdapter.add(holi_frag);
        mAdapter.add(daniel_frag);


        mPager.setAdapter(mAdapter);
    }


    private void FirstToast() {

        Configuration cfg=new Configuration.Builder()
                .setAnimDuration(1500)
                .setDispalyDuration(1500)
                .setTextPadding(5)                       //dp
                .setTextLines(2)                        //You had better use setViewHeight and setTextLines together
                .setBackgroundColor("#FFe1e1e1")
                .setIconBackgroundColor("#00000000")
                .setTextColor("#FF212121")
                .setTextGravity(Gravity.CENTER)         //only text def  Gravity.CENTER,contain icon Gravity.CENTER_VERTICAL
                .build();

        String msg = "Swipe across the screen to view next";

        final NiftyNotificationView toast = NiftyNotificationView.build(this, msg, Effects.thumbSlider, R.id.toast_layout, cfg)
                .setIcon(R.drawable.ic_launcher);
        toast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast.hide();
                toast2Flag=0;
            }
        });
        toast.show();
    }


    @Override
    protected void onPause() {
        SharedPreferences.Editor editor = spaceapp.edit();
        editor.putInt("toast2Flag",toast2Flag);
        editor.commit();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        SharedPreferences.Editor editor = spaceapp.edit();
        editor.putInt("toast2Flag", toast2Flag);
        editor.commit();
        super.onDestroy();
    }
}
