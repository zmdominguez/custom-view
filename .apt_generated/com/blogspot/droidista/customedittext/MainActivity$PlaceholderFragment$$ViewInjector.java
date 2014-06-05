// Generated code from Butter Knife. Do not modify!
package com.blogspot.droidista.customedittext;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class MainActivity$PlaceholderFragment$$ViewInjector {
  public static void inject(Finder finder, final com.blogspot.droidista.customedittext.MainActivity.PlaceholderFragment target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131230723, "field 'mNotRequiredText'");
    target.mNotRequiredText = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131230722, "field 'mRequiredText'");
    target.mRequiredText = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131230724, "field 'mGoButton' and method 'onClick'");
    target.mGoButton = (android.widget.Button) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
  }

  public static void reset(com.blogspot.droidista.customedittext.MainActivity.PlaceholderFragment target) {
    target.mNotRequiredText = null;
    target.mRequiredText = null;
    target.mGoButton = null;
  }
}
