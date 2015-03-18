package de.phonostar;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import android.os.SystemClock;
import android.view.View;

import android.util.Log;
import java.lang.String;

public class SoftKeyboard extends CordovaPlugin {

    private float xpos;
    private float ypos;

    public SoftKeyboard() {
    }

    public void showKeyBoard() {
      InputMethodManager mgr = (InputMethodManager) cordova.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
      mgr.showSoftInput(webView, InputMethodManager.SHOW_IMPLICIT);

      ((InputMethodManager) cordova.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(webView, 0);
    }

    public void hideKeyBoard() {
      InputMethodManager mgr = (InputMethodManager) cordova.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
      mgr.hideSoftInputFromWindow(webView.getWindowToken(), 0);
    }

    public int getWebViewWidth() {
      return webView.getWidth();
    }

    public int getWebViewHeight() {
      return webView.getHeight();
    }

    public boolean isKeyBoardShowing() {
      webView.setOnTouchListener(new View.OnTouchListener() { 
        @Override
        public boolean onTouch(View v, MotionEvent event) {
          xpos = (float) event.getX();
          ypos = (float) event.getY();
          Log.d("CordovaNIK", String.format("Last event was at %sx%s on the Android side", xpos, ypos));
          return false;
        }
      });
      int heightDiff = webView.getRootView().getHeight() - webView.getHeight();
      return (100 < heightDiff); // if more than 100 pixels, its probably a keyboard...
    }
    public boolean sendTap(final int posx, final int posy, final CallbackContext callbackContext) {
      cordova.getActivity().runOnUiThread(new Runnable() {
        public void run() {
          boolean up, down;
          up = webView.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, posx, posy, 0));
          down = webView.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, posx, posy, 0));
          if (!down || !up) {
            callbackContext.error("Failed sending key up+down event for coords " + posx + ", " + posy);
          } else {
            callbackContext.success("Succesfully sent key up+down event for coords " + posx + ", " + posy);
          }
        }
      });
      return true;
        
    }
    public boolean sendKey(final int keyCode, final CallbackContext callbackContext) {
      /*
       if (!isKeyBoardShowing()) {
        callbackContext.error("Unable to send key press for " + keyCode + ": Softkeyboard is not showing");
        return false;
      }
      */

      cordova.getActivity().runOnUiThread(new Runnable() {
        public void run() {
          boolean downResult, upResult;
          downResult = webView.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, keyCode));
          if (!downResult) {
            callbackContext.error("Failed sending keydown event for key " + keyCode);
            return;
          }

          upResult = webView.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, keyCode));
          if (upResult) {
            callbackContext.success("Last event's coords were: " + xpos + "x" + ypos);
          } else {
            callbackContext.error("Last event's coords were: " + xpos + "x" + ypos);
          }
        }
      });
      return true;
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) {
    if (action.equals("show")) {
      this.showKeyBoard();
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
    else if (action.equals("getWebViewWidth")) {
      callbackContext.success(getWebViewWidth());
      return true;
    }
    else if (action.equals("getWebViewHeight")) {
      callbackContext.success(getWebViewHeight());
      return true;
    }
    else if (action.equals("sendKey")) {
      try {
        int keyCode = args.getInt(0);
        return this.sendKey(keyCode, callbackContext);
      } catch (JSONException jsonEx) {
        callbackContext.error(jsonEx.getMessage());
        return false;
      }
    } 
    else if (action.equals("sendTap")) {
      try {
        int posx = args.getInt(0);
        int posy = args.getInt(1);
        return this.sendTap(posx, posy, callbackContext);
      } catch (JSONException jsonEx) {
        callbackContext.error(jsonEx.getMessage());
        return false;
      }
    }
    else {
      return false;
    }
  }
}


