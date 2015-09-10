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

package cn.kavelcortex.bookpriceculculator.util.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Map;

import cn.kavelcortex.bookpriceculculator.R;

/**
 * Created by 嘉维 on 2015/9/3.
 */
public class BookPickerAdapter extends RecyclerView.Adapter<BookPickerAdapter.BookPickButtonViewHolder>{

    ArrayList<Map<String,Object>> mData;
    ArrayList<View>mViewMap = new ArrayList<>();
    ItemCheckedChangedListener mListener;
    String ITEM_LOCATOR;
    public BookPickerAdapter(){}

    public void setItemData(ArrayList<Map<String, Object>> data){
        mData = data;
    }

    public void setTagForLocateItem(String TAG){
        ITEM_LOCATOR = TAG;
    }

    public void setOnItemCheckedChangedListener(ItemCheckedChangedListener listener){
        mListener = listener;
    }

    public void addData(int toPosition,Map<String,Object> mapData){
        mData.add(toPosition, mapData);
        notifyItemInserted(toPosition);
    }
    public void addData(Map<String,Object> mapData){
        mData.add(mapData);
        notifyItemInserted(mData.size());
    }
    public void delData(int fromPosition){
        mData.remove(fromPosition);
        notifyItemRemoved(fromPosition);
    }
    public void delData(){
        int lastPosition = getItemCount();
        mData.remove(lastPosition);
        notifyItemRemoved(lastPosition);
    }

    @Override
    public int getItemCount() {return mData.size();}

    public View getView(int position){
        return mViewMap.get(position);
    }

    @Override
    public BookPickButtonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_pick_button_layout, parent, false);
        return new BookPickButtonViewHolder(v,mListener);


    }

    @Override
    public void onBindViewHolder(BookPickButtonViewHolder holder, int position) {
        int bookPosition = (int)mData.get(position).get(ITEM_LOCATOR);
        String bookSerial =String.valueOf(bookPosition+1);
        holder.toggleButton.setText(bookSerial);
        holder.toggleButton.setTextOn(bookSerial);
        holder.toggleButton.setTextOff(bookSerial);
        holder.position = position;
        if(mViewMap.size()>position)
            mViewMap.remove(position);
        mViewMap.add(position,holder.toggleButton);
    }

    /**
     * Created by 嘉维 on 2015/9/4.
     */
    public interface ItemCheckedChangedListener  {
        void onCheckedChanged(CompoundButton compoundButton, boolean b, int position);
    }


    public static class BookPickButtonViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener{
        public ToggleButton toggleButton;
        public ItemCheckedChangedListener mListener;
        public int position;

        public BookPickButtonViewHolder(View v, ItemCheckedChangedListener listener){
            super(v);
            toggleButton = (ToggleButton)v.findViewById(R.id.book_pick_button);
            this.mListener = listener;
            toggleButton.setOnCheckedChangeListener(this);
        }
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            mListener.onCheckedChanged(compoundButton, b, position);
        }
    }
}
