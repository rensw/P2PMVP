package com.android.mvpp2p.base.recyclerview;

import android.view.View;
import android.view.ViewGroup;

public interface OnItemClickListener
{
    void onItemClick(ViewGroup parent, View view, int position);
    boolean onItemLongClick(ViewGroup parent, View view, int position);
}