/**
 * Copyright Bryan Rickman 2011-2012
 */
package com.bryanrickman.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.ClipboardManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class PasswordFactory extends Activity {

	private static final String CONSONANT_STRING = "bcdfghjklmnpqrstvwxz";
	private static final char[] CONSONANT_CHARS = CONSONANT_STRING
			.toCharArray();

	private static final String VOWEL_STRING = "aeiouy";
	private static final char[] VOWEL_CHARS = VOWEL_STRING.toCharArray();

	private static final String NUMERIC_STRING = "1234567890";
	private static final char[] NUMERIC = NUMERIC_STRING.toCharArray();

	private static final String SPECIAL_CHAR_STRING = "!@#$%^&*()_+=-<>?";
	private static final char[] SPECIAL_CHAR = SPECIAL_CHAR_STRING
			.toCharArray();

	private static final int DIALOG_ABOUT = 0;

	private static Map<Character, Character[]> secondLetters = new HashMap<Character, Character[]>();

	private List<String> passwordList = new ArrayList<String>();
	private ListView lv = null;
	private int charsInPassword;
	private int numberOfPasswords;
	private int frequencyOfCaps;
	private int numbersBelow;
	private int specialCharAbove;
	private boolean useApostSign;
	private boolean useAtSign;
	private boolean usePoundSign;
	private boolean useDollarSign;
	private boolean usePercentSign;
	private boolean useCarrotSign;
	private boolean useAndSign;
	private boolean useStarSign;
	private boolean useOpenParenSign;
	private boolean useCloseParenSign;
	private boolean useUnderScoreSign;
	private boolean usePlusSign;
	private boolean useEqualSign;
	private boolean useMinusSign;
	private boolean useLessThanSign;
	private boolean useGreaterThanSign;
	private boolean useQuestionSign;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		lv = (ListView) findViewById(R.id.listView1);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				System.out.println(position);
				ClipboardManager clip = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
				clip.setText(lv.getItemAtPosition(position).toString());
			}
		});
		generateSecondLetters();
		getPrefs();
		initButtons();
		initPasswordList();
		initListView();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {
		super.onStart();
		getPrefs();
	}

	/**
	 * 
	 */
	private void initPasswordList() {
		for (int i = 0; i < numberOfPasswords; i++) {
			passwordList.add(getString(R.string.passwordTxt));
		}
	}

	/**
	 * 
	 */
	private void initListView() {
		lv.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, passwordList));
	}

	/**
	 * 
	 */
	private void generateSecondLetters() {
		Character[] a = { 'e', 'i', 'o', 'u', 'y' };
		secondLetters.put('a', a);
		Character[] b = { 'l', 'r' };
		secondLetters.put('b', b);
		Character[] c = { 'h', 'k', 'l', 'r', 's' };
		secondLetters.put('c', c);
		Character[] d = { 'l', 's', 't', 'w' };
		secondLetters.put('d', d);
		Character[] e = { 'a', 'i', 'o', 'u', 'y' };
		secondLetters.put('e', e);
		Character[] f = { 'l', 'r', 's', 't' };
		secondLetters.put('f', f);
		Character[] g = { 'h', 'l', 'n', 'r' };
		secondLetters.put('g', g);
		// yes, I left h out...what follows h?
		Character[] i = { 'a', 'e', 'o', 'u' };
		secondLetters.put('i', i);
		// j is weird too
		Character[] k = { 'l', 'n', 'r', 'w' };
		secondLetters.put('k', k);
		Character[] l = { 'm', 't' };
		secondLetters.put('l', l);
		Character[] m = { 'b', 'n', 's', 't' };
		secondLetters.put('m', m);
		// n is weird
		Character[] o = { 'a', 'e', 'i', 'u', 'y' };
		secondLetters.put('o', o);
		Character[] p = { 'f', 'h', 'l', 'n', 'r', 's' };
		secondLetters.put('p', p);
		Character[] q = { 'w' };
		secondLetters.put('q', q);
		Character[] r = { 'b', 'd', 'f', 'g', 'k', 'l', 'm', 'n', 't' };
		secondLetters.put('r', r);
		Character[] s = { 'h', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 't', 'v', 'w' };
		secondLetters.put('s', s);
		Character[] t = { 'h', 'r', 's', 'w' };
		secondLetters.put('t', t);
		Character[] u = { 'a', 'e', 'i', 'o', 'y' };
		secondLetters.put('u', u);
		Character[] v = { 'r', 's' };
		secondLetters.put('v', v);
		Character[] w = { 'r', 's', 't' };
		secondLetters.put('w', w);
		// x is weird
		Character[] y = { 'a', 'e', 'o', 'u' };
		secondLetters.put('y', y);
		// z is weird
	}

	/**
	 * Initialize buttons
	 */
	private void initButtons() {
		Button button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				generatePassword();
			}

		});
	}

	/**
	 * 
	 */
	private void generatePassword() {
		getPrefs();
		List<Character> consonants = new ArrayList<Character>();
		List<Character> vowels = new ArrayList<Character>();
		List<Character> numbers = new ArrayList<Character>();
		List<Character> specialChars = new ArrayList<Character>();

		for (char c : CONSONANT_CHARS) {
			consonants.add(c);
		}

		for (char c : VOWEL_CHARS) {
			vowels.add(c);
		}

		for (char c : NUMERIC) {
			numbers.add(c);
		}

		for (char c : SPECIAL_CHAR) {
			processSpecialCharacters(specialChars, c);
		}

		Random r = new Random();
		int passwordSize = Integer.valueOf(charsInPassword);
		passwordList.clear();
		for (int i = 0; i < numberOfPasswords; i++) {
			StringBuilder sb = new StringBuilder();
			boolean lastWasVowel = true;
			while (sb.length() < passwordSize) {
				int number = r.nextInt(100) + 1;
				if (numbersBelow > number) {
					setNextLetters(numbers, sb, r);
				} else if (specialCharAbove < number) {
					setNextLetters(specialChars, sb, r);
				} else {
					if (lastWasVowel) {
						setNextLetters(consonants, sb, r);
						lastWasVowel = false;
					} else {
						setNextLetters(vowels, sb, r);
						lastWasVowel = true;
					}
				}
			}
			passwordList.add(sb.toString());
		}
		initListView();
	}

	/**
	 * @param specialChars
	 * @param c
	 */
	private void processSpecialCharacters(List<Character> specialChars, char c) {
		switch (c) {
		case '!':
			if (useApostSign) {
				specialChars.add(c);
			}
			break;
		case '@':
			if (useAtSign) {
				specialChars.add(c);
			}
			break;
		case '#':
			if (usePoundSign) {
				specialChars.add(c);
			}
			break;
		case '$':
			if (useDollarSign) {
				specialChars.add(c);
			}
			break;
		case '%':
			if (usePercentSign) {
				specialChars.add(c);
			}
			break;
		case '^':
			if (useCarrotSign) {
				specialChars.add(c);
			}
			break;
		case '&':
			if (useAndSign) {
				specialChars.add(c);
			}
			break;
		case '*':
			if (useStarSign) {
				specialChars.add(c);
			}
			break;
		case '(':
			if (useOpenParenSign) {
				specialChars.add(c);
			}
			break;
		case ')':
			if (useCloseParenSign) {
				specialChars.add(c);
			}
			break;
		case '_':
			if (useUnderScoreSign) {
				specialChars.add(c);
			}
			break;
		case '+':
			if (usePlusSign) {
				specialChars.add(c);
			}
			break;
		case '=':
			if (useEqualSign) {
				specialChars.add(c);
			}
			break;
		case '-':
			if (useMinusSign) {
				specialChars.add(c);
			}
			break;
		case '<':
			if (useLessThanSign) {
				specialChars.add(c);
			}
			break;
		case '>':
			if (useGreaterThanSign) {
				specialChars.add(c);
			}
			break;
		case '?':
			if (useQuestionSign) {
				specialChars.add(c);
			}
			break;
		default:
			specialChars.add(c);
		}
	}

	/**
	 * @param letters
	 * @param sb
	 * @param r
	 */
	private void setNextLetters(List<Character> letters, StringBuilder sb,
			Random r) {
		if (null != letters && 0 < letters.size()) {
			Character c = letters.get(r.nextInt(letters.size()));
			if (isAlphabetic(c)) {
				if (frequencyOfCaps > r.nextInt(100)) {
					c = Character.toUpperCase(c);
				}
			}
			sb.append(c);
			if (6 > sb.length() && 0 == r.nextInt(2)) {
				Character[] cs = secondLetters.get(c);
				if (null != cs) {
					Character next = cs[r.nextInt(cs.length)];
					if (isAlphabetic(next)) {
						if (frequencyOfCaps > r.nextInt(100)) {
							next = Character.toUpperCase(next);
						}
					}
					sb.append(next);
				}
			}
		}
	}

	/**
	 * @param c
	 * @return
	 */
	private boolean isAlphabetic(Character c) {
		boolean result = false;
		if (-1 != CONSONANT_STRING.indexOf(c)) {
			result = true;
		} else if (-1 != VOWEL_STRING.indexOf(c)) {
			result = true;
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options, menu);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.quit:
			this.finish();
			return true;
		case R.id.prefs:
			Intent prefsActivity = new Intent(getBaseContext(),
					PasswordPrefsActivity.class);
			startActivity(prefsActivity);
			return true;
		case R.id.about:
			showDialog(DIALOG_ABOUT);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreateDialog(int)
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		switch (id) {
		case DIALOG_ABOUT:
			dialog = new AlertDialog.Builder(this).setTitle(R.string.about)
					.setMessage(R.string.aboutText).create();
			break;
		default:
			dialog = null;
		}
		return dialog;
	}

	/**
	 * 
	 */
	private void getPrefs() {
		// Get the xml/preferences.xml preferences
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		charsInPassword = prefs.getInt("charsPrefInt", 8);
		numberOfPasswords = prefs.getInt("passwordCountInt", 10);
		frequencyOfCaps = prefs.getInt("frequencyCapsInt", 10);
		numbersBelow = prefs.getInt("frequencyNumbersInt", 10);
		specialCharAbove = 101 - prefs.getInt("frequencySpecialCharsInt", 10);
		useApostSign = prefs.getBoolean("useApostBool", true);
		useAtSign = prefs.getBoolean("useAtBool", true);
		usePoundSign = prefs.getBoolean("usePoundBool", true);
		useDollarSign = prefs.getBoolean("useDollarBool", true);
		usePercentSign = prefs.getBoolean("usePercentBool", true);
		useCarrotSign = prefs.getBoolean("useCarrotBool", true);
		useAndSign = prefs.getBoolean("useAndBool", true);
		useStarSign = prefs.getBoolean("useStarBool", true);
		useOpenParenSign = prefs.getBoolean("useOpenParenBool", true);
		useCloseParenSign = prefs.getBoolean("useCloseParenBool", true);
		useUnderScoreSign = prefs.getBoolean("useUnderScoreBool", true);
		usePlusSign = prefs.getBoolean("usePlusBool", true);
		useEqualSign = prefs.getBoolean("useEqualBool", true);
		useMinusSign = prefs.getBoolean("useMinusBool", true);
		useLessThanSign = prefs.getBoolean("useLessThanBool", true);
		useGreaterThanSign = prefs.getBoolean("useGreaterThanBool", true);
		useQuestionSign = prefs.getBoolean("useQuestionBool", true);
	}

}