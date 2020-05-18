package com.martin.myapplication.Hero;

import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.coppel.framework.core.view.BaseAppCompatActivity;
import com.martin.myapplication.R;

public class MainActivity extends BaseAppCompatActivity {

    @Override
    protected int getContentId() {
        return R.id.content;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setFragmentContentViewWithToolbar(
                savedInstanceState,
                R.layout.activity_main,
                R.id.toolbar,
                false,
                new HeroFragment(),
                HeroFragment.tag
        );
    }
}
