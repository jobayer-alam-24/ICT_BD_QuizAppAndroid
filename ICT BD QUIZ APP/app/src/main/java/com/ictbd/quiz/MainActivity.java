package com.ictbd.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.OnBackPressedCallback;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ictbd.quiz.GridViewbyJuba;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    GridViewbyJuba mainGrid;
    SharedPreferences sharedPreferences;
    TextView tvScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainGrid = findViewById(R.id.mainGrid);
        tvScore = findViewById(R.id.tvScore);
        sharedPreferences = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);

        QuestionCollection.createQuestionBank();


        MyAdapter adapter = new MyAdapter();
        mainGrid.setExpanded(true);
        mainGrid.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        String lastScore = sharedPreferences.getString("savedScore", "No Data");
        tvScore.setText(lastScore);

        this.getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            private long mBackPressed = 0;
            private final int TIME_INTERVAL = 2000;

            @Override
            public void handleOnBackPressed() {
                if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    Toast.makeText(getBaseContext(), "Press again to exit", Toast.LENGTH_SHORT).show();
                }
                mBackPressed = System.currentTimeMillis();
            }
        });
    }

    private class MyAdapter extends BaseAdapter {
        private LayoutInflater inflater;

        public MyAdapter() {
            this.inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return QuestionCollection.questionBank.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = inflater.inflate(R.layout.grid_item, parent, false);

            ImageView imgIcon = convertView.findViewById(R.id.imgIcon);
            TextView tvTitle = convertView.findViewById(R.id.tvTitle);
            LinearLayout layItem = convertView.findViewById(R.id.layItem);

            HashMap<String, String> mHashMap = QuestionCollection.subjectList.get(position);
            String subjectName = mHashMap.get("subjectName");
            String icon = mHashMap.get("icon");

            if (tvTitle != null) tvTitle.setText(subjectName);
            if (imgIcon != null && icon != null) {
                int drawable = Integer.parseInt(icon);
                imgIcon.setImageResource(drawable);
            }

            Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_grid);
            animation.setStartOffset(position * 400);
            convertView.startAnimation(animation);

            layItem.setOnClickListener(view -> {
                QuestionCollection.SUBJECT_NAME = subjectName;
                QuestionCollection.question_list = QuestionCollection.questionBank.get(position);
                startActivity(new Intent(MainActivity.this, QuestionCollection.class));

            });

            return convertView;
        }
    }


}
