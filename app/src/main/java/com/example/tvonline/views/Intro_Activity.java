package com.example.tvonline.views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.tvonline.R;
import com.example.tvonline.adapters.intro_adapter;
import com.example.tvonline.models.intro_model;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import kotlin.collections.ArrayDeque;

public class Intro_Activity extends AppCompatActivity implements View.OnClickListener {

    ViewPager viewPager;

    TextView textView_next , textView_back , textView_escape;

    TabLayout tabLayout;

    List<intro_model> list;
    intro_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        init();
        onClick();
        loadData();
    }

    private void init(){
        textView_next = findViewById(R.id.textview_next);
        textView_back = findViewById(R.id.textview_back);
        textView_escape = findViewById(R.id.textview_escape);
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tablayout);

        list = new ArrayList<>();
        adapter = new intro_adapter(list,this);

        viewPager.setAdapter(adapter);

    }

    private void loadData(){
        list.add(new intro_model("تلوزیون در دستان شما","هر کجا به تلوزیون متصل باشید به راحتی!!",R.drawable.a));
        list.add(new intro_model("با اینترنت نیم بها","راحتی و ازران بودن در استفاده هدف ماست",R.drawable.b));
        list.add(new intro_model("راحتی در کار کردن بودن نیاز به اشتراک","ما برنامه را برای احترام به مشتری به گونه ای ساخته ایم که به راحتی بتواند در هر جا و مکان استفاده کرد",R.drawable.c));

        adapter.notifyDataSetChanged();//refresh the adapter

        tabLayout.setupWithViewPager(viewPager);

    }
    private void onClick(){
        textView_next.setOnClickListener(this);
        textView_back.setOnClickListener(this);
        textView_escape.setOnClickListener(this);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == list.size() - 1){
                    textView_next.setText("بزن بریم");
                }
                else {
                    textView_next.setText("بعدی");
                }

                if(position == 0){
                    textView_back.setTextColor(Color.parseColor("#c6c6c6"));
                }
                else {
                    textView_back.setTextColor(Color.parseColor("#FFBB86FC"));

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view == textView_back){
            if(viewPager.getCurrentItem() > 0){
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }
        }
        if(view == textView_next){
            if(viewPager.getCurrentItem() < list.size() - 1){
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
            else{
                startActivity(new Intent(Intro_Activity.this,HomeActivity.class));
                finish();
            }
        }
        if(view == textView_escape){
            startActivity(new Intent(Intro_Activity.this,HomeActivity.class));
            finish();
        }


    }
}