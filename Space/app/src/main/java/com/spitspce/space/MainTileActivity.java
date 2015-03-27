package com.spitspce.space;

import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.TypedArray;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.avast.android.dialogs.iface.ISimpleDialogCancelListener;
import com.avast.android.dialogs.iface.ISimpleDialogListener;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;


import org.xmlpull.v1.XmlPullParserException;



import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import com.gitonway.lee.niftynotification.lib.Configuration;
import com.gitonway.lee.niftynotification.lib.Effects;
import com.gitonway.lee.niftynotification.lib.NiftyNotificationView;


public class MainTileActivity extends ActionBarActivity implements BaseSliderView.OnSliderClickListener,ISimpleDialogCancelListener,ISimpleDialogListener{

    private SliderLayout mDemoSlider,mDemoSlider2;

    private LinkedHashMap<String,Integer> AllEvents = new LinkedHashMap<String, Integer>();

    private HashMap<String,Integer> EventsSubset1 = new HashMap<>();

    private HashMap<String,Integer> EventsSubset2 = new HashMap<>();

    public static final ArrayList<EventItem> EventsCollection = new ArrayList<>();


    private SharedPreferences spaceapp;

    private int toastFlag=1;
    private int dialogFlag=1;

    private static final int REQUEST_PROGRESS = 1;
    private static final int REQUEST_LIST_SIMPLE = 9;
    private static final int REQUEST_LIST_MULTIPLE = 10;
    private static final int REQUEST_LIST_SINGLE = 11;
    private static final int REQUEST_DATE_PICKER = 12;
    private static final int REQUEST_TIME_PICKER = 13;
    private static final int REQUEST_SIMPLE_DIALOG = 42;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDemoSlider = (SliderLayout)findViewById(R.id.slider1);
        mDemoSlider2 = (SliderLayout)findViewById(R.id.slider2);

        spaceapp = getSharedPreferences("space",0);

        toastFlag = spaceapp.getInt("toastFlag",1);
        dialogFlag = spaceapp.getInt("dialogFlag",1);

        EventsCollection.clear();


        if(toastFlag!=0)
            FirstToast();


        InputStream eventsStream = null;
        AssetManager assetManager = getAssets();
        List events = null;
        try {
            eventsStream = assetManager.open("events.xml");
            EventsXmlParser parser = new EventsXmlParser();
            events = parser.parse(eventsStream);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        String[] event_names  = getResources().getStringArray(R.array.event_name);
        String[] event_emails  = getResources().getStringArray(R.array.event_email_id);
        String[] event_dates  = getResources().getStringArray(R.array.event_date);
        String[] event_time  = getResources().getStringArray(R.array.event_time);
        TypedArray event_image_ids = getResources().obtainTypedArray(R.array.event_image_id);

        for(int i=0;i<event_names.length;i++){
            EventsCollection.add(new EventItem(i,event_image_ids.getResourceId(i,0),event_names[i],((EventsXmlParser.Event)events.get(i)).introduction
            ,((EventsXmlParser.Event)events.get(i)).contact,event_emails[i],((EventsXmlParser.Event)events.get(i)).category
            ,((EventsXmlParser.Event)events.get(i)).venue,((EventsXmlParser.Event)events.get(i)).prizes,((EventsXmlParser.Event)events.get(i)).rules
            ,event_dates[i],event_time[i],false));
        }

        for(int i=0;i<EventsCollection.size();++i)
            AllEvents.put(event_names[i], i);


        for(int i=0;i<EventsCollection.size();++i) {
            if(!EventsCollection.get(i).getEventCategory().equals("Informals"))
                EventsSubset1.put(event_names[i], event_image_ids.getResourceId(i, 0));
        }

        for(int i=0;i<EventsCollection.size();++i) {
            if(EventsCollection.get(i).getEventCategory().equals("Informals"))
                EventsSubset2.put(event_names[i], event_image_ids.getResourceId(i, 0));
        }


        for(String event_name : EventsSubset1.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(event_name)
                    .image(EventsSubset1.get(event_name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.getBundle()
                    .putInt("id", AllEvents.get(event_name));

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Top);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4100);


        for(String event_name : EventsSubset2.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(event_name)
                    .image(EventsSubset2.get(event_name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.getBundle()
                    .putInt("id", AllEvents.get(event_name));

            mDemoSlider2.addSlider(textSliderView);
        }
        mDemoSlider2.setPresetTransformer(SliderLayout.Transformer.DepthPage);
        mDemoSlider2.setPresetIndicator(SliderLayout.PresetIndicators.Center_Top);
        mDemoSlider2.setCustomAnimation(new DescriptionAnimation());

        mDemoSlider2.setDuration(2900);

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

        String[] fav = spaceapp.getString("favStr","").split(" ");
        for(int i = 0; i < fav.length; i++) {
            if (fav[i] != "") {
                int fuid = Integer.parseInt(fav[i]);
                EventsCollection.get(fuid).setFavourite(true);
            }
        }


        //tile image views

        View view1 = findViewById(R.id.button1);
        RectImageView1 rectImageView11 = (RectImageView1) view1.findViewById(R.id.image_header);
        rectImageView11.setBackgroundResource(R.drawable.informals);
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainTileActivity.this, ActionDrawerActivity.class);
                i.putExtra("category", "inf");
                MainTileActivity.this.startActivity(i);
            }
        });

        View view2 = findViewById(R.id.button2);
        RectImageView1 rectImageView12 = (RectImageView1) view2.findViewById(R.id.image_header);
        rectImageView12.setBackgroundResource(R.drawable.literary);
        view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainTileActivity.this, ActionDrawerActivity.class);
                i.putExtra("category", "la");
                MainTileActivity.this.startActivity(i);
            }
        });

        View view3 = findViewById(R.id.button3);
        RectImageView1 rectImageView13 = (RectImageView1) view3.findViewById(R.id.image_header);
        rectImageView13.setBackgroundResource(R.drawable.games);
        view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainTileActivity.this, ActionDrawerActivity.class);
                i.putExtra("category", "gas");
                MainTileActivity.this.startActivity(i);
            }
        });

        View view7 = findViewById(R.id.button5);
        VerticalImageView2 ImageView7 = (VerticalImageView2) view7.findViewById(R.id.image_header);
        ImageView7.setBackgroundResource(R.drawable.picks);
        view7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainTileActivity.this, ActionDrawerActivity.class);
                i.putExtra("category", "fav");
                MainTileActivity.this.startActivity(i);
            }
        });


        View view4 = findViewById(R.id.button_cat);
        VerticalImageView1 ImageView4 = (VerticalImageView1) view4.findViewById(R.id.image_header);
        ImageView4.setBackgroundResource(R.drawable.focus);
        view4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainTileActivity.this, ParallaxActivity.class);
                MainTileActivity.this.startActivity(i);
            }
        });

        View view5 = findViewById(R.id.button4);
        RectImageView1 rectImageView15 = (RectImageView1) view5.findViewById(R.id.image_header);
        rectImageView15.setBackgroundResource(R.drawable.performing);
        view5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainTileActivity.this, ActionDrawerActivity.class);
                i.putExtra("category", "pa");
                MainTileActivity.this.startActivity(i);
            }
        });

        View view6 = findViewById(R.id.youtube_click);
        RectImageView2 rectImageView2 = (RectImageView2) view6.findViewById(R.id.image_header);
        rectImageView2.setBackgroundResource(R.drawable.media);
        view6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainTileActivity.this, VideoStreamActivity.class);
                MainTileActivity.this.startActivity(i);
            }
        });





    }

    private void FirstToast() {

        Configuration cfg=new Configuration.Builder()
                .setAnimDuration(1500)
                .setDispalyDuration(2000)
                .setTextPadding(10)                       //dp
                .setTextLines(5)                        //You had better use setViewHeight and setTextLines together
                .setBackgroundColor("#FFe1e1e1")
                .setIconBackgroundColor("#00000000")
                .setTextColor("#FF212121")
                .setTextGravity(Gravity.CENTER)         //only text def  Gravity.CENTER,contain icon Gravity.CENTER_VERTICAL
                .build();

        String msg = "Swipe across the sliders to navigate" +
                "\nTap on any event for further details" +
                "\nTap on these toasts to disable them";

        final NiftyNotificationView toast = NiftyNotificationView.build(this, msg, Effects.thumbSlider, R.id.toast_layout, cfg)
                .setIcon(R.drawable.ic_launcher);
        toast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast.hide();
                toastFlag=0;
            }
        });
        toast.show();
    }

    @Override
    protected void onPause() {
        String favStr = "";
        for(int i=0;i<MainTileActivity.EventsCollection.size();i++) {
            if(MainTileActivity.EventsCollection.get(i).isFavourite()) {

                favStr += MainTileActivity.EventsCollection.get(i).getEventUID() + " ";
            }
        }
        SharedPreferences.Editor editor = spaceapp.edit();
        editor.putString("favStr",favStr);
        editor.putInt("toastFlag",toastFlag);
        editor.putInt("dialogFlag",dialogFlag);
        editor.commit();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        String favStr = "";
        for(int i=0;i<MainTileActivity.EventsCollection.size();i++) {
            if(MainTileActivity.EventsCollection.get(i).isFavourite()) {

                favStr += MainTileActivity.EventsCollection.get(i).getEventUID() + " ";
            }
        }
        SharedPreferences.Editor editor = spaceapp.edit();
        editor.putString("favStr",favStr);
        editor.putInt("toastFlag", toastFlag);
        editor.putInt("dialogFlag",dialogFlag);
        editor.commit();


        super.onDestroy();
    }

    @Override
    public void onBackPressed() {

        if(dialogFlag!=0) {
            SimpleDialogFragment.createBuilder(MainTileActivity.this, getSupportFragmentManager())
                    .setTitle("Do you like the app?")
                    .setMessage("Please spare a moment and give us a rating :)\n" +
                            "Ignore if done\n")
                    .setPositiveButtonText("Rate")
                    .setNegativeButtonText("Not Now")
                    .setNeutralButtonText("Never")
                    .setRequestCode(REQUEST_SIMPLE_DIALOG)
                    .show();
        }else{
            super.onBackPressed();
        }

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Intent i = new Intent(MainTileActivity.this, EventDetailActivity.class);
        i.putExtra("event_value", (int)slider.getBundle().get("id"));
        MainTileActivity.this.startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(MainTileActivity.this, DevelopersActivity.class);
            MainTileActivity.this.startActivity(i);
            return true;
        }else if (id == R.id.action_share) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "The Official App of SPACE'15:\nhttps://play.google.com/store/apps/details?id=com.spitspce.space\nDownload, Review & Share :)";
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // ISimpleDialogCancelListener

    @Override
    public void onCancelled(int requestCode) {
        switch (requestCode) {
            case REQUEST_SIMPLE_DIALOG:
                //Toast.makeText(c, "Dialog cancelled", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    // ISimpleDialogListener

    @Override
    public void onPositiveButtonClicked(int requestCode) {
        if (requestCode == REQUEST_SIMPLE_DIALOG) {
            //Toast.makeText(c, "Positive button clicked", Toast.LENGTH_SHORT).show();
            Intent viewIntent =
                    new Intent("android.intent.action.VIEW",
                            Uri.parse("https://play.google.com/store/apps/details?id=com.spitspce.space"));
            startActivity(viewIntent);
        }
    }

    @Override
    public void onNegativeButtonClicked(int requestCode) {
        if (requestCode == REQUEST_SIMPLE_DIALOG) {
            //Toast.makeText(c, "Negative button clicked", Toast.LENGTH_SHORT).show();
            super.onBackPressed();
        }
    }

    @Override
    public void onNeutralButtonClicked(int requestCode) {
        if (requestCode == REQUEST_SIMPLE_DIALOG) {
            //Toast.makeText(c, "Neutral button clicked", Toast.LENGTH_SHORT).show();
            dialogFlag=0;
            super.onBackPressed();
        }
    }
}