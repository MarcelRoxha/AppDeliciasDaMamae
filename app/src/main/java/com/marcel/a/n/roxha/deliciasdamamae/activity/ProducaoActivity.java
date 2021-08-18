package com.marcel.a.n.roxha.deliciasdamamae.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.fragments.ReceitaNovaFragment;
import com.marcel.a.n.roxha.deliciasdamamae.fragments.ReceitasProntasFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ProducaoActivity extends AppCompatActivity {

    private SmartTabLayout smartTabLayout;
    private ViewPager viewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setTheme(R.style.Theme_DeliciasDaMamae);
        setContentView(R.layout.activity_producao);


        smartTabLayout = findViewById(R.id.smartTabProducao);
        viewPager = findViewById(R.id.viewPagerProducao);

       getSupportActionBar().setElevation(0);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(),
                FragmentPagerItems.with(this)
                        .add("PRODUÇÕES CADASTRADAS", ReceitasProntasFragment.class)
                        .add("CADASTRAR NOVA PRODUÇÃO", ReceitaNovaFragment.class)
                        .create()
        );
        viewPager.setAdapter(adapter);
        smartTabLayout.setViewPager(viewPager);



    }
}