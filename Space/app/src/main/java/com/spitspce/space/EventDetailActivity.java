/*
 * Copyright (C) 2013 Manuel Peinado
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.spitspce.space;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract.Events;
import android.support.v7.app.ActionBarActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.RandomTransitionGenerator;
import com.manuelpeinado.fadingactionbar.extras.actionbarcompat.FadingActionBarHelper;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.listeners.ActionClickListener;

import java.util.Calendar;

public class EventDetailActivity extends ActionBarActivity {

    private int event_value=0;

    private String diff_text,complete_text;
    private SpannableString s;
    private SharedPreferences spaceapp;
    private EventItem item=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        spaceapp = getSharedPreferences("space",0);
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            event_value= extras.getInt("event_value");
        }
        item = MainTileActivity.EventsCollection.get(event_value);

        final Typeface tf1 = Typeface.createFromAsset(getAssets(), "fonts/berlin-sans-fb-demi-bold.ttf");
        final Typeface tf2 = Typeface.createFromAsset(getAssets(), "fonts/Berlin Sans FB.ttf");

        getSupportActionBar().setTitle(item.getEventName());


        FadingActionBarHelper helper = new FadingActionBarHelper()
                .actionBarBackground(R.drawable.black_drawable)
                .headerLayout(R.layout.header_light)
                .contentLayout(R.layout.eventdesc)
                .headerOverlayLayout(R.layout.shadow);
        setContentView(helper.createView(this));
        helper.initActionBar(this);


        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(item.getEventName());

        KenBurnsView img = (KenBurnsView) findViewById(R.id.image_header);
        img.setImageResource(item.getEventImageID());

        RandomTransitionGenerator generator = new RandomTransitionGenerator(6000, new AccelerateDecelerateInterpolator());
        img.setTransitionGenerator(generator);
        TextView EventName = (TextView) findViewById(R.id.textView1);
        EventName.setTypeface(tf1);
        EventName.setText(item.getEventName());
        ImageView EventIcon = (ImageView) findViewById(R.id.imageView2);
        EventIcon.setImageResource(item.getEventImageID());

        TextView categoryText = (TextView) findViewById(R.id.event_category_text);
        TextView timeText = (TextView) findViewById(R.id.event_time_text);
        TextView venueText = (TextView) findViewById(R.id.event_venue_text);
        TextView dateText = (TextView) findViewById(R.id.event_date_text);
        TextView introText = (TextView) findViewById(R.id.event_intro_text);
        TextView rulesText = (TextView) findViewById(R.id.event_rules_text);
        TextView prizesText = (TextView) findViewById(R.id.event_prizes_text);
        TextView contactText = (TextView) findViewById(R.id.event_contact_text);
        TextView emailText = (TextView) findViewById(R.id.event_email_text);

        categoryText.setTypeface(tf2);
        categoryText.setText("Category:\n"+item.getEventCategory());

        diff_text = "Time: ";
        complete_text = diff_text+item.getEventTime();
        s = new SpannableString(complete_text);
        setFont();
        timeText.setText(s);


        diff_text = "Venue: ";
        complete_text = diff_text+item.getEventVenue();
        s = new SpannableString(complete_text);
        setFont();
        venueText.setText(s);

        diff_text = "Date: ";
        complete_text = diff_text+item.getEventDate();
        s = new SpannableString(complete_text);
        setFont();
        dateText.setText(s);


        diff_text = "Description: ";
        complete_text = diff_text+"\n"+item.getEventIntro();
        s = new SpannableString(complete_text);
        setFont();
        introText.setText(s);

        diff_text = "Rules: ";
        complete_text = diff_text+"\n"+item.getEventRules();
        s = new SpannableString(complete_text);
        setFont();
        rulesText.setText(s);

        diff_text = "Prizes: ";
        complete_text = diff_text+"\n"+item.getEventPrizes();
        s = new SpannableString(complete_text);
        setFont();
        prizesText.setText(s);

        diff_text = "Email ID: ";
        complete_text = diff_text+"\n"+item.getEventMail();
        s = new SpannableString(complete_text);
        setFont();
        emailText.setText(s);

        diff_text = "Contact: ";
        complete_text = diff_text+"\n"+item.getEventContact();
        s = new SpannableString(complete_text);
        setFont();
        contactText.setText(s);

        final ImageView imageView = (ImageView)findViewById(R.id.setfav);
        if(item.isFavourite())
            imageView.setImageResource(R.drawable.fav);
        else
            imageView.setImageResource(R.drawable.unfav);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setFavourite(!item.isFavourite());
                if(item.isFavourite()) {
                    imageView.setImageResource(R.drawable.fav);
                    Snackbar.with(getApplicationContext()) // context
                            .text(item.getEventName()+" has been added to your picks") // text to be displayed
                            .textColor(Color.BLACK) // change the text color
                            .textTypeface(tf1) // change the text font
                            .color(Color.argb(235,99,214,74)) // change the background color
                            .show(EventDetailActivity.this);
                }else {
                    imageView.setImageResource(R.drawable.unfav);
                    Snackbar.with(getApplicationContext()) // context
                            .text(item.getEventName()+" has been removed from your picks") // text to be displayed
                            .textColor(Color.WHITE) // change the text color
                            .textTypeface(tf1) // change the text font
                            .color(Color.argb(235,151,27,16)) // change the background color
                            .show(EventDetailActivity.this);
                }
            }
        });


    }

    private void setFont() {
        s.setSpan(new ForegroundColorSpan(Color.BLACK), 0, diff_text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        s.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, diff_text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        s.setSpan(new TypefaceSpan("fonts/berlin-sans-fb-demi-bold.ttf"), 0, diff_text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
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
        editor.commit();
        super.onDestroy();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }


}
