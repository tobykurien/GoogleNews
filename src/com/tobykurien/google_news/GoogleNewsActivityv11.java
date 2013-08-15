package com.tobykurien.google_news;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class GoogleNewsActivityv11 extends GoogleNewsActivity {
   protected float startX;
   protected float startY;

   @Override
   public void onCreate(Bundle savedInstanceState) {
      v11 = true; // prevent recursive activity redirects
      super.onCreate(savedInstanceState);
      
      getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                              WindowManager.LayoutParams.FLAG_FULLSCREEN);
      
      // setup actionbar
      ActionBar ab = getActionBar();
      ab.setDisplayShowTitleEnabled(false);
      ab.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
      ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
               android.R.layout.simple_list_item_1,
               android.R.id.text1,
               getResources().getStringArray(R.array.sites));
      ab.setListNavigationCallbacks(adapter, new OnNavigationListener() {
         @Override
         public boolean onNavigationItemSelected(int arg0, long arg1) {
            String url = getResources().getStringArray(R.array.sites_url)[arg0];
            openSite(url);
            return true;
         }
      });
      
      autohideActionbar();
   }
   
   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      boolean ret = super.onCreateOptionsMenu(menu);
      menu.findItem(R.id.menu_site).setVisible(false);
      return ret;
   }

   /**
    * Attempt to make the actionBar auto-hide and auto-reveal based on drag,
    * but unfortunately makes the bit under the actionbar mostly inaccessible,
    * so leaving this out for now.
    * @param activity
    * @param wv
    */
   public void autohideActionbar() {
      wv.setOnTouchListener(new OnTouchListener() {
         @Override
         public boolean onTouch(View arg0, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
               startY = event.getY();
            }

            if (event.getAction() == MotionEvent.ACTION_MOVE) {
               // avoid juddering by waiting for large-ish drag
               if (Math.abs(startY - event.getY()) > 
                  new ViewConfiguration().getScaledTouchSlop() * 5) {
                  if (startY < event.getY()) 
                     getActionBar().show();
                  else
                     getActionBar().hide();
               }
            }

            return false;
         }
      });
   }
}