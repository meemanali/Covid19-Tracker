package com.lgmInternee.covid19tracker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

@SuppressLint({"InflateParams", "SetTextI18n"})
public class MyAdapter extends BaseExpandableListAdapter {

    private final Context context;
    private final ArrayList<DistrictData> districtData;

    public MyAdapter(Context context, ArrayList<DistrictData> districtData) {
        this.context = context;
        this.districtData = districtData;
    }

    @Override
    public int getGroupCount() {
        return districtData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1; // Each group will have only one child item containing confirmed, active, deceased, and recovered data
    }

    @Override
    public Object getGroup(int groupPosition) {
        return districtData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return districtData.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group, null);
        }

        TextView heading = convertView.findViewById(R.id.txtHeading);
        DistrictData data = (DistrictData) getGroup(groupPosition);
        heading.setText(data.getDistrict());



        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);
        }

        TextView txtConfirmed = convertView.findViewById(R.id.txtConfirmed);
        TextView txtActive = convertView.findViewById(R.id.txtActive);
        TextView txtDeceased = convertView.findViewById(R.id.txtDeceased);
        TextView txtRecovered = convertView.findViewById(R.id.txtRecovered);

        DistrictData data = (DistrictData) getChild(groupPosition, childPosition);

        txtConfirmed.setText(Integer.toString(data.getConfirmed()));
        txtActive.setText(Integer.toString(data.getActive()));
        txtDeceased.setText(Integer.toString(data.getDeceased()));
        txtRecovered.setText(Integer.toString(data.getRecovered()));

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

//    @Override
//    public void registerDataSetObserver(DataSetObserver observer) {
//        super.registerDataSetObserver(observer);
//        Toast.makeText(context, "complete from adapter", Toast.LENGTH_LONG).show();
//    }
}