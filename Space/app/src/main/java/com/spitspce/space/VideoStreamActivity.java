package com.spitspce.space;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;
 
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.PlayerStyle;
import com.google.android.youtube.player.YouTubePlayerView;
import com.nispok.snackbar.Snackbar;


public class VideoStreamActivity extends YouTubeBaseActivity implements
YouTubePlayer.OnInitializedListener {


    public static final String DEVELOPER_KEY = "AIzaSyAf8oTMHWLggTNeYwt20fuOasAvOk1lauE";

    // YouTube video id
    public static final String YOUTUBE_VIDEO_CODE = "wnmaztOyxDE";

	private static final int RECOVERY_DIALOG_REQUEST = 1;
	 
    // YouTube player view
    private YouTubePlayerView youTubeView;

    private Handler dialogHandler=null;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        setContentView(R.layout.youtube_main);

        final Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/berlin-sans-fb-demi-bold.ttf");

        TextView textView = (TextView) findViewById(R.id.preview);
        textView.setTypeface(tf);
        textView.setText("SPACE 2015 PREVIEW");

        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
 
        // Initializing video player with developer key
        youTubeView.initialize(DEVELOPER_KEY, this);

        dialogHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                Snackbar.with(getApplicationContext()) // context
                        .text("Please do wait for a few moments...") // text to be displayed
                        .textColor(Color.WHITE) // change the text color
                        .textTypeface(tf) // change the text font
                        .color(Color.argb(235, 105, 5, 98)) // change the background color
                        .show(VideoStreamActivity.this);
                super.handleMessage(msg);
                return;
            }
        };

        new Thread(){
            @Override
            public void run() {
                // TODO Auto-generated method stub
                super.run();

                try {
                    Thread.sleep(2500);
                    dialogHandler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }.start();


 
    }
 
    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
            YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format(
                    getString(R.string.error_player), errorReason.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }
 
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
            YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {
 
            // loadVideo() will auto play video
            // Use cueVideo() method, if you don't want to play it automatically
            player.loadVideo(YOUTUBE_VIDEO_CODE);
            // Hiding player controls

            player.setPlayerStyle(PlayerStyle.CHROMELESS);
        }
    }
 
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(DEVELOPER_KEY, this);
        }
    }
 
    private YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_view);
    }
 
}
