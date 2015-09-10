/*
 * Copyright (c) 2015 KavelCortex
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.kavelcortex.bookpriceculculator;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatDelegate;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;

/**
 * Created by 嘉维 on 2015/9/5.
 */
public class BookPricePreference extends PreferenceActivity {
    PreferenceScreen mPreferenceScreen;
    AppCompatDelegate mDelegate;
    String BOOK_SERIAL,BOOK_PRICE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeSetter themeSetter = new ThemeSetter(this);
        themeSetter.setCurrentTheme(false);
        getDelegate().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        initToolBar();
        getExtra();
        addPreferencesFromResource(R.xml.book_price);
        setupPreferenceScreen();
    }
    private void initToolBar(){
        LinearLayout root = (LinearLayout)findViewById(android.R.id.list).getParent().getParent().getParent();
        Toolbar toolbar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.toolbar,root,false);
        toolbar.setTitle(R.string.set_price_title);
        toolbar.setSubtitle(R.string.set_price_subtitle);
        root.addView(toolbar, 0);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);
    }
    private void getExtra(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        BOOK_SERIAL = bundle.getString("BOOK_SERIAL");
        BOOK_PRICE = bundle.getString("BOOK_PRICE");
    }
    private void setupPreferenceScreen(){
        mPreferenceScreen = getPreferenceScreen();
        mPreferenceScreen.setOrderingAsAdded(true);
        for(int i=0;i<20;i++){
            Preference preference = addNewEditTextPreference(i);
            mPreferenceScreen.addPreference(preference);
            bindPreferenceSummaryToValue(findPreference(BOOK_SERIAL+i));
        }
    }
    private Preference addNewEditTextPreference(int index){
        int serial = index+1;
        EditTextPreference editTextPreference = new EditTextPreference(this);
        editTextPreference.getEditText().setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        editTextPreference.getEditText().setSingleLine(true);
        editTextPreference.getEditText().setSelectAllOnFocus(true);
        editTextPreference.setKey(BOOK_SERIAL + index);
        editTextPreference.setTitle("设定课本" + serial + "的价格");
        editTextPreference.setDialogTitle("请输入课本" + serial + "的价格");
        editTextPreference.setDefaultValue("0.0");
        return editTextPreference;
    }
    private void bindPreferenceSummaryToValue(Preference preference){
        preference.setOnPreferenceChangeListener(listener);
        listener.onPreferenceChange(preference, PreferenceManager.getDefaultSharedPreferences(this).getString(preference.getKey(),"0"));
    }
    private static Preference.OnPreferenceChangeListener listener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object o) {
            String stringValue = o.toString();
            preference.setSummary(stringValue+"元");
            return true;
        }
    };
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();break;
        }
        return super.onOptionsItemSelected(item);
    }
    private AppCompatDelegate getDelegate() {
        if (mDelegate == null)
            mDelegate = AppCompatDelegate.create(this, null);
        return mDelegate;
    }
        @Override
        protected void onPostCreate(Bundle savedInstanceState) {
            super.onPostCreate(savedInstanceState);
            getDelegate().onPostCreate(savedInstanceState);
        }

        public ActionBar getSupportActionBar() {
            return getDelegate().getSupportActionBar();
        }

        public void setSupportActionBar(@Nullable Toolbar toolbar) {
            getDelegate().setSupportActionBar(toolbar);
        }

        @Override
        public MenuInflater getMenuInflater() {
            return getDelegate().getMenuInflater();
        }

        @Override
        public void setContentView(@LayoutRes int layoutResID) {
            getDelegate().setContentView(layoutResID);
        }

        @Override
        public void setContentView(View view) {
            getDelegate().setContentView(view);
        }

        @Override
        public void setContentView(View view, ViewGroup.LayoutParams params) {
            getDelegate().setContentView(view, params);
        }

        @Override
        public void addContentView(View view, ViewGroup.LayoutParams params) {
            getDelegate().addContentView(view, params);
        }

        @Override
        protected void onPostResume() {
            super.onPostResume();
            getDelegate().onPostResume();
        }

        @Override
        protected void onTitleChanged(CharSequence title, int color) {
            super.onTitleChanged(title, color);
            getDelegate().setTitle(title);
        }

        @Override
        public void onConfigurationChanged(Configuration newConfig) {
            super.onConfigurationChanged(newConfig);
            getDelegate().onConfigurationChanged(newConfig);
        }

        @Override
        protected void onStop() {
            super.onStop();
            getDelegate().onStop();
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            getDelegate().onDestroy();
        }
    }

