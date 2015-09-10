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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 嘉维 on 2015/9/5.
 */
public class BookPriceAdapter extends ArrayAdapter<BookPriceAdapter.BookPriceTag> {
    int mResId;
    LayoutInflater mInflater;
    public BookPriceAdapter(Context context,int layoutResId,List<BookPriceTag> object){
        super(context, layoutResId, object);
        mResId = layoutResId;
        mInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BookPriceTag priceTag = getItem(position);
        TextView tvBookName;
        TextView tvBookPrice;
        View v;
        if(convertView==null)
            v=mInflater.inflate(mResId,parent,false);
        else
            v=convertView;
        tvBookName = (TextView)v.findViewById(android.R.id.text1);
        tvBookPrice = (TextView)v.findViewById(android.R.id.text2);
        tvBookName.setText(priceTag.getBookName());
        tvBookPrice.setText(priceTag.getBookDetail());
        return v;
    }

    public static class BookPriceTag{
        private String mBookName,mBookDetail,mBookPrice;
        public BookPriceTag(){
        }
        public void setBookName(String bookName){
            mBookName = bookName;
        }
        public void setBookDetail(String bookDetail){
            mBookDetail = bookDetail;
        }
        public void setBookPrice(String bookPrice){
            mBookPrice = bookPrice;
        }
        public String getBookName(){
            return mBookName;
        }
        public String getBookDetail(){
            return mBookDetail;
        }
        public String getBookPrice(){
            return mBookPrice;
        }
    }
}
