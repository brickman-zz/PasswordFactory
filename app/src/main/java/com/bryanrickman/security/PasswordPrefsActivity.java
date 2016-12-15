package com.bryanrickman.security;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class PasswordPrefsActivity extends PreferenceActivity {
	public static final String CHARACTERS_IN_PASSWORD = "CHARS_IN_PASSWORD";

	/* (non-Javadoc)
	 * @see android.preference.PreferenceActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    addPreferencesFromResource(R.xml.preferences);
	}

	/* (non-Javadoc)
	 * @see android.preference.PreferenceActivity#onContentChanged()
	 */
	@Override
	public void onContentChanged() {
		// TODO Auto-generated method stub
		super.onContentChanged();
	}
	 
	
}
