package com.cgearc.yummy.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.sql.DataSource;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.cgearc.yummy.dao.Article;

/* 
 *  
 * Provide interface to default preference 
 * 
 */
public class Setting implements OnSharedPreferenceChangeListener {
	public static final boolean IS_SAMSUNG= false;
	public static final String SERVICE_MAIL_ACCOUNT = "cnevinchen@gmail.com";
	public static final String SERVICE_MAIL_PASSWORD = "931593943";
	
	public static ArrayList<Article> allArticles;
	
	public static HashMap<String , ArrayList<Article>> RESULT_CACHE = new HashMap<String , ArrayList<Article>>(); 
	
	// TODO: Setup below to avoid hard coded package/activity name
//	public static final String DEFAULT_LOCK_SCREEN_ACTIVITY = Act_GestureTest.class.getName();//  Act_GestureTest.class.getName();
//	public static final String DEFAULT_LOCK_SCREEN_ACTIVITY_SIMPLE = Act_GestureTest.class.getSimpleName();//  Act_GestureTest.class.getName();

	public static Setting getInstance(Context context) {
		return mInstance == null ? (mInstance = new Setting(context))
				: mInstance;
	}
	
	public boolean isWeeklyReport() {
		return mWeeklyReport;
	}

	public boolean isAutoStart() {
		return mAutoStart;
	}

	public boolean isServiceEnabled() {
		return mServiceEnabled;
	}

	public boolean isFirstUse() {
		return mFirstUse;
	}

	public void setFirstUse(boolean first) {
		mFirstUse = first;
		mPref.edit().putBoolean(PREF_FIRST_USE, first).commit();
		this.reloadPreferences();
	}
	
	public String[] getApplicationList() {
		return mApplicationList;
	}

	public int getRelockTimeout() {
		return mRelockTimeout;
	}

	public String getPassword() {
		return mPassword;
	}

	public void setPassword(String password) {
		mPassword = password;
		mPref.edit().putString(PREF_PASSWORD, password).commit();
		this.reloadPreferences();
	}

	public String getGestureSequence() {
		return mGestureSequence;
	}

	public void setGestureSequence(String gesture) {
		Log.d(Setting.TAG,"Saving pass====="+gesture); //LRD
		mGestureSequence = gesture;
		mPref.edit().putString(PREF_GESTURE, gesture).commit();
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		reloadPreferences();
	}


	/** Private Members **/
	private static final String PREF_SERVICE_ENABLED = "service_enabled";
	private static final String PREF_APPLICATION_LIST = "application_list";
	private static final String PREF_AUTO_START = "start_service_after_boot";
	private static final String PREF_PASSWORD = "password";
	private static final String PREF_GESTURE = "gesture";
	private static final String PREF_RELOCK_POLICY = "relock_policy";
	private static final String PREF_RELOCK_TIMEOUT = "relock_timeout";
	private static final String PREF_FIRST_USE = "first_use";
	private static final String PREF_WEEKLY_REPORT = "weekly_report";

	public static final String TAG = "nevin";
	public static final String APP_VERSION = "App Version";
	public static final String LOCKED_APP = "Locked App";
	
	private SharedPreferences mPref;
	private static Setting mInstance;
	public static int TOTAL_ROWS;
	public static int PER_PAGE = 30;
	public static int CURRENT_PAGE ;
	public static int GESTURE;
	private boolean mServiceEnabled, mAutoStart,mWeeklyReport ;
	private String[] mApplicationList;
	private String mPassword;
	private int mRelockTimeout;
	private String mGestureSequence;
	private boolean mFirstUse;
	private Context mContext;
	
	
	private Setting(Context context) {
		mPref = PreferenceManager.getDefaultSharedPreferences(context);
		mPref.registerOnSharedPreferenceChangeListener(this);
		reloadPreferences();
		mContext = context;
		RESULT_CACHE.put("hot", new ArrayList<Article>());
		RESULT_CACHE.put("1", new ArrayList<Article>());
		RESULT_CACHE.put("2", new ArrayList<Article>());
		RESULT_CACHE.put("3", new ArrayList<Article>());
		RESULT_CACHE.put("4", new ArrayList<Article>());
		RESULT_CACHE.put("5", new ArrayList<Article>());
		RESULT_CACHE.put("6", new ArrayList<Article>());
		RESULT_CACHE.put("7", new ArrayList<Article>());
		RESULT_CACHE.put("8", new ArrayList<Article>());
		RESULT_CACHE.put("9", new ArrayList<Article>());
		RESULT_CACHE.put("10", new ArrayList<Article>());
		
	}

	private void reloadPreferences() {
		mServiceEnabled = mPref.getBoolean(PREF_SERVICE_ENABLED, false);
		mApplicationList = mPref.getString(PREF_APPLICATION_LIST, "")
				.split(";");
		mAutoStart = mPref.getBoolean(PREF_AUTO_START, false);
		mPassword = mPref.getString(PREF_PASSWORD, "");						// Default password is empty
		mGestureSequence = mPref.getString(PREF_GESTURE, "DOWN DOWN DOWN");	// Default questure is Down*3
		mFirstUse = mPref.getBoolean(PREF_FIRST_USE, true);
		mWeeklyReport = mPref.getBoolean(PREF_WEEKLY_REPORT, true);
		
		if (mPref.getBoolean(PREF_RELOCK_POLICY, true)) {
			try {
				mRelockTimeout = Integer.parseInt(mPref.getString(
						PREF_RELOCK_TIMEOUT, "-1"));
			} catch (Exception e) {
				mRelockTimeout = -1;
			}
		} else {
			mRelockTimeout = -1;
		}
	}
	 
	public static boolean isNetworkAvailable(ConnectivityManager check) {
		boolean isConnected = false;
//		ConnectivityManager check = (ConnectivityManager) this.
//				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (check != null) {
			NetworkInfo[] info = check.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						isConnected = true;
					}
				}
			}
		}
		return isConnected;
	}
	
	
	// email ---start---
	public void sendMail(String to ,String subject, String messageBody, String filename) {
		Session session = createSessionObject();
		
		try {
			Message message = createMessage(to, subject,
					messageBody, session, filename);
			new SendMailTask().execute(message);
			Log.d(Setting.TAG, "send to " + to + "\nsubject:" +  subject + "\ntext: \n"
					+ messageBody);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private class SendMailTask extends AsyncTask<Message, Void, Void> {

		@Override
		public void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		public void onPostExecute(Void aVoid) {
			super.onPostExecute(aVoid);
			Log.d(Setting.TAG, "SendMailTask complete");
		}

		@Override
		public Void doInBackground(Message... messages) {
			try {
				Log.i(Setting.TAG, "SendMailTask doInBackground");
				Transport.send(messages[0]);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	private Message createMessage(String email, String subject,
			String messageBody, javax.mail.Session session, String filename)
			throws MessagingException, UnsupportedEncodingException {
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(Setting.SERVICE_MAIL_ACCOUNT, "Yummy Admin"));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(
				email, email));
		message.setSubject(subject);

		
		if (filename != null) {
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(messageBody);
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			messageBodyPart = new MimeBodyPart();
			DataSource source = (DataSource) new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler((javax.activation.DataSource) source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);
		}else{
			message.setText(messageBody);
		}

		return message;
	}

	private Session createSessionObject() {
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");

		return Session.getInstance(properties, new javax.mail.Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(Setting.SERVICE_MAIL_ACCOUNT, Setting.SERVICE_MAIL_PASSWORD);
			}
		});
	}
	
	// email ----end----

}