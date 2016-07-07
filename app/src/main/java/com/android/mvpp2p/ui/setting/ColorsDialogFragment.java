package com.android.mvpp2p.ui.setting;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.android.mvpp2p.Constants;
import com.android.mvpp2p.R;
import com.android.mvpp2p.base.BaseActivity;
import com.android.mvpp2p.injector.HasComponent;
import com.android.mvpp2p.utils.RxBus;
import com.android.mvpp2p.utils.SettingPrefUtil;
import com.android.mvpp2p.utils.ThemeUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sll on 2016/5/17.
 */
public class ColorsDialogFragment extends DialogFragment
    implements AdapterView.OnItemClickListener {

  public static void launch(Activity activity) {
    Fragment fragment = activity.getFragmentManager().findFragmentByTag("DialogFragment");
    if (fragment != null) {
      activity.getFragmentManager().beginTransaction().remove(fragment).commit();
    }
    ColorsDialogFragment dialogFragment = new ColorsDialogFragment();
    dialogFragment.show(activity.getFragmentManager(), "DialogFragment");
  }


  private Map<String, ColorDrawable> colorMap = new HashMap<>();

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    SettingComponent.class.cast(((HasComponent<SettingComponent>) getActivity()).getComponent())
        .inject(this);
  }

  @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
    setCancelable(true);
    View view = View.inflate(getActivity(), R.layout.dialog_md_colors, null);
    GridView gridView = (GridView) view.findViewById(R.id.grid);
    gridView.setAdapter(new MDColorsAdapter());
    gridView.setOnItemClickListener(this);
    return new AlertDialogWrapper.Builder(getActivity()).setView(view)
        .setPositiveButton("取消", null)
        .create();
  }

  class MDColorsAdapter extends BaseAdapter {

    @Override public int getCount() {
      return ThemeUtil.themeColorArr.length;
    }

    @Override public Object getItem(int position) {
      return ThemeUtil.themeColorArr[position];
    }

    @Override public long getItemId(int position) {
      return position;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
      if (convertView == null) {
        convertView = View.inflate(getActivity(), R.layout.item_md_colors, null);
      }

      if (!colorMap.containsKey(String.valueOf(position))) {
        colorMap.put(String.valueOf(position),
            new ColorDrawable(getResources().getColor(ThemeUtil.themeColorArr[position][0])));
      }

      ImageView imgColor = (ImageView) convertView.findViewById(R.id.ivColor);
      ColorDrawable colorDrawable = colorMap.get(String.valueOf(position));
      imgColor.setImageDrawable(colorDrawable);

      View imgSelected = convertView.findViewById(R.id.ivSelected);
      imgSelected.setVisibility(
          SettingPrefUtil.getThemeIndex(getActivity()) == position ? View.VISIBLE : View.GONE);

      return convertView;
    }
  }

  @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    if (position == SettingPrefUtil.getThemeIndex(getActivity())) {
      dismiss();
      return;
    }
    SettingPrefUtil.setThemeIndex(getActivity(), position);
    dismiss();
    RxBus.get().post(Constants.CHANGE_THEME,"");
    if (getActivity() instanceof BaseActivity) ((BaseActivity) getActivity()).reload();
  }
}
