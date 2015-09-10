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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by 嘉维 on 2015/9/6.
 */
public class ThemeSetter {
    ArrayList<Map<String, Object>> themeList = new ArrayList<>();
    Activity mActivity;
    public ThemeSetter(Activity activity) {
        themeList.add(getThemeSet(0, R.style.AppTheme_Red, R.style.AppTheme_Red_DrawerLayoutActivity));
        themeList.add(getThemeSet(1, R.style.AppTheme_Orange, R.style.AppTheme_Orange_DrawerLayoutActivity));
        themeList.add(getThemeSet(2, R.style.AppTheme_Yellow, R.style.AppTheme_Yellow_DrawerLayoutActivity));
        themeList.add(getThemeSet(3, R.style.AppTheme_Green, R.style.AppTheme_Green_DrawerLayoutActivity));
        themeList.add(getThemeSet(4, R.style.AppTheme_Cyan, R.style.AppTheme_Cyan_DrawerLayoutActivity));
        themeList.add(getThemeSet(5, R.style.AppTheme_Blue, R.style.AppTheme_Blue_DrawerLayoutActivity));
        themeList.add(getThemeSet(6, R.style.AppTheme_Purple, R.style.AppTheme_Purple_DrawerLayoutActivity));
        mActivity = activity;
    }

    private Map<String, Object> getThemeSet(int index, int themeResId, int themeDrawerResId) {
        Map<String, Object> themeSet = new HashMap<>();
        themeSet.put("Index", index);
        themeSet.put("ThemeResId", themeResId);
        themeSet.put("ThemeDrawerResId", themeDrawerResId);
        return themeSet;
    }

    public ThemeSetter shuffleTheme() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mActivity);
        int indexCurrent = preferences.getInt("ColorIndex", 0);
        int index;
        do{
            index = (int) (Math.random()*10) % 7;
        }while (index==indexCurrent);
        Log.d("shuffleTheme",""+index);
        preferences.edit().putInt("ColorIndex", index).commit();
        return this;
    }

    public ThemeSetter setCurrentTheme(boolean isDrawerLayout) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mActivity);
        int index = preferences.getInt("ColorIndex", 0);
        Log.d("setCurrentTheme",""+index);
        Map<String, Object> themeMap = themeList.get(index);
        if (isDrawerLayout)
            mActivity.setTheme((int) themeMap.get("ThemeDrawerResId"));
        else
            mActivity.setTheme((int) themeMap.get("ThemeResId"));
        return this;
    }

    public void finish() {
        mActivity.finish();
        mActivity.startActivity(new Intent(mActivity, mActivity.getClass()));
        mActivity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
}
