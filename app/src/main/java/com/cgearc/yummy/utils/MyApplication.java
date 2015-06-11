/**
 * Copyright 2013 Ognyan Bankov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cgearc.yummy.utils;

import com.cgearc.yummy.R;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import android.app.Application;

/**
 * Application class for the demo. Used to ensure that MyVolley is initialized.
 * {@see MyVolley}
 * 
 * @author Ognyan Bankov
 * 
 */
public class MyApplication extends Application {
	private Tracker mTracker;

	@Override
	public void onCreate() {
		super.onCreate();

		init();
	}

	private void init() {
		MyVolley.init(this);
	}

	public synchronized Tracker getTracker() {

		GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
		if (mTracker == null) {
			this.mTracker = analytics.newTracker(R.xml.analytics);
		}
		return this.mTracker;
	}
}
