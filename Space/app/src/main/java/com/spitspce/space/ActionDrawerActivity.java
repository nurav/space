/*******************************************************************************
 * Copyright 2012 Steven Rudenko
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.spitspce.space;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.gitonway.lee.niftynotification.lib.Configuration;
import com.gitonway.lee.niftynotification.lib.Effects;
import com.gitonway.lee.niftynotification.lib.NiftyNotificationView;
import com.spitspce.space.actionview.ActionsContentView;


public class ActionDrawerActivity extends ActionBarActivity {

  private static final String STATE_URI = "state:uri";
  private static final String STATE_FRAGMENT_TAG = "state:fragment_tag";


    private static final String ABOUT_SCHEME = "category";

  private ActionsContentView viewActionsContentView;

  private Uri currentUri;
  private String currentContentFragmentTag = null;


    TextView textviewTitle;
    private String category_value;

    private SharedPreferences spaceapp;

    private int toast3Flag=1;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);


      Bundle extras = getIntent().getExtras();
      if(extras!=null){
          category_value= extras.getString("category");
      }

      currentUri = new Uri.Builder()
              .scheme(ABOUT_SCHEME)
              .authority(category_value)
              .build();

    setContentView(R.layout.actions_drawer);

      spaceapp = getSharedPreferences("space",0);
      toast3Flag = spaceapp.getInt("toast3Flag",1);

      if(toast3Flag!=0)
          FirstToast();


    viewActionsContentView = (ActionsContentView) findViewById(R.id.actionsContentView);
    viewActionsContentView.setSwipingType(ActionsContentView.SWIPING_ALL);

      final ActionBar abar = getSupportActionBar();
      View viewActionBar = getLayoutInflater().inflate(R.layout.title_view, null);
      ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
              ActionBar.LayoutParams.WRAP_CONTENT,
              ActionBar.LayoutParams.WRAP_CONTENT,
              Gravity.CENTER);
      textviewTitle = (TextView) viewActionBar.findViewById(R.id.title);
      Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/berlin-sans-fb-demi-bold.ttf");
      textviewTitle.setTypeface(tf);
      textviewTitle.setText(this.getTitle());
      abar.setCustomView(viewActionBar, params);
      abar.setDisplayShowCustomEnabled(true);
      abar.setDisplayShowTitleEnabled(false);
      abar.setHomeButtonEnabled(true);

    final ListView viewActionsList = (ListView) findViewById(R.id.actions);
    final ActionsAdapter actionsAdapter = new ActionsAdapter(this);
    viewActionsList.setAdapter(actionsAdapter);

      viewActionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> adapter, View v, int position,
                                  long flags) {
              final Uri uri = actionsAdapter.getItem(position);
              updateContent(uri);
              viewActionsContentView.showContent();
          }
      });

    if (savedInstanceState != null) {
      currentUri = Uri.parse(savedInstanceState.getString(STATE_URI));
      currentContentFragmentTag = savedInstanceState.getString(STATE_FRAGMENT_TAG);
    }

    updateContent(currentUri);

  }



  @Override
  protected void onSaveInstanceState(Bundle outState) {
    outState.putString(STATE_URI, currentUri.toString());
    outState.putString(STATE_FRAGMENT_TAG, currentContentFragmentTag);

    super.onSaveInstanceState(outState);
  }

  public void updateContent(Uri uri) {
    final Fragment fragment;
    final String tag;

    final FragmentManager fm = getSupportFragmentManager();
    final FragmentTransaction tr = fm.beginTransaction();

    if (!currentUri.equals(uri)) {
      final Fragment currentFragment = fm.findFragmentByTag(currentContentFragmentTag);
      if (currentFragment != null)
        tr.hide(currentFragment);
    }

    if (PAFragment.ABOUT_URI.equals(uri)) {
        textviewTitle.setText("Performing Arts");
      tag = PAFragment.TAG;
      final Fragment foundFragment = fm.findFragmentByTag(tag);
      if (foundFragment != null) {
        fragment = foundFragment;
      } else {
        fragment = new PAFragment();
      }
    }else if (LAFragment.ABOUT_URI.equals(uri)) {
        textviewTitle.setText("Literary Arts");
        tag = LAFragment.TAG;
        final Fragment foundFragment = fm.findFragmentByTag(tag);
        if (foundFragment != null) {
            fragment = foundFragment;
        } else {
            fragment = new LAFragment();
        }
    }else if (InformalsFragment.ABOUT_URI.equals(uri)) {
        textviewTitle.setText("Informals");
        tag = InformalsFragment.TAG;
        final Fragment foundFragment = fm.findFragmentByTag(tag);
        if (foundFragment != null) {
            fragment = foundFragment;
        } else {
            fragment = new InformalsFragment();
        }
    }else if (GASFragment.ABOUT_URI.equals(uri)) {
        textviewTitle.setText("Games & Sports");
        tag = GASFragment.TAG;
        final Fragment foundFragment = fm.findFragmentByTag(tag);
        if (foundFragment != null) {
            fragment = foundFragment;
        } else {
            fragment = new GASFragment();
        }
    }else if (FavouritesFragment.ABOUT_URI.equals(uri)) {
        textviewTitle.setText("Favourites");
        tag = FavouritesFragment.TAG;
        final Fragment foundFragment = fm.findFragmentByTag(tag);
        if (foundFragment != null) {
            fragment = foundFragment;
        } else {
            fragment = new FavouritesFragment();
        }
    }else if (WorkshopsFragment.ABOUT_URI.equals(uri)) {
        textviewTitle.setText("Workshops");
        tag = WorkshopsFragment.TAG;
        final Fragment foundFragment = fm.findFragmentByTag(tag);
        if (foundFragment != null) {
            fragment = foundFragment;
        } else {
            fragment = new WorkshopsFragment();
        }
    }else if (InnovationsFragment.ABOUT_URI.equals(uri)) {
        textviewTitle.setText("Innovations");
        tag = InnovationsFragment.TAG;
        final Fragment foundFragment = fm.findFragmentByTag(tag);
        if (foundFragment != null) {
            fragment = foundFragment;
        } else {
            fragment = new InnovationsFragment();
        }
    }else {
      return;
    }

    if (fragment.isAdded()) {
      tr.show(fragment);
    } else {
      tr.replace(R.id.content, fragment, tag);
    }
    tr.commit();

    currentUri = uri;
    currentContentFragmentTag = tag;
  }

    private void FirstToast() {

        Configuration cfg=new Configuration.Builder()
                .setAnimDuration(2000)
                .setDispalyDuration(1500)
                .setTextPadding(5)                       //dp
                .setTextLines(2)                        //You had better use setViewHeight and setTextLines together
                .setBackgroundColor("#FFe1e1e1")
                .setIconBackgroundColor("#00000000")
                .setTextColor("#FF212121")
                .setTextGravity(Gravity.CENTER)         //only text def  Gravity.CENTER,contain icon Gravity.CENTER_VERTICAL
                .build();

        String msg = "Drag from the left to open drawer";

        final NiftyNotificationView toast = NiftyNotificationView.build(this, msg, Effects.thumbSlider, R.id.toast_layout, cfg)
                .setIcon(R.drawable.ic_launcher);
        toast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast.hide();
                toast3Flag=0;
            }
        });
        toast.show();
    }


    @Override
    protected void onPause() {
        SharedPreferences.Editor editor = spaceapp.edit();
        editor.putInt("toast3Flag",toast3Flag);
        editor.commit();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        SharedPreferences.Editor editor = spaceapp.edit();
        editor.putInt("toast3Flag", toast3Flag);
        editor.commit();
        super.onDestroy();
    }


}
