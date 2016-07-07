package com.android.mvpp2p.ui.HotNews;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.android.mvpp2p.Constants;
import com.android.mvpp2p.R;
import com.android.mvpp2p.base.BaseFragment;
import com.android.mvpp2p.base.ViewHolder;
import com.android.mvpp2p.base.recyclerview.BaseHeadAdapter;
import com.android.mvpp2p.base.recyclerview.EmptyRecyclerView;
import com.android.mvpp2p.bean.NewsConsultData;
import com.android.mvpp2p.ui.main.MainComponent;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;


public class HotNewsFragment extends BaseFragment implements HotNewsContract.View {
    @Inject
    Activity mActivity;
    @Inject
    HotNewsPresenter mHotNewsPresenter;
    @Bind(R.id.recyclerView)
    EmptyRecyclerView mEmptyRecyclerView;
    List<NewsConsultData> mNewList = new ArrayList<>();
    BaseHeadAdapter<NewsConsultData> mAdapter;
    int tid;

    public static HotNewsFragment newInstance(int tid) {
        Bundle args = new Bundle();
        args.putInt(Constants.TID, tid);
        HotNewsFragment fragment = new HotNewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

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
        tid = getArguments().getInt(Constants.TID, 10);
    }

    @Override
    public void initUI(View view) {
        ButterKnife.bind(this, view);
        mHotNewsPresenter.attachView(this);
        mEmptyRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new BaseHeadAdapter<NewsConsultData>(mActivity, R.layout.item_consult_detail, mNewList) {
            @Override
            public void convert(ViewHolder holder, NewsConsultData newsConsultData) {
                SimpleDraweeView ivIcon = holder.getView(R.id.iv_consult_icon);
                TextView tvTitle = holder.getView(R.id.tv_consult_title);
                TextView tvDescription = holder.getView(R.id.tv_consult_desc);
                Uri uri = Uri.parse("http://www.p2peye.com/data/attachment/" + newsConsultData.getPic());
//                ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
//                        .build();
//                PipelineDraweeController controller =
//                        (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
//                                .setImageRequest(request)
//                                .build();
//                ivIcon.setController(controller);
                tvTitle.setText(newsConsultData.getTitle());
                tvDescription.setText("" + tid);
            }
        };
        mEmptyRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void initData() {
        hideLoading();
        mHotNewsPresenter.BindList(tid);
    }

    @Override
    public void RenderList(List<NewsConsultData> mNewList) {
        this.mNewList.clear();
        this.mNewList.addAll(mNewList);
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
        ButterKnife.unbind(this);
        mHotNewsPresenter.detachView();

    }
}
