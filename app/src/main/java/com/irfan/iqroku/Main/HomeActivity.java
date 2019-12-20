package com.irfan.iqroku.Main;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.irfan.iqroku.R;

import io.isfaaghyth.rak.Rak;

public class HomeActivity extends AppCompatActivity {

    ImageView imgIqro, imgInfo, imgLatihan, imgLogout;

    MediaPlayer audioBackground;

    ToggleButton myTombol;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_main);


        //Memanggil file my_sound pada folder raw
        audioBackground = MediaPlayer.create(this, R.raw.happiness);
        //Set looping ke true untuk mengulang audio jika telah selesai
        audioBackground.setLooping(true);
        //Set volume audio agar berbunyi
        audioBackground.setVolume(1, 1);
        //Memulai audio
        audioBackground.start();




        imgIqro = (ImageView) findViewById(R.id.imgiqro);
        imgInfo = (ImageView) findViewById(R.id.imginfo);
        imgLatihan = (ImageView) findViewById(R.id.imglatihan);
        imgLogout = (ImageView) findViewById(R.id.imglogout);


        imgIqro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(HomeActivity.this, IqroActivity.class);
                startActivity(i);

            }
        });

        imgInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(HomeActivity.this, InfoActivity.class);
                startActivity(i);
            }
        });

        imgLatihan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(HomeActivity.this, KuisTebakHijaiyahActivity.class);
                startActivity(i);


            }
        });

        imgLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                Rak.entry("login", false);
                Rak.removeAll(getApplicationContext());
                finishAffinity();
            }
        });
    }


    public void onToggleClicked(View view) {
        boolean on = ((ToggleButton) view).isChecked();

        if (on) {
            /*Mematikan suara audio*/
            audioBackground.setVolume(0, 0);
        } else {
            /*Menghidupkan kembali audio background*/
            audioBackground.setVolume(1, 1);
        }
    }


    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        audioBackground.stop();

//        if (audioBackground.isPlaying())
//            audioBackground.pause();

        HomeActivity.this.finish();

    }
//    @Override
//    public void onStop()
//    {
//        super.onStop();
//        if (audioBackground.isPlaying())
//            audioBackground.stop();
//        else
//            return;
//
//
//    }
//
//    @Override
//    public void onStart()
//    {
//        super.onStart();
//        if (audioBackground.isPlaying())
//            audioBackground.start();
//        else
//            return;
//
//
//    }



}
