package com.spitspce.space;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.manuelpeinado.fadingactionbar.extras.actionbarcompat.FadingActionBarHelper;


public class DevelopersActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FadingActionBarHelper helper = new FadingActionBarHelper()
                .actionBarBackground(R.drawable.black_drawable)
                .headerLayout(R.layout.header_light)
                .contentLayout(R.layout.activity_developers)
                .headerOverlayLayout(R.layout.shadow);
        setContentView(helper.createView(this));
        helper.initActionBar(this);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        KenBurnsView img = (KenBurnsView) findViewById(R.id.image_header);
        img.setImageResource(R.drawable.app_team);

        View view1 = findViewById(R.id.one);
        ImageView photo1 = (ImageView) view1.findViewById(R.id.imagedeveloper);
        photo1.setImageResource(R.drawable.aditya);
        TextView name1 = (TextView)view1.findViewById(R.id.name);
        name1.setText("Aditya Rathi");
        TextView develop1 = (TextView)view1.findViewById(R.id.development);
        develop1.setText("Third Year COMPS\nTeam Lead\nadityarathi95@gmail.com");

        View view2 = findViewById(R.id.two);
        ImageView photo2 = (ImageView) view2.findViewById(R.id.imagedeveloper);
        photo2.setImageResource(R.drawable.abbas);
        TextView name2 = (TextView)view2.findViewById(R.id.name);
        name2.setText("Abbas Cyclewala");
        TextView develop2 = (TextView)view2.findViewById(R.id.development);
        develop2.setText("Third Year COMPS\nabbasc52@gmail.com");

        View view3 = findViewById(R.id.three);
        ImageView photo3 = (ImageView) view3.findViewById(R.id.imagedeveloper);
        photo3.setImageResource(R.drawable.varun);
        TextView name3 = (TextView)view3.findViewById(R.id.name);
        name3.setText("Varun Joshi");
        TextView develop3 = (TextView)view3.findViewById(R.id.development);
        develop3.setText("Second Year COMPS\nvarunj.1011@gmail.com");

        View view4 = findViewById(R.id.four);
        ImageView photo4 = (ImageView) view4.findViewById(R.id.imagedeveloper);
        photo4.setImageResource(R.drawable.parisa);
        TextView name4 = (TextView)view4.findViewById(R.id.name);
        name4.setText("Parisa Manjarekar");
        TextView develop4 = (TextView)view4.findViewById(R.id.development);
        develop4.setText("Third Year IT\nparisamanjarekar@gmail.com");

        View view5 = findViewById(R.id.five);
        ImageView photo5 = (ImageView) view5.findViewById(R.id.imagedeveloper);
        photo5.setImageResource(R.drawable.shubham);
        TextView name5 = (TextView)view5.findViewById(R.id.name);
        name5.setText("Shubham Singh");
        TextView develop5 = (TextView)view5.findViewById(R.id.development);
        develop5.setText("Third Year IT\nsingh.shubham889@gmail.com");



    }



}
