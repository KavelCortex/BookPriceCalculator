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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import cn.kavelcortex.bookpriceculculator.R;
import cn.kavelcortex.bookpriceculculator.util.SimpleCallback;
import cn.kavelcortex.bookpriceculculator.TotalPrice;

/**
 * Created by 嘉维 on 2015/9/4.
 */
public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.ReceiptItemViewHolder>implements SimpleCallback.ItemTouchHelperAdapter{

    public interface ItemOperateHelper{
        void Checked(int serial,boolean b);
        void receivePrice(BigDecimal price, boolean b);
    }

    ArrayList<Map<String,Object>> mData = new ArrayList<>();
    ArrayList<Object> index = new ArrayList<>();
    String BOOK_SERIAL;
    String BOOK_TITLE;
    String BOOK_PRICE;
    ItemOperateHelper mHelper;
    public ReceiptAdapter(){}

    public void setTagForSerial(String TAG){
        BOOK_SERIAL = TAG;
    }

    public void setTagForTitle(String TAG){
        BOOK_TITLE = TAG;
    }

    public void setTagForPrice(String TAG){
        BOOK_PRICE = TAG;
    }

    public void setItemOperateHelper(ItemOperateHelper helper){
        mHelper = helper;
    }

    public void addData(Map<String,Object> mapData){
        Log.d("addData","mapData:"+mapData.get(BOOK_PRICE).toString());
        mData.add(mapData);
        index.add(mapData.get(BOOK_SERIAL));
        BigDecimal price = new BigDecimal(0);
        price = price.add((BigDecimal) mapData.get(BOOK_PRICE));
        Log.d("addData","price:"+price.toString());
        mHelper.receivePrice(price,true);
        notifyItemInserted(mData.size());
    }
    public void delData(Map<String,Object> mapData){
        Object tag =mapData.get(TotalPrice.BOOK_SERIAL);
        int position = index.lastIndexOf(tag);
        mData.remove(position);
        index.remove(position);
        BigDecimal price = new BigDecimal(0);
        price = price.add((BigDecimal) mapData.get(TotalPrice.BOOK_PRICE));
        mHelper.receivePrice(price, false);
        notifyItemRemoved(position);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mData,fromPosition,toPosition);
        Collections.swap(index,fromPosition,toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        mHelper.Checked(getSerial(position),false);
    }

    private int getSerial(int position){
        return (int) index.get(position);
    }

    @Override
    public ReceiptItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.receipt_card_layout, parent, false);
        return new ReceiptItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ReceiptItemViewHolder holder, int position) {
        String title = mData.get(position).get(BOOK_TITLE).toString();
        String detail = "本书价格："+mData.get(position).get(BOOK_PRICE).toString()+"元";
        holder.mTitle.setText(title);
        holder.mDetail.setText(detail);
    }

    @Override
    public int getItemCount(){
        return mData.size();
    }

    public static class ReceiptItemViewHolder extends RecyclerView.ViewHolder{
        TextView mTitle;
        TextView mDetail;
        int SERIAL;
        public ReceiptItemViewHolder(View v){
            super(v);
            mTitle = (TextView)v.findViewById(R.id.receipt_title);
            mDetail = (TextView)v.findViewById(R.id.receipt_detail);
        }
    }

}