package com.marcel.a.n.roxha.deliciasdamamae.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.fragments.BolosParaVendaFragment;
import com.marcel.a.n.roxha.deliciasdamamae.fragments.ConfeccionarBoloFragment;
import com.marcel.a.n.roxha.deliciasdamamae.fragments.ReceitaNovaFragment;
import com.marcel.a.n.roxha.deliciasdamamae.fragments.ReceitasProntasFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class BolosActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_bolos);


        smartTabLayout = findViewById(R.id.smartTabBolos);
        viewPager = findViewById(R.id.viewPagerBolos);

        getSupportActionBar().setElevation(0);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(),
                FragmentPagerItems.with(this)
                        .add("PRODUTOS PARA VENDA", BolosParaVendaFragment.class)
                        .add("CADASTRAR PRODUTO PARA VENDA", ConfeccionarBoloFragment.class)
                        .create()
        );
        viewPager.setAdapter(adapter);
        smartTabLayout.setViewPager(viewPager);

    }
}