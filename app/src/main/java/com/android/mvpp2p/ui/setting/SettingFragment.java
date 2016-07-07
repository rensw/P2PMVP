package com.android.mvpp2p.ui.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.mvpp2p.R;
import com.android.mvpp2p.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;


public class SettingFragment extends BaseFragment implements View.OnClickListener {


    @Bind(R.id.setting_change_theme)
    TextView setting_change_theme;

    @Override
    public void initInjector() {

    }

    @Override
    public int initContentView() {
        return R.layout.fragment_setting;
    }

    @Override
    public void getBundle(Bundle bundle) {

    }

    @Override
    public void initUI(View view) {
        ButterKnife.bind(this, view);
        setting_change_theme.setOnClickListener(this);
        showContent(true);
    }

    @Override
    public void initData() {

    }


    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting_change_theme:
                ColorsDialogFragment.launch(getActivity());
                break;
        }
    }
}
