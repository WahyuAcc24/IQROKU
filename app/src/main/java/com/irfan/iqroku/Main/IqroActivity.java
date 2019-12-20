package com.irfan.iqroku.Main;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.irfan.iqroku.R;
import com.irfan.iqroku.fragments.IqroFragment;

public class IqroActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_main);

        Fragment fragment = getSupportFragmentManager().findFragmentById(android.R.id.content);
        if (fragment == null) {
            fragment = IqroFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(android.R.id.content, fragment, "")
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .attach(fragment)
                    .commit();

        }


    }


}
