package io.coreflodev.openchat.common;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity {

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        ButterKnife.bind(this);
    }
}
