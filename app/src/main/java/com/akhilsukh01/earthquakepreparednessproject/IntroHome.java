package com.akhilsukh01.earthquakepreparednessproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.marcinorlowski.fonty.Fonty;

public class IntroHome extends Activity {

    private DatabaseReference mDatabase;

    public static TextView locat1;
    public static TextView locat2;
    public static TextView locat3;
    public static TextView locat4;
    public static TextView locat5;
    public static TextView locat6;

    public static TextView mag1;
    public static TextView mag2;
    public static TextView mag3;
    public static TextView mag4;
    public static TextView mag5;
    public static TextView mag6;


    public static TextView titleView;
    public static TextView titleViewS;

    Animation frombotton;
    public RelativeLayout splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        fetchedData process = new fetchedData();
        process.execute();

        this.setContentView(R.layout.activity_intro_home);
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        locat1 = findViewById(R.id.locat1);
        locat2 = findViewById(R.id.locat2);
        locat3 = findViewById(R.id.locat3);
        locat4 = findViewById(R.id.locat4);
        locat5 = findViewById(R.id.locat5);
        locat6 = findViewById(R.id.locat6);

        mag1 = findViewById(R.id.mag1);
        mag2 = findViewById(R.id.mag2);
        mag3 = findViewById(R.id.mag3);
        mag4 = findViewById(R.id.mag4);
        mag5 = findViewById(R.id.mag5);
        mag6 = findViewById(R.id.mag6);

        titleView = findViewById(R.id.titleView);
        titleViewS = findViewById(R.id.titleViewS);
        splash = findViewById(R.id.splash);





//        Fonty
//                .context(this)
//                .fontDir("fonts")
//                .normalTypeface("Montserrat-Light.ttf")
//                .boldTypeface("Montserrat-Regular.ttf")
//                .build();
//        Fonty.setFonts(this);

        Typeface Light = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Light.ttf");
        titleView.setTypeface(Light);
        titleViewS.setTypeface(Light);



        CardView mChecklist = (CardView) findViewById(R.id.mChecklist);
        mChecklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(IntroHome.this, Checklist.class);
                startActivity(intent1);
            }
        });

        CardView mInfo = (CardView) findViewById(R.id.mInfo);
        mInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(IntroHome.this, PrepInfo.class);
                startActivity(intent2);
            }
        });

        CardView mAlerts = (CardView) findViewById(R.id.mAlerts);
        mAlerts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(IntroHome.this, message.class);
                startActivity(intent3);
            }
        });

        TableLayout mList = (TableLayout) findViewById(R.id.mQuake);
        mList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!locat1.getText().toString().equals("Location1 ... LOADING")) {
                    Intent intent4 = new Intent(IntroHome.this, quakeList.class);
                    startActivity(intent4);
                }
            }
        });

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
           }
        }, 800);
        frombotton = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.frombottom );
        titleViewS.setAnimation(frombotton);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                splash.setVisibility(View.GONE);
            }
        }, 1800);

    }
}
