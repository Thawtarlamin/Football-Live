package com.thukuma.footballstream;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.thukuma.livesoccer.LiveScore;
import com.thukuma.livesoccer.Modal.LoadModal;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new LiveScore(MainActivity.this, new LiveScore.onComplete() {
            @Override
            public void onComplete(LoadModal modal) {
                Log.d("my-test", "League: "+modal.getLeague());
                Log.d("my-test", "Home Name: "+modal.getHome_name());
                Log.d("my-test", "Home Flag: "+modal.getHome_flag());
                Log.d("my-test", "Away Name: "+modal.getAway_name());
                Log.d("my-test", "Away Flag: "+modal.getAway_flag());
                Log.d("my-test", "Time: "+modal.getTime());
                Log.d("my-test", "Status: "+modal.getStatus());
                Log.d("my-test", "Link1: "+modal.getLink());

            }
        }, new LiveScore.onError() {
            @Override
            public void onError(Exception e) {
                Log.d("my-test", "onError: "+e.getMessage());
            }

        });
    }
}