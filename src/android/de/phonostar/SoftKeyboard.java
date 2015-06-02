package de.phonostar;

import org.json.JSONArray;

import android.R;
import android.graphics.Rect;
import android.view.View;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import android.app.Activity;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;

import android.util.Log;

public class SoftKeyboard extends CordovaPlugin {

    boolean softKeyBoardIsShowing = false;
    CallbackContext todoAtContext;

    public SoftKeyboard() {

    }

    @Override
    protected void pluginInitialize() {

        this.initPlugin();
        Log.d("SoftKeyboard", "::private init::");

    }

    public void initPlugin() {

        final View activityRootView = (View)webView.getParent();
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                //be sure that keyboard showing is finished
                try {
                    Thread.sleep(250);
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                Rect r = new Rect();
                //r will be populated with the coordinates of your view that area still visible.
                activityRootView.getWindowVisibleDisplayFrame(r);

                int heightDiff = activityRootView.getRootView().getHeight() - (r.bottom - r.top);

                if (heightDiff > 100) { // if more than 100 pixels, its probably a keyboard...

                    softKeyBoardIsShowing = true;
                    Log.d("isKeyBoardShowing", "::true::");

                } else {

                    softKeyBoardIsShowing = false;
                    Log.d("isKeyBoardShowing", "::false::");
                }
                Log.d("isKeyBoardShowing::height::", Integer.toString(heightDiff));

                PluginResult dataResult = new PluginResult(PluginResult.Status.OK, heightDiff);
                dataResult.setKeepCallback(true);
                todoAtContext.sendPluginResult(dataResult);
            }
        });

    }

    public void toggleKeyBoard() {
        InputMethodManager mgr = (InputMethodManager) cordova.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

      mgr.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
    }

    public void hideKeyBoard() {
      InputMethodManager mgr = (InputMethodManager) cordova.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

      mgr.hideSoftInputFromWindow(webView.getWindowToken(), 0);
    }

    public boolean isKeyBoardShowing() {

        return softKeyBoardIsShowing;

    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) {
    if (action.equals("onToggle")) {
        todoAtContext = callbackContext;
        return true;
    }
    else if (action.equals("show")) {
      this.toggleKeyBoard();
      callbackContext.success("done");
      return true;
    }
    else if (action.equals("hide")) {
      this.hideKeyBoard();
      callbackContext.success();
      return true;
    }
    else if (action.equals("isShowing")) {
      callbackContext.success(Boolean.toString(this.isKeyBoardShowing()));
      return true;
    }
    else {
      return false;
    }
  }
}

