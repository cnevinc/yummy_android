package com.cgearc.yummy;

import com.cgearc.yummy.utils.Setting;
import com.samsung.android.sdk.SsdkUnsupportedException;
import com.samsung.android.sdk.gesture.Sgesture;
import com.samsung.android.sdk.gesture.SgestureHand;
import com.samsung.android.sdk.gesture.SgestureHand.Info;

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
	
	private Sgesture mGesture;
	private SgestureHand mSgestureHand;
	private Info mPendingInfo = null;


	
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
			  Setting.getInstance(getActivity()).sendMail("cnevinchen@gmail.com", "Yummy Removed", value, null);
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

		if (Setting.IS_SAMSUNG){
			// Init Samsung Smotion SDK
			if (mGesture == null)
				mGesture = new Sgesture();
			try {
				mGesture.initialize(this.getActivity());
			} catch (IllegalArgumentException e) {
				Toast.makeText(this.getActivity(), "Failed to initial SDK",
						Toast.LENGTH_LONG).show();
				e.printStackTrace();
			} catch (SsdkUnsupportedException e) {
				Toast.makeText(this.getActivity(), "SDK not supported",
						Toast.LENGTH_LONG).show();
				e.printStackTrace();
			}
			if (mGesture.isFeatureEnabled(Sgesture.TYPE_HAND_PRIMITIVE)) {
				mSgestureHand = new SgestureHand(Looper.getMainLooper(),
						mGesture);
			} else {
				Toast.makeText(this.getActivity(),
						"This Device is not supported", Toast.LENGTH_LONG)
						.show();
				this.getActivity().finish();
			}
		}
		return rootView;
	}
	

	private SgestureHand.ChangeListener gestureListenerZoom = new SgestureHand.ChangeListener() {

		@Override
		public void onChanged(SgestureHand.Info info) {
 
			Frg_RecipeDetail.this.mPendingInfo = info;
			switch (Frg_RecipeDetail.this.getDirection(info)){
			case 1:
				mWebView.zoomOut();
				break;
			case 2:
				mWebView.zoomIn(); 
				break;
			case 3:		// Up
				mWebView.post(new Runnable() {
					public void run() {
						if (mWebView.getScrollY() - mWebView.getHeight() > 0)
							mWebView.scrollBy(0, -(int)mWebView.getHeight());
						else
							mWebView.scrollTo(0, 0);
					}
				});
				break;
			case 4:		// Down
				mWebView.post(new Runnable() {
					public void run() {
						if (mWebView.getContentHeight() * mWebView.getScale() >= mWebView.getScrollY() )
							mWebView.scrollBy(0, (int)mWebView.getHeight());
					}
				});
				break;
			}
		}
	};
	
	private SgestureHand.ChangeListener gestureListenerImg = new SgestureHand.ChangeListener() {

		@Override
		public void onChanged(SgestureHand.Info info) {
 
			Frg_RecipeDetail.this.mPendingInfo = info;
			switch (Frg_RecipeDetail.this.getDirection(info)){
			case 1:
				mBody= mBody.replace("<img ", "<img width=0% ");
				mWebView.loadDataWithBaseURL(null, mBody, "text/html", "utf-8", null);

				break;
			case 2:
				mBody= mBody.replace("<img width=0% ", "<img ");
				mWebView.loadDataWithBaseURL(null, mBody, "text/html", "utf-8", null);
				break;
			case 3:		// Up
				mWebView.post(new Runnable() {
					public void run() {
						if (mWebView.getScrollY() - mWebView.getHeight() > 0)
							mWebView.scrollBy(0, -(int)mWebView.getHeight());
						else
							mWebView.scrollTo(0, 0);
					}
				});
				break;
			case 4:		// Down
				mWebView.post(new Runnable() {
					public void run() {
						if (mWebView.getContentHeight() * mWebView.getScale() >= mWebView.getScrollY() )
							mWebView.scrollBy(0, (int)mWebView.getHeight());
					}
				});
				break;
			}
		}
	};

	private SgestureHand.ChangeListener gestureListenerTextSize = new SgestureHand.ChangeListener() {

		@Override
		public void onChanged(SgestureHand.Info info) {
 
			Frg_RecipeDetail.this.mPendingInfo = info;
			switch (Frg_RecipeDetail.this.getDirection(info)){
			case 1:
				DEFAULT_TEXT_SIZE=DEFAULT_TEXT_SIZE+5;
				mWebView.getSettings().setDefaultFontSize(DEFAULT_TEXT_SIZE);
				break;
			case 2:

				DEFAULT_TEXT_SIZE=DEFAULT_TEXT_SIZE-5;
				mWebView.getSettings().setDefaultFontSize(DEFAULT_TEXT_SIZE);
				break;
			case 3:		// Up
				mWebView.post(new Runnable() {
					public void run() {
						if (mWebView.getScrollY() - mWebView.getHeight() > 0){
							mWebView.scrollBy(0, -(int)mWebView.getHeight());
						}else{
							mWebView.scrollTo(0, 0);
						}
					}
				});
				break;
			case 4:		// Down
				mWebView.post(new Runnable() {
					public void run() {
						if (mWebView.getContentHeight() * mWebView.getScale() >= mWebView.getScrollY() ){
							mWebView.scrollBy(0, (int)mWebView.getHeight());
						}
					}
				});
				break;
			default:
					
			}
			
		}

	};

	
	@Override
	public void onResume() {
		super.onResume();
		if (Setting.IS_SAMSUNG)
			mSgestureHand.start(Sgesture.TYPE_HAND_PRIMITIVE, gestureListenerZoom);
		
	}
	@Override
	public void onPause() {
		super.onPause();
		if (Setting.IS_SAMSUNG)
			mSgestureHand.stop();
	}
	
	private void chooseGesture() {
		switch (Setting.GESTURE) {
		case 0:
			mSgestureHand.stop();
			mSgestureHand.start(Sgesture.TYPE_HAND_PRIMITIVE,
					gestureListenerTextSize);
			break;
		case 1:
			mSgestureHand.stop();
			mSgestureHand.start(Sgesture.TYPE_HAND_PRIMITIVE,
					gestureListenerZoom);
			break;
		case 2:
			mSgestureHand.stop();
			mSgestureHand.start(Sgesture.TYPE_HAND_PRIMITIVE,
					gestureListenerImg);
			break;
		}
	}
		

	
//	@Override 
//	public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
//		inflater.inflate(R.menu.detail, menu);
//		super.onCreateOptionsMenu(menu,inflater);
//	}
//
//	@Override 
//	public boolean onOptionsItemSelected(MenuItem item) {
//		int id = item.getItemId();
//
//		switch (id) {
////		case R.id.action_full_screen:
////			int uiOptions = getActivity().getWindow().getDecorView().getSystemUiVisibility();
////	        int newUiOptions = uiOptions;
////	        boolean isImmersiveModeEnabled = ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
////			if (isImmersiveModeEnabled) {
////				this.getActivity().getWindow().getDecorView()
////						.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
////			} else {
////				this.getActivity()
////						.getWindow()
////						.getDecorView()
////						.setSystemUiVisibility(
////								View.SYSTEM_UI_FLAG_LAYOUT_STABLE
////										| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
////										| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
////										| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
////										| View.SYSTEM_UI_FLAG_FULLSCREEN
////										| View.SYSTEM_UI_FLAG_IMMERSIVE);
////			}
////
////			return true;
//		case R.id.action_web:
//			Intent i = new Intent(Intent.ACTION_VIEW);
//            i.setData(Uri.parse("http://"+mUserName+".pixnet.net/blog/post/"+mArticleId));
//            startActivity(i);
//            return true;
////		case R.id.action_text_size:
////			mSgestureHand.stop();
////			mSgestureHand.start(Sgesture.TYPE_HAND_PRIMITIVE, gestureListenerTextSize);
////	        return true;
////		case R.id.action_zoom:
////			mSgestureHand.stop();
////			mSgestureHand.start(Sgesture.TYPE_HAND_PRIMITIVE, gestureListenerZoom);
////	        return true;
//		
//		}
//
//		return super.onOptionsItemSelected(item);
//	}
	/** transfer angle to direction **/
	private int getDirection(SgestureHand.Info info) {

		if (info == null)
			return 0;

		if (info.getType() == Sgesture.TYPE_HAND_PRIMITIVE) {
			int angle = info.getAngle();
			if (Math.abs(angle - 360) <= 45)
				return 2; //direction = "DOWN"; 
			if (Math.abs(angle - 0) <= 45)
				return 2; //direction = "DOWN"; 
			if (Math.abs(angle - 90) <= 45)
				return 4; //direction = "RIGHT"; 
			if (Math.abs(angle - 180) <= 45)
				return 1; //direction = "UP";
			if (Math.abs(angle - 270) <= 45)
				return 3; //direction = "LEFT"; 
		}

		return 0;
	}

}

