package com.cgearc.yummy;

import com.cgearc.yummy.utils.Setting;
//import com.samsung.android.sdk.SsdkUnsupportedException;
//import com.samsung.android.sdk.gesture.Sgesture;
//import com.samsung.android.sdk.gesture.SgestureHand;
//import com.samsung.android.sdk.gesture.SgestureHand.Info;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Fragment that appears in the "content_frame", shows a planet
 */
public class Frg_RecipeDetail extends Fragment {

	private static final String TAG = "nevin";
	
	static int DEFAULT_TEXT_SIZE=12;
	static int MAX_WIDTH  = 600;
	static int MAX_HEIGHT = 400;
	static int scale_ratio=80;

	
	WebView mWebView;
	String mBody;
	String mArticleId;
	String mUserName;
	AlertDialog levelDialog;
	class JsObject {
	    @JavascriptInterface
	    public void unistall() { 
	    	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle("真抱歉你不喜歡...");
			builder.setMessage("你願意給我一點意見嗎?");
			// Set an EditText view to get user input 
			final EditText input = new EditText(getActivity());
			builder.setView(input);

			builder.setPositiveButton("送出並移除", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
			  String value = input.getText().toString();
			  // Do something with value!
//			  Setting.getInstance(getActivity()).sendMail("cnevinchen@gmail.com", "Yummy Removed", value, null);
			  Intent intent = new Intent(Intent.ACTION_DELETE);
		    	intent.setData(Uri.parse("package:com.cgearc.yummy"));
		    	startActivity(intent);
			  }
			});

			builder.setNegativeButton("算了再給你一次機會", new DialogInterface.OnClickListener() {
			  public void onClick(DialogInterface dialog, int whichButton) {
			    // Canceled.
			  }
			});

			levelDialog = builder.create();
			levelDialog.show();
	    	
	    	}
	}
	 

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.act_recipe_detail, container, false);
//         this.setHasOptionsMenu(true);
        

        
		if (this.getArguments() != null && this.getArguments().getString("body")!=null){
			mBody = this.getArguments().getString("body");
			mArticleId = this.getArguments().getString("article_id");
			mUserName = this.getArguments().getString("user_name");
			
		}else
			mBody="Welcome!";
		mBody= mBody.replace("<img ", "<img style=\"max-width:80%; max-height:auto;\" ");
		mBody= mBody.replaceAll("(?i)(font-size): [0-9]+pt","");
//		Log.d(TAG,"------------"+mBody);

		mWebView = (WebView)rootView.findViewById(R.id.webView1);
		
		WebSettings wb = mWebView.getSettings();
		wb.setDefaultFontSize(++DEFAULT_TEXT_SIZE);
		mWebView.getSettings().setAppCacheMaxSize( 200 * 1024 * 1024 ); // 5MB
		mWebView.getSettings().setAppCachePath( this.getActivity().getCacheDir().getAbsolutePath() );
		mWebView.getSettings().setAllowFileAccess( true );
		mWebView.getSettings().setAppCacheEnabled( true );
		mWebView.getSettings().setJavaScriptEnabled( true );

		mWebView.addJavascriptInterface(new JsObject(), "injectedObject");
//		mWebView.getSettings().setCacheMode( WebSettings.LOAD_DEFAULT ); // load online by default

		wb.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		wb.setSupportZoom(true);
		wb.setBuiltInZoomControls(true);
		wb.setDisplayZoomControls(false);
		mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		String baseUrl="file://"+Environment.getExternalStorageDirectory().getPath()+"/myimg";
		mWebView.loadDataWithBaseURL(baseUrl, mBody, "text/html", "utf-8", null);

		return rootView;
	}
	
}

