package com.android.mvpp2p.widget.state;


import com.android.mvpp2p.R;

/**
 * Created by sll on 2015/3/13.
 */
public class EmptyState extends AbstractShowState {
  @Override public void show(boolean animate) {
    showViewById(R.id.epf_empty, animate);
  }

  @Override public void dismiss(boolean animate) {
    dismissViewById(R.id.epf_empty, animate);
  }
}
