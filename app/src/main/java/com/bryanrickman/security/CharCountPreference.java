/*
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
*/

package com.bryanrickman.security;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.bryanrickman.security.R;

public class CharCountPreference extends DialogPreference implements OnSeekBarChangeListener {

	private static final String PREFERENCE_NS = "http://schemas.android.com/apk/res/com.bryanrickman.seekbarpreference";
	private static final String ANDROID_NS = "http://schemas.android.com/apk/res/android";

	// Attribute names
	private static final String ATTR_DEFAULT_VALUE = "defaultValue";
	private static final String ATTR_MIN_VALUE = "minValue";
	private static final String ATTR_MAX_VALUE = "maxValue";

	// Default values for default constructor
	private static final int DEFAULT_CURRENT_VALUE = 10;
	private static final int DEFAULT_MIN_VALUE = 8;
	private static final int DEFAULT_MAX_VALUE = 18;

	// Real defaults
	private final int defaultValue;
	private final int maxValue;
	private final int minValue;

	// Current value
	private int currentValue;

	// View elements
	private SeekBar seekBar;
	private TextView valueText;
	
	public CharCountPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		minValue = attrs.getAttributeIntValue(PREFERENCE_NS, ATTR_MIN_VALUE,
				DEFAULT_MIN_VALUE);
		maxValue = attrs.getAttributeIntValue(PREFERENCE_NS, ATTR_MAX_VALUE,
				DEFAULT_MAX_VALUE);
		defaultValue = attrs.getAttributeIntValue(ANDROID_NS,
				ATTR_DEFAULT_VALUE, DEFAULT_CURRENT_VALUE);
	}

	@Override
	protected View onCreateDialogView() {

		// Get current value from settings
		currentValue = getPersistedInt(defaultValue);

		// Inflate layout
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.dialog_slider, null);

		// Put minimum and maximum
		((TextView) view.findViewById(R.id.min_value)).setText(Integer
				.toString(minValue));
		((TextView) view.findViewById(R.id.max_value)).setText(Integer
				.toString(maxValue));

		// Setup SeekBar
		seekBar = (SeekBar) view.findViewById(R.id.seek_bar);
		seekBar.setMax(maxValue - minValue);
		seekBar.setProgress(currentValue - minValue);
		seekBar.setOnSeekBarChangeListener(this);

		// Put current value
		valueText = (TextView) view.findViewById(R.id.current_value);
		valueText.setText(Integer.toString(currentValue));

		return view;
	}

	@Override
	public void onProgressChanged(SeekBar seek, int value, boolean fromTouch) {
		// Update current value
		currentValue = value + minValue;
		// Update label with current value
		valueText.setText(Integer.toString(currentValue));
	}

	/* (non-Javadoc)
	 * @see android.preference.DialogPreference#onDialogClosed(boolean)
	 */
	@Override
	protected void onDialogClosed(boolean positiveResult) {
	    if (shouldPersist()) {
	        persistInt(currentValue);
	    }

		super.onDialogClosed(positiveResult);
	    notifyChanged();
	}

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}
}