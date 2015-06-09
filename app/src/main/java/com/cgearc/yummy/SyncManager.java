package com.cgearc.yummy;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.util.JsonArrayRequest;
import com.android.volley.util.JsonObjectRequest;
import com.cgearc.yummy.DaoMaster.DevOpenHelper;
import com.cgearc.yummy.Frg_RecipeList.ArticleAdapter;
import com.cgearc.yummy.api.search.ApiArticle;
import com.cgearc.yummy.api.search.ApiSearchResult;

import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Query;
import retrofit.http.QueryMap;

public class SyncManager {
	private static final String TAG = "nevin";
	private static SyncManager mSyncManager;
	private Activity mActivity;
	
	public interface SyncObserver{
		public void receiveNewItem(Object result);
	}

	public static SyncManager getInstance(Activity act) {
		if (mSyncManager == null)
			return new SyncManager(act);
		else
			return mSyncManager;
	}

	public void getHotArticleFromJson( final SyncObserver adapter,final View view){
		Log.d(TAG,"----getHotArticleFromJson----");
		String json = null;
        try {
            InputStream is = this.mActivity.getAssets().open("hot.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            SyncManager mgr = SyncManager.getInstance(this.mActivity);
            
            json = new String(buffer, "UTF-8");
            JSONArray entries = new JSONArray(json);
            for (int i = 0; i < entries.length(); i++) {
            	
                
            	JSONObject entry = entries.getJSONObject(i);
            	
            	mgr.getOneArticlesFromPixnet(adapter, entry.getString("id"), entry.getString("user_name"),view);
            }
        } catch (JSONException e) {
			Log.v(TAG,e.toString());
		} catch (NumberFormatException e) {
			Log.v(TAG,e.toString());
		} catch (IOException e) {
			Log.v(TAG,e.toString());
        } 
	}
	public void getPresetArticlesFromLocalJson(String fileName , final SyncObserver adapter,final View view){
		Log.d(TAG,"----getPresetArticlesFromLocalJson m----");
		String json = null;
        try {
            InputStream is = this.mActivity.getAssets().open(fileName+".json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            
            json = new String(buffer, "UTF-8");
            JSONObject content = new JSONObject(json);
            JSONArray entries = content.getJSONArray("articles");
			// Describe the data set that we are dealing with
			Setting.TOTAL_ROWS = content.getInt("total");
			//	Setting.PER_PAGE = response.getInt("per_page"); // set perpage in settings
			Setting.CURRENT_PAGE = content.getInt("page");
			
			// TODO: should not touch activity resource here
			// mActivity.getActionBar().setSubtitle(formatter.format(Setting.TOTAL_ROWS)+ "�ӵ��G");
			for (int i = 0; i < entries.length(); i++) {

				JSONObject entry = entries.getJSONObject(i);
				getOneArticlesFromPixnet(
						adapter,
						entry.getString("id"),
						entry.getJSONObject("user").getString("name"),
						view);
			}	
        } catch (JSONException e) {
			Log.v(TAG,e.toString());
		} catch (NumberFormatException e) {
			Log.v(TAG,e.toString());
		} catch (IOException e) {
			Log.v(TAG,e.toString());
        } 
	}

	public interface SearchArticleService {
		@GET("/blog/articles/search")
		List<ApiSearchResult> listRepos(@QueryMap HashMap<String, String> options);
	}
	public void searchArticlesByKeyword(final SyncObserver adapter, final  String keyword, final View view){



		new Thread(new Runnable(){
			@Override
			public void run() {

				RestAdapter restAdapter = new RestAdapter.Builder()
						.setEndpoint("http://emma.pixnet.cc")
						.build();
				SearchArticleService service = restAdapter.create(SearchArticleService.class);
				HashMap<String, String> options = new HashMap<String, String>();
				options.put("key",keyword);
				options.put("category","27");
				List<ApiSearchResult> repos = service.listRepos(options);
				for (int i = 0; i < repos.size(); i++) {
					ApiSearchResult result = repos.get(i);
					for (ApiArticle a : result.getArticles()){
						getOneArticlesFromPixnet(
								adapter,
								a.getId(),
								a.getUser().getName(),
								view);
					}

				}
			}
		}).start();

		// http://emma.pixnet.cc/blog/articles/search?category=27&key={keyword}&per_page=80


//		Log.d(TAG,"----getArticlesByKeyword----");
//		Setting.CURRENT_PAGE++;
//		String api_url = "http://emma.pixnet.cc/blog/articles/search?category=27&key="
//				+ URLEncoder.encode(keyword+"")+"&per_page="+Setting.PER_PAGE + "&page="+Setting.CURRENT_PAGE;
//		JsonObjectRequest jsonRequest = new JsonObjectRequest(Method.GET,
//				api_url, null, new Response.Listener<JSONObject>() {
//					@Override
//					public void onResponse(JSONObject response) {
//
//						try {
//
//							JSONArray entries = response.getJSONArray("articles");
//							// Describe the data set that we are dealing with
//							Setting.TOTAL_ROWS = response.getInt("total");
//							Setting.PER_PAGE = response.getInt("per_page");
//							Setting.CURRENT_PAGE = response.getInt("page");
//
//							// TODO: should not touch activity resource here
////							mActivity.getActionBar().setSubtitle(formatter.format(Setting.TOTAL_ROWS)+ "�ӵ��G");
//							for (int i = 0; i < entries.length(); i++) {
//
//								JSONObject entry = entries.getJSONObject(i);
//								getOneArticlesFromPixnet(
//										adapter,
//										entry.getString("id"),
//										entry.getJSONObject("user").getString("name"),
//										view);
//							}
//						} catch (JSONException e) {
//							showErrorDialog(e);
//						} catch (NumberFormatException e) {
//							showErrorDialog(e);
//						}
//					}
//				},new Response.ErrorListener() {
//					@Override
//					public void onErrorResponse(VolleyError error) {
//						view.setVisibility(View.GONE);
//						Log.e(TAG, "searchArticlesByKeyword :" + error.toString());
//						showErrorDialog(error);
//					}
//				});
//			MyVolley.getRequestQueue().add(jsonRequest);
		}

	public void getHotArticleFromCDN(final SyncObserver adapter, final View view) {

		String api_url = "http://nevin-lab.appspot.com/data/hot.json";

		JsonArrayRequest jsonRequest = new JsonArrayRequest(api_url,
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						try {
							for (int i = 0; i < response.length(); i++) {
								JSONObject entry = response.getJSONObject(i);
								getOneArticlesFromPixnet(adapter,
										entry.getString("id"),
										entry.getString("user_name"), view);
							}
						} catch (JSONException e) {
							showErrorDialog(e);
						} catch (NumberFormatException e) {
							showErrorDialog(e);
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						view.setVisibility(View.GONE);
						Log.e(TAG, "getHotArticleFromCDN:" + error.toString());
						showErrorDialog(error);
					}
				});
		MyVolley.getRequestQueue().add(jsonRequest);
	}
 
	public void getHotArticleFromPixnet(final SyncObserver adapter, final View view){

		String api_url = "http://emma.pixnet.cc/mainpage/blog/categories/hot/27?page=1&count="+Setting.PER_PAGE;

		JsonArrayRequest jsonRequest = new JsonArrayRequest(
				api_url, new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						
						try {

							// Describe the data set that we are dealing with
							for (int i = 0; i < response.length(); i++) {

								JSONObject entry = response.getJSONObject(i);

								getOneArticlesFromPixnet(adapter, entry.getString("id"), entry.getString("user_name"),view);
							}
						} catch (JSONException e) {
							showErrorDialog(e);
						} catch (NumberFormatException e) {
							showErrorDialog(e);
						}
					}
				},new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						view.setVisibility(View.GONE);
						Log.e(TAG, "updateTableReqError:" + error.toString());
						showErrorDialog(error);
					}
				});
			MyVolley.getRequestQueue().add(jsonRequest);
		}
	
	public void getFavoriteArticlesFromLocalDB(final ArticleAdapter adapter,  final View view){

		DevOpenHelper helper = new DaoMaster.DevOpenHelper(this.mActivity,
				"foodabc-db", null);
		SQLiteDatabase db = helper.getWritableDatabase();
		DaoMaster mDaoMaster = new DaoMaster(db);
		DaoSession mDaoSession = mDaoMaster.newSession();
		ArticleDao adao = mDaoSession.getArticleDao();
		FavoriteDao fdao = mDaoSession.getFavoriteDao();
		List<Favorite> a = fdao.queryBuilder().list();
		List<Article> articles = adao.queryBuilder().list();
		mDaoSession.clear();
		db.close();
		
		view.setVisibility(View.GONE);
		adapter.getArticles().addAll(articles);
		adapter.notifyDataSetChanged();
		
	}
	public void getOneArticlesFromPixnet(final SyncObserver adapter,final String article_id, String user_name, final View view) {
		// Update User Table
		String api_url = "http://emma.pixnet.cc/blog/articles/"+article_id+"?user="+user_name;
		JsonObjectRequest jsonRequest = new JsonObjectRequest(Method.GET,
				api_url, null, new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						try {
							view.setVisibility(View.GONE);
							JSONObject entry= response.getJSONObject("article");
							Article article = new Article();
							article.setId(Long.valueOf(article_id));
							article.setArticle_id(article_id);
							article.setBody(entry.getString("body"));
							article.setComments_count(entry.getJSONObject("info").getString("comments_count"));
							article.setHits_daily(entry.getJSONObject("hits").getString("daily"));
							article.setHits_total(entry.getJSONObject("hits").getString("total"));
							article.setLink(entry.getString("link"));
							article.setPublic_at(entry.getString("public_at"));
							if (entry.getString("thumb").equals("http://s.pixfs.net/mobile/images/blog/article-image.png"))
								return;	// don't want a recipe without thumb
							article.setTitle(entry.getString("title"));
//							article.setUser_name(entry.getJSONObject("user").getString("name"));
							String link = entry.getString("link");
							String user_name = link.substring(link.indexOf("/"),link.indexOf("."));
							article.setUser_name(user_name);
							article.setDisplay_name(entry.getJSONObject("user").getString("display_name"));								
							article.setAvatar(entry.getJSONObject("user").getString("cavatar"));								
							
							JSONArray imgArray = entry.getJSONArray("images");
							for (int j = 0; j < imgArray.length(); j++) {
								JSONObject imgJ = imgArray.getJSONObject(j);
								Picture p = new Picture() ;
								if (j==0) article.setThumb(imgJ.getString("url"));
								if (imgJ.getString("url")==null)return;
								p.setHeight(imgJ.getString("height"));
								p.setWidth(imgJ.getString("width"));
								p.setUri(imgJ.getString("url"));
								p.setArticle_id(Long.valueOf(article_id));
								
							}
							adapter.receiveNewItem(article);
						} catch (JSONException e) {
							showErrorDialog(e);
						} catch (NumberFormatException e) {
							showErrorDialog(e);
						}
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						view.setVisibility(View.GONE);
						Log.e(TAG, "getOneArticlesFromPixnet :" + error.toString());
						showErrorDialog(error);
					}
				});
		MyVolley.getRequestQueue().add(jsonRequest);
	}

	
	private SyncManager(Activity act) {

		mActivity = act;
	}

	
	// Display error message to user. depreciated
	private void showErrorDialog(Exception e) {
		AlertDialog.Builder b = new AlertDialog.Builder(this.mActivity);
		b.setMessage("Error! " + e.getLocalizedMessage());
		//		b.show();
	}


	
}
