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

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.kavelcortex.bookpriceculculator.util.Adapter.BookPriceAdapter;

/*THIS CLASS IS UNDER CONSTRUCTION*/

public class BookPriceFragment extends ListFragment {
    public interface BookPriceUpdater {
        void setUpdatedBookPriceData(ArrayList<Map<String,Object>> updatedData);
    }

    private BookPriceUpdater mUpdater;
    ArrayList<Map<String,Object>> mData;
    public BookPriceFragment() {
    }
    public void setBookPriceUpdater(BookPriceUpdater updater){
        mUpdater = updater;
    }

    public void setData(ArrayList<Map<String,Object>> data){
        mData = data;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new BookPriceAdapter(getActivity(),android.R.layout.simple_list_item_2,convertData(mData)));
    }


    private List<BookPriceAdapter.BookPriceTag> convertData(ArrayList<Map<String,Object>> data){
        List<BookPriceAdapter.BookPriceTag> convertedData = new ArrayList<>();
        BookPriceAdapter.BookPriceTag priceTag = new BookPriceAdapter.BookPriceTag();
        for(int i=0;i<mData.size();i++) {
            priceTag.setBookName(data.get(i).get("BookTitle").toString());
            priceTag.setBookDetail(data.get(i).get("BookDetail").toString());
            //priceTag.setBookPrice(data.get(i).get("BookPrice").toString());
            convertedData.add(priceTag);
        }
        return convertedData;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mUpdater = null;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        //TODO:set method when item clicked.
    }


}
