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

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ToggleButton;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.kavelcortex.bookpriceculculator.util.Adapter.BookPickerAdapter;
import cn.kavelcortex.bookpriceculculator.util.Adapter.ReceiptAdapter;
import cn.kavelcortex.bookpriceculculator.util.SimpleCallback;

public class TotalPrice extends AppCompatActivity implements ReceiptAdapter.ItemOperateHelper {

    int BOOK_COUNT = 20;
    public static final String BOOK_SERIAL = "BookSerial";
    public static final String BOOK_TITLE = "BookTitle";
    public static final String BOOK_PRICE = "BookPrice";

    BigDecimal totalPrice = new BigDecimal(0);

    Toolbar mToolbar;
    ImageView idleImage;
    ToggleButton ButtonAOR;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    ArrayList<Map<String, Object>> mDataList = new ArrayList<>();
    RecyclerView BookPickerRecyclerView, ReceiptRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeSetter themeSetter = new ThemeSetter(this);
        themeSetter.setCurrentTheme(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_price);
        initArrayListData();
        initToolBar();
        initDrawer();
        initReceipt();
    }

    private void initArrayListData() {
        setList(mDataList, BOOK_COUNT);
    }

    private void setList(ArrayList<Map<String, Object>> toList, int itemCount) {
        if (!toList.isEmpty())
            toList.clear();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        for (int i = 0; i < itemCount; i++) {
            Map<String, Object> map = new HashMap<>();
            String stringValue = preferences.getString(BOOK_SERIAL + i, "0.0");
            BigDecimal price = new BigDecimal(stringValue);
            map.put(BOOK_SERIAL, i);
            map.put(BOOK_TITLE, "课本" + (i + 1));
            map.put(BOOK_PRICE, price);
            toList.add(i, map);
        }
    }

    private void initToolBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setSubtitle(R.string.main_activity_subtitle);
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setATheme();
            }
        });
        setSupportActionBar(mToolbar);
    }

    private void setATheme(){
        ThemeSetter themeSetter =new ThemeSetter(this);
        themeSetter.shuffleTheme().finish();
    }

    private void initDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_opened, R.string.drawer_closed);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        initRecyclerViewOnDrawer();
    }

    private void initReceipt() {
        initStatusBar();
        initRecyclerViewOnReceipt();
    }

    private void initStatusBar() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
        int color = typedValue.data;
        mDrawerLayout.setStatusBarBackgroundColor(color);
        initButtonAOR();
    }

    private void initButtonAOR() {
        ButtonAOR = (ToggleButton) findViewById(R.id.buttonAOR);
        ButtonAOR.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                setCheckedAll(b);
            }
        });
    }

    private void setCheckedAll(boolean b) {
        BookPickerAdapter adapter = (BookPickerAdapter) BookPickerRecyclerView.getAdapter();
        for (int i = 0; i < mDataList.size(); i++)
            pressButton((ToggleButton) adapter.getView(i), b);
    }

    private void pressButton(ToggleButton button, boolean b) {
        button.setChecked(b);
    }

    private void initRecyclerViewOnDrawer() {
        idleImage = (ImageView) findViewById(R.id.idle_image);
        BookPickerRecyclerView = (RecyclerView) findViewById(R.id.recycler_book_picker);
        BookPickerRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager;
        if (isScreenOrientationPortrait(this))
            layoutManager = new GridLayoutManager(this, 4);
        else
            layoutManager = new GridLayoutManager(this, 7);
        BookPickerRecyclerView.setLayoutManager(layoutManager);
        final BookPickerAdapter adapter = new BookPickerAdapter();
        adapter.setItemData(mDataList);
        adapter.setTagForLocateItem(BOOK_SERIAL);
        adapter.setOnItemCheckedChangedListener(new BookPickerAdapter.ItemCheckedChangedListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b, int position) {
                buy(position, b);
            }
        });
        BookPickerRecyclerView.setAdapter(adapter);
    }

    private boolean isScreenOrientationPortrait(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    private void buy(int bookPosition, boolean isChecked) {
        ReceiptAdapter adapter = (ReceiptAdapter) ReceiptRecyclerView.getAdapter();
        if (isChecked) {
            adapter.addData(mDataList.get(bookPosition));
        } else {
            adapter.delData(mDataList.get(bookPosition));
        }
        if (adapter.getItemCount() != 0) {
            idleImage.setVisibility(View.GONE);
            mToolbar.setTitle(getString(R.string.title_activity_total_price) + "=" + totalPrice.toString());
            Log.d("buy", "totalPrice:" + totalPrice.toString());
        } else {
            idleImage.setVisibility(View.VISIBLE);
            mToolbar.setTitle(R.string.app_name);
        }
        supportInvalidateOptionsMenu();
    }


    private void initRecyclerViewOnReceipt() {
        ReceiptRecyclerView = (RecyclerView) findViewById(R.id.recycler_receipt);
        ReceiptRecyclerView.setHasFixedSize(true);
        ReceiptRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ReceiptRecyclerView.setItemAnimator(new DefaultItemAnimator());
        ReceiptAdapter adapter = new ReceiptAdapter();
        adapter.setTagForSerial(BOOK_SERIAL);
        adapter.setTagForTitle(BOOK_TITLE);
        adapter.setTagForPrice(BOOK_PRICE);
        adapter.setItemOperateHelper(this);
        ReceiptRecyclerView.setAdapter(adapter);
        ItemTouchHelper.Callback callback = new SimpleCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(ReceiptRecyclerView);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_total_price, menu);
        return ReceiptRecyclerView.getAdapter().getItemCount() != 0;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item))
            return true;
        else if (item != null && item.getItemId() == R.id.action_reset) {
            reset();
        }
        return super.onOptionsItemSelected(item);
    }

    private void reset() {
        ButtonAOR.setChecked(false);
        setCheckedAll(false);
    }

    public void setPrice(View v) {
        Intent intent = new Intent(this, BookPricePreference.class);
        intent.putExtra("BOOK_SERIAL", BOOK_SERIAL);
        intent.putExtra("BOOK_PRICE", BOOK_PRICE);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ReceiptRecyclerView.getAdapter().getItemCount() != 0)
            reset();
        setList(mDataList, BOOK_COUNT);
        ReceiptRecyclerView.getAdapter().notifyDataSetChanged();
    }

    //implement method
    @Override
    public void Checked(int serial, boolean b) {
        BookPickerAdapter adapter = (BookPickerAdapter) BookPickerRecyclerView.getAdapter();
        ToggleButton button = (ToggleButton) adapter.getView(serial);
        pressButton(button, b);
    }

    @Override
    public void receivePrice(BigDecimal price, boolean b) {
        Log.d("receivePrice", "price:" + (b ? "add" : "subtract") + price.toString());
        if (b)
            totalPrice = totalPrice.add(price);
        else
            totalPrice = totalPrice.subtract(price);
        Log.d("receivePrice", "totalPrice:" + totalPrice.toString());
    }
}