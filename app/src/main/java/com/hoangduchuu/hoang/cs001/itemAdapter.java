package com.hoangduchuu.hoang.cs001;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by hoang on 9/22/16.
 */

public class itemAdapter extends ArrayAdapter<ItemList> {

    public itemAdapter(Context context, int resource, List<ItemList> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view =  inflater.inflate(R.layout.item_layout_list, null);
        }
        ItemList p = getItem(position);
        if (p != null) {

            TextView txtItemName = (TextView) view.findViewById(R.id.textViewItemName);
            TextView txtID = (TextView) view.findViewById(R.id.textViewID);
            TextView txtDate = (TextView) view.findViewById(R.id.textViewDate) ;
            TextView txtPrio  = (TextView)view.findViewById(R.id.textViewPriority_DL);

            txtID.setText(String.valueOf("sqliteID: " +p.getId()));
            txtItemName.setText("Task: " + p.getItName());
            txtDate.setText(""+p.getDueDate());

            String priorityView = "abc";
            switch (p.getPriority()){
                case 0 : priorityView = "Hight"; break;
                case 1 : priorityView = "Low"; break;
                case 2 : priorityView = "Medium"; break;
            }

            txtPrio.setText(priorityView);
        }
        return view;
    }

}
