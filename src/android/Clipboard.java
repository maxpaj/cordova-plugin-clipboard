package com.danielsogl.cordova.clipboard;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.content.ClipboardManager;
import android.content.ClipData;
import android.content.ClipDescription;

public class Clipboard extends CordovaPlugin {
  
  private static final String actionCopy = "copy";
  private static final String actionPaste = "paste";

  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    ClipboardManager clipboard = (ClipboardManager) cordova.getActivity().getSystemService(Context.CLIPBOARD_SERVICE);

    if (action.equals(actionCopy)) {
      try {
        String text = args.getString(0);
        String mime = "text/plain";

        if(args.size() > 1) {
          mime = args.getString(1);
        }

        ClipData clip = null;

        switch(mime) {
          case "text/html":
            clip = ClipData.newHtmlText("HTML", text, text);
            clipboard.setPrimaryClip(clip);
            System.out.println("copied HTML " + text);
            break;
          case "text/plain":
          default:
            clip = ClipData.newPlainText("Text", text);
            clipboard.setPrimaryClip(clip);
            System.out.println("copied text " + text);
            break;
        }
        callbackContext.success(text);
        return true;
      } catch (JSONException e) {
        callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.JSON_EXCEPTION));
      } catch (Exception e) {
        callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, e.toString()));
      }
    } else if (action.equals(actionPaste)) {
      if (!clipboard.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
        callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.NO_RESULT));
      }

      try {
        ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
        String text = item.getText().toString();

        if (text == null) text = "";

        callbackContext.success(text);

        return true;
      } catch (Exception e) {
        callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, e.toString()));
      }
    }

    return false;
  }
}
