package com.example.codetour.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.codetour.R;
import com.example.codetour.Spot;

import java.util.List;

// ListView와 Fragment를 연결해주는 class
public class PlaceItemViewAdapter extends BaseAdapter {

    private List<Spot> spotList;
    private Context context;
    private LayoutInflater layoutInflater;

    public PlaceItemViewAdapter() {}

    public PlaceItemViewAdapter(List<Spot> placeList, Context context){
        this.context = context;
        this.spotList = placeList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    // List의 사이즈 리턴
    @Override
    public int getCount() {
        return spotList.size();
    }

    // i번째 아이템 리턴
    @Override
    public Object getItem(int i) {
        return spotList.get(i);
    }

    // 아이템의 id 반환
    @Override
    public long getItemId(int i) {
        return i;
    }

    // placeList의 바뀐 내용을 View에 표시
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemLayout = view;
        if(itemLayout == null){
            itemLayout = layoutInflater.inflate(R.layout.fragment_placeitem,viewGroup, false);
        }

        TextView textView = itemLayout.findViewById(R.id.placeName);

        textView.setText(spotList.get(i).getAddress());

        return itemLayout;
    }
}
