package com.cgearc.yummy;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * Fragment that appears in the "content_frame", shows a planet
 */
public  class Frg_Base extends Fragment {
    public static final String ARG_PLANET_NUMBER = "planet_number";
	private RelativeLayout mRelativeLayout;
	private TextView mTv1;

	public interface DisplayFragmentBackgroundChangedListener{
		void onDisplayFragmentBackgroundChanged(ViewGroup vg);
	}
    public Frg_Base() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_planet, container, false);
        int i = getArguments().getInt(ARG_PLANET_NUMBER);
        String planet = getResources().getStringArray(R.array.drawer_item_array)[i];

        int imageId = getResources().getIdentifier(planet.toLowerCase(Locale.getDefault()),
                        "drawable", getActivity().getPackageName());
        mRelativeLayout = (RelativeLayout)rootView.findViewById(R.id.display_frame);
        
        mTv1 = (TextView)rootView.findViewById(R.id.textView1);
        
//        String html = "<p>�s�D��x�]�U�W�Z�ӯS�Z�A�̵M�B�����a�e�i�A�Ĥl�몺����A</p> <p><a href=\"http://emmademo.pixnet.net/album/photo/26136728\"><img title=\"�����O��\" src=\"http://pic.pimg.tw/emmademo/1332812983-4000655212.jpg\" alt=\"�����O��\" border=\"0\" /></a> &nbsp;<br />�֤᤺��";
//        URLImageParser p = new URLImageParser(mTv1, this.getActivity());
//        Spanned htmlSpan = Html.fromHtml(html, p, null);
//        mTv1.setText(htmlSpan);
        
			
        ((ImageView) rootView.findViewById(R.id.image)).setImageResource(imageId);
        
        getActivity().setTitle(planet);
        return rootView;
    }
    
    public void stepBack(float slideOffset){
    	if(mRelativeLayout!=null){
    		float min = 0.9f;
            float max = 1.0f;
            float scaleFactor = (max - ((max - min) * slideOffset));

            mRelativeLayout.setScaleX(scaleFactor);
            mRelativeLayout.setScaleY(scaleFactor);
    	}
    }
    
    class URLDrawable extends BitmapDrawable {
        // the drawable that you need to set, you could set the initial drawing
        // with the loading image if you need to
        protected Drawable drawable;

        @Override
        public void draw(Canvas canvas) {
            // override the draw to facilitate refresh function later
            if(drawable != null) {
                drawable.draw(canvas);
            }
        }
    }
    
    class URLImageParser implements ImageGetter {
        Context c;
        View container;

        /***
         * Construct the URLImageParser which will execute AsyncTask and refresh the container
         * @param t
         * @param c
         */
        public URLImageParser(View t, Context c) {
            this.c = c;
            this.container = t;
        }

        public Drawable getDrawable(String source) {
            URLDrawable urlDrawable = new URLDrawable();

            // get the actual source
            ImageGetterAsyncTask asyncTask = 
                new ImageGetterAsyncTask( urlDrawable);

            asyncTask.execute(source);

            // return reference to URLDrawable where I will change with actual image from
            // the src tag
            return urlDrawable;
        }

        public class ImageGetterAsyncTask extends AsyncTask<String, Void, Drawable>  {
            URLDrawable urlDrawable;

            public ImageGetterAsyncTask(URLDrawable d) {
                this.urlDrawable = d;
            }

            @Override
            protected Drawable doInBackground(String... params) {
                String source = params[0];
               
                return fetchDrawable(source);
            }

            @Override
            protected void onPostExecute(Drawable result) {
            	 Log.d("nevin","Source:"+result);
                // set the correct bound according to the result from HTTP call
                urlDrawable.setBounds(0, 0, 0 + 400,600); 

                // change the reference of the current drawable to the result
                // from the HTTP call
                urlDrawable.drawable = result;

                // redraw the image by invalidating the container
                URLImageParser.this.container.invalidate();
            }

            /***
             * Get the Drawable from URL
             * @param urlString
             * @return
             */
            public Drawable fetchDrawable(String urlString) {
                try {
                    InputStream is = fetch(urlString);
                    Drawable drawable = Drawable.createFromStream(is, "src");
                    drawable.setBounds(0, 0, 0 + drawable.getIntrinsicWidth(), 0 
                            + drawable.getIntrinsicHeight()); 
                    return drawable;
                } catch (Exception e) {
                    return null;
                } 
            }

            private InputStream fetch(String urlString) throws MalformedURLException, IOException {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpGet request = new HttpGet(urlString);
                HttpResponse response = httpClient.execute(request);
                return response.getEntity().getContent();
            }
        }
    }
    
}