package com.android.mvpp2p.ui.product;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.TextView;

import com.android.mvpp2p.R;
import com.android.mvpp2p.base.BaseFragment;
import com.android.mvpp2p.base.ViewHolder;
import com.android.mvpp2p.base.recyclerview.BaseHeadAdapter;
import com.android.mvpp2p.base.recyclerview.EmptyRecyclerView;
import com.android.mvpp2p.bean.ManagerListData;
import com.android.mvpp2p.ui.main.MainComponent;
import com.android.mvpp2p.utils.Spanny;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by apple on 16/7/5.
 */
public class ProductFragment extends BaseFragment implements ProductContract.View {

    @Inject ProductPresenter mProductPresenter;
    @Bind(R.id.recyclerView)
    EmptyRecyclerView mRecyclerView;

    BaseHeadAdapter<ManagerListData> mAdapter;
    List<ManagerListData> mManagerListData=new ArrayList<>();;


    @Override
    public void initInjector() {
        getComponent(MainComponent.class).inject(this);
    }

    @Override
    public int initContentView() {
        return R.layout.base_list_layout;
    }

    @Override
    public void getBundle(Bundle bundle) {

    }

    @Override
    public void initUI(View view) {
        ButterKnife.bind(this, view);
        mProductPresenter.attachView(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new BaseHeadAdapter<ManagerListData>(getActivity(), R.layout.item_manager_listview, mManagerListData) {
            @Override
            public void convert(ViewHolder holder, ManagerListData managerData) {
                TextView tvTitle = holder.getView(R.id.tv_manager_product_name);
                TextView tvStyle = holder.getView(R.id.tv_manager_style);
                TextView tvRateNum = holder.getView(R.id.tv_manager_rate);
                TextView tvRate = holder.getView(R.id.tv_manager_rate_name);
                TextView tvInvestors = holder.getView(R.id.tv_manager_investors);
                TextView tvMoneyStart = holder.getView(R.id.tv_manager_moneystart);
                String name = managerData.getName();
                if ("乐投宝".equals(name)) {
                    tvTitle.setText("乐投宝(1元起投)");
                    tvInvestors.setText(managerData.getMain_title());
                    Spanny spanny = new Spanny("投资期限: ").append("灵活赎回", new ForegroundColorSpan(Color.parseColor("#2EB3E8")));
                    tvMoneyStart.setText(spanny);
                    tvStyle.setVisibility(View.VISIBLE);
                    tvRate.setText("最高年化收益");
                } else if ("乐定存".equals(name)) {
                    tvTitle.setText("乐定存(" + managerData.getSub_desc() + ")");
                    tvRate.setText("最高年化收益");
                    tvInvestors.setText(managerData.getMain_title());
                    Spanny spanny = new Spanny("投资期限: ").append("1~12月", new ForegroundColorSpan(Color.parseColor("#2EB3E8")));
                    tvMoneyStart.setText(spanny);
                    tvStyle.setVisibility(View.GONE);
                } else {
                    tvStyle.setVisibility(View.GONE);
                    tvTitle.setText("理财基金(" + managerData.getSub_desc() + ")");
                    tvRate.setText("预期年化收益");
                    if (name.contains("-")) {
                        String[] splitName = name.split("-");
                        tvInvestors.setText(splitName[1]);
                    } else {
                        tvInvestors.setText("");
                    }
                    Spanny spanny = new Spanny("投资期限: ").append(managerData.getDeadline(), new ForegroundColorSpan(Color.parseColor("#2EB3E8")));
                    tvMoneyStart.setText(spanny);
                }
                Spanny spanny = new Spanny(managerData.getRate()).append("%", new RelativeSizeSpan(0.6f));
                tvRateNum.setText(spanny);
            }
        };
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initData() {
        mProductPresenter.BindList();
    }

    @Override
    public void ItemClick(int position) {

    }

    @Override
    public void RenderList(List<ManagerListData> managerList) {
        mManagerListData.clear();
        mManagerListData.addAll(managerList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoading() {
        showProgress(true);
    }

    @Override
    public void hideLoading() {
        showContent(true);
    }

    @Override
    public void onError() {
        showError(true);
    }

    public void onDestroy() {
        super.onDestroy();
        mProductPresenter.detachView();
        ButterKnife.unbind(this);
    }
}
