package com.cgearc.yummy;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.cgearc.yummy.Frg_RecipeList.SearchCompletedListener;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.method.ScrollingMovementMethod;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
 
/**
 * Fragment that appears in the "content_frame", shows a planet
 */
public class Act_RecipeSearch extends Activity implements OnQueryTextListener,SearchCompletedListener {
	private static final String TAG = "nevin";
	ListView mListView;
	ProgressBar mProgressBar;
	private SearchView mSearchView;
	String[] item = new String[] {
			"電鍋","麵包","素食",
			"滷味","懷孕","蛋糕",
			"點心","減肥","便當",
			"嬰兒","副食品","泰式" };
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_recipe_search);
		getActionBar().setDisplayHomeAsUpEnabled(true);

//		int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
//        int newUiOptions = uiOptions;
//        boolean isImmersiveModeEnabled =
//                ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
//        if (Build.VERSION.SDK_INT >= 14) {
//            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
//        }
//        if (Build.VERSION.SDK_INT >= 16) {
//            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
//        }
//        if (Build.VERSION.SDK_INT >= 18) {
//            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
//        
//		}
//        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);

//        getWindow().getDecorView().setSystemUiVisibility(
//                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//              | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//              | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//              | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//              | View.SYSTEM_UI_FLAG_FULLSCREEN
//              | View.SYSTEM_UI_FLAG_IMMERSIVE)        
		mListView = (ListView) this.findViewById(R.id.lv_tag);
		mProgressBar = (ProgressBar)this.findViewById(R.id.pb_r_search);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1, item);
		
		
		mListView.setOnItemClickListener(new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View view,
		        int position, long id) { 
//		    	Log.d(TAG,position+"."+ id + "pressed");
//		    	Frg_RecipeList fragment = new Frg_RecipeList();
//				FragmentManager fragmentManager = getFragmentManager();
//		        fragmentManager.beginTransaction().replace(R.id.recipe_container, fragment).commit();
//		        Bundle args = new Bundle();
//		        args.putString("query",item[(int) id] );
//		        fragment.setArguments(args);
//		        mListView.setVisibility(View.INVISIBLE);
//		        mProgressBar.setVisibility(View.VISIBLE);
		    	if (id <0 ) return;
		        Intent returnIntent = new Intent();
		        returnIntent.putExtra("keyword",item[(int) id]);
		        returnIntent.putExtra("localFileId",String.valueOf(id));
		        Setting.allArticles.clear();
		        setResult(RESULT_OK,returnIntent);
		        finish();
		        
		    }
		});
		TextView tv= new TextView(this);
		tv.setText("熱門關鍵字");
		tv.setTextSize(30f);
		tv.setPadding(5, 0, 0, 0);
		tv.setClickable(false);
		tv.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if (mSearchView!=null)
					mSearchView.requestFocusFromTouch();
				
			}});
		tv.setTextColor(getResources().getColor(android.R.color.darker_gray));
		mListView.addHeaderView(tv);
		
		mListView.setAdapter(adapter);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.search, menu);

	    return true;
	}
	@Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        MenuItem searchItem = menu.findItem(R.id.action_searchview);
        mSearchView = (SearchView) searchItem.getActionView();
        mSearchView.setIconifiedByDefault(false) ;
        // mSearchView.requestFocus();
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setQueryHint("輸入食譜關鍵字");
        return super.onPrepareOptionsMenu(menu);
    }

	@Override
	public boolean onQueryTextChange(String arg0) {
		// only take action when search keyword is submited
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String arg0) {
		
//		mProgressBar.setVisibility(View.VISIBLE);
//		Frg_RecipeList fragment = new Frg_RecipeList();
//		FragmentManager fragmentManager = getFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.recipe_container, fragment).commit();
//        Bundle args = new Bundle();
//        args.putString("query",arg0 );
//        fragment.setArguments(args);
		Intent returnIntent = new Intent();
        returnIntent.putExtra("keyword",arg0);
        setResult(RESULT_OK,returnIntent);
        finish();
		return false;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		switch (id) {
		case android.R.id.home:
			finish();
			overridePendingTransition(R.anim.activity_back_in,
					R.anim.activity_back_out);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onSearchCompleted() {
		mProgressBar.setVisibility(View.INVISIBLE);
		
	}
}
