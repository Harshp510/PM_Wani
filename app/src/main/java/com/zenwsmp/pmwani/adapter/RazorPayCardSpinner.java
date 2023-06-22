/*
 * Copyright (c) 2017. Suthar Rohit
 * Developed by Suthar Rohit for NicheTech Computer Solutions Pvt. Ltd. use only.
 * <a href="http://RohitSuthar.com/">Suthar Rohit</a>
 *
 * @author Suthar Rohit
 */

package com.zenwsmp.pmwani.adapter;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.zenwsmp.pmwani.R;
import com.zenwsmp.pmwani.objects.RazorPayCardList;

import java.util.List;



/**
 * Matrubharti(com.nichetech.matrubharti) <br />
 * Developed by <b><a href="http://www.RohitSuthar.com/">Suthar Rohit</a></b>  <br />
 * on 29-Jun-2017.
 *
 * @author Suthar Rohit
 */
public class RazorPayCardSpinner extends BaseAdapter {

    private final LayoutInflater mInflater;

    private final List<RazorPayCardList> dataList;
    private String selectedItem;
    private final String type ;

    private SelectedValue selectedValue;

    public interface SelectedValue {
        void getSelectedValue(int pos,String item,String type,String selectedMonthNo,String selectedYear);
    }

    public void setSelectedItem(SelectedValue payment) {
        this.selectedValue = payment;
    }


    public RazorPayCardSpinner(Context context, List<RazorPayCardList> dataList, String type) {
        mInflater = LayoutInflater.from(context);
        this.dataList = dataList;
        this.type = type;
    }


    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ViewHolder {
        TextView tvItemText,tvItemTextMonth;
        RadioButton radioDate;
        LinearLayout cardRadioLl;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder h;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.razor_pay_custom_sp, parent, false);
            h = new ViewHolder();
            h.tvItemText = convertView.findViewById(R.id.tvItemText);
            convertView.setTag(h);
        } else {
            h = (ViewHolder) convertView.getTag();
        }

        h.tvItemText.setText(dataList.get(position).no);
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        ViewHolder h;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.razor_pay_custom_sp_dropdown, parent, false);
            h = new ViewHolder();
            h.tvItemText = convertView.findViewById(R.id.tvItemText);
            h.tvItemTextMonth = convertView.findViewById(R.id.tvItemTextMonth);
            h.radioDate = convertView.findViewById(R.id.radioDate);
            h.cardRadioLl = convertView.findViewById(R.id.cardRadioLl);
            convertView.setTag(h);

        } else {
            h = (ViewHolder) convertView.getTag();
        }

        h.tvItemText.setText(dataList.get(position).no);

        if("month".equals(type)){
            h.tvItemTextMonth.setVisibility(View.VISIBLE);
            h.tvItemTextMonth.setText(String.format("(%s)", dataList.get(position).monthName));
        }else {
            h.tvItemTextMonth.setVisibility(View.GONE);
        }

        h.radioDate.setChecked(dataList.get(position).no.equals(selectedItem));
        h.radioDate.setClickable(false);

        View.OnClickListener clickListener = v -> {
            selectedItem = dataList.get(position).no;
            String selectedMonthNo = dataList.get(position).monthNo;
            String selectedYear = dataList.get(position).currentYear;
            // close the dropdown
            View root = parent.getRootView();
            root.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
            root.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));
            notifyDataSetChanged();
            if (selectedValue != null)
                selectedValue.getSelectedValue(position,selectedItem,type,selectedMonthNo,selectedYear);
        };
        h.cardRadioLl.setOnClickListener(clickListener);

        return convertView;
    }

}
