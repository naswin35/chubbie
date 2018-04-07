package com.cgtin.admin.sherazipetshopkimo.Classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.cgtin.admin.sherazipetshopkimo.StartActivities.LoginActivity;

import java.util.HashMap;



public class SessionManager {
	// Shared Preferences
	public static SharedPreferences pref;
	
	// Editor for Shared preferences
	public static Editor editor,editor2;
	
	// Context
	private Context _context;

	// Shared pref mode
	private int PRIVATE_MODE = 0;

	// Sharedpref file name
	private static final String PREF_NAME = "SheraZi";
	
	// All Shared Preferences Keys
	private static final String IS_LOGIN = "IsLoggedIn";

	private static final String IS_SELECTED_LIST = "IsSelectedIn";
	
	// User name (make variable public to access from outside)


	public static final String KEY_CUSTOMER_ID = "customerid";
	public static final String KEY_CUSTOMER_TOKEN = "token";
	public static final String KEY_CUSTOMER_NAME = "customer_username";
	public static final String KEY_CUSTOMER_MOBILE = "customer_mobile";
	public static final String KEY_USER_EMAIL = "user_email";
	public static final String KEY_GENDER = "user_gender";
	public static final String KEY_SORT_DATA = "sort_data";

	public static final String KEY_CART_DATA = "cart_data";


	// Constructor

	@SuppressLint("CommitPrefEdits")
	public SessionManager(Context context){
		this._context = context;
		pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = pref.edit();
		editor2=pref.edit();

	}
	
	/**
	 * Create login session
	 * */
	public void createLoginSession(String customer_id,String customer_name, String token, String customer_mobile,String email,String gender){
		// Storing login value as TRUE
		editor.putBoolean(IS_LOGIN, true);
		// Storing name in pref
		editor.putString(KEY_CUSTOMER_ID,customer_id);
		editor.putString(KEY_CUSTOMER_TOKEN,token);
		editor.putString(KEY_CUSTOMER_NAME,customer_name);
		editor.putString(KEY_CUSTOMER_MOBILE,customer_mobile);
		editor.putString(KEY_USER_EMAIL,email);
		editor.putString(KEY_GENDER,gender);


		// commit changes
		editor.commit();
	}

	public void CreatePopupSession(String sort_data){
		// Storing login value as TRUE

		// Storing name in pref
		editor.putString(KEY_SORT_DATA,sort_data);

		// commit changes
		editor.commit();
	}
	public void CartSession(String cart){
		// Storing login value as TRUE

		// Storing name in pref
		editor.putString(KEY_CART_DATA,cart);

		// commit changes
		editor.commit();
	}

	/**
	 * Check login method wil check user login status
	 * If false it will redirect user to login page
	 * Else won't do anything
	 * */
	public void checkLogin(){
		// Check login status
		if(!this.isLoggedIn()){
			// user is not logged in redirect him to Login Activity
			Intent i = new Intent(_context, LoginActivity.class);
			// Closing all the Activities
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

			// Add new Flag to start new Activity
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

			// Staring Login Activity
			_context.startActivity(i);
		}
		
	}

	/**
	 * Get stored session data
	 * */
	public HashMap<String, String> getUserDetails(){
		HashMap<String, String> user = new HashMap<>();
		// user name
		//user.put(KEY_CUSTOMER, pref.getString(KEY_CUSTOMER, null));

		user.put(KEY_CUSTOMER_ID, pref.getString(KEY_CUSTOMER_ID, null));
		user.put(KEY_CUSTOMER_TOKEN, pref.getString(KEY_CUSTOMER_TOKEN, null));

		user.put(KEY_CUSTOMER_MOBILE, pref.getString(KEY_CUSTOMER_MOBILE, null));
		user.put(KEY_CUSTOMER_NAME, pref.getString(KEY_CUSTOMER_NAME, null));
		user.put(KEY_USER_EMAIL, pref.getString(KEY_USER_EMAIL, null));
		user.put(KEY_GENDER, pref.getString(KEY_GENDER, null));



		//user.put(KEY_MOBILE, pref.getString(KEY_MOBILE, null));
		
		// return user
		return user;
	}

	public HashMap<String, String> getPopupDetails(){
		HashMap<String, String> user = new HashMap<>();

		user.put(KEY_SORT_DATA, pref.getString(KEY_SORT_DATA, null));


		return user;
	}
	/**
	 * Clear session details
	 * */

	public HashMap<String, String> getCartDetails(){
		HashMap<String, String> user = new HashMap<>();

		user.put(KEY_CART_DATA, pref.getString(KEY_CART_DATA, null));


		return user;
	}




	public void logoutUser(){
		// Clearing all data from Shared Preferences
		editor.clear();
		editor.commit();
		Arrays.ProductItemsArray.clear();
		Arrays.CartArray.clear();
		
		// After logout redirect user to Loing Activity
	}

	public boolean isLoggedIn(){
		return pref.getBoolean(IS_LOGIN, false);
	}


}
