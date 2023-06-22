package com.zenwsmp.pmwani.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zenwsmp.pmwani.R;
import com.zenwsmp.pmwani.objects.RazorPayCardList;

import java.util.List;



public class RazorPayCardSpinner1 extends RecyclerView.Adapter<RazorPayCardSpinner1.MyViewHolder> {

    private final List<RazorPayCardList> dataList;
    private String selectedItem;
    private final String type;

    private SelectedValue selectedValue;

    public interface SelectedValue {
        void getSelectedValue(int pos,String item,String type,String selectedMonthNo,String selectedYear);
    }

    public void setSelectedItem(SelectedValue payment) {
        this.selectedValue = payment;
    }


    public RazorPayCardSpinner1(List<RazorPayCardList> dataList, String type,String selectedValue) {
        this.dataList = dataList;
        this.type = type;
        this.selectedItem = selectedValue;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.razor_pay_custom_sp_dropdown, parent, false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try {
            holder.tvItemText.setText(dataList.get(position).no);

            if("month".equals(type)){
                holder.tvItemTextMonth.setVisibility(View.VISIBLE);
                holder.tvItemTextMonth.setText(String.format("(%s)", dataList.get(position).monthName));
            }else {
                holder.tvItemTextMonth.setVisibility(View.GONE);
            }

            holder.radioDate.setChecked(dataList.get(position).no.equals(selectedItem));
            holder.radioDate.setClickable(false);

            View.OnClickListener clickListener = v -> {
                selectedItem = dataList.get(position).no;
                String selectedMonthNo = dataList.get(position).monthNo;
                String selectedYear = dataList.get(position).currentYear;
                notifyDataSetChanged();
                if (selectedValue != null)
                    selectedValue.getSelectedValue(position,selectedItem,type,selectedMonthNo,selectedYear);
            };
            holder.cardRadioLl.setOnClickListener(clickListener);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemText, tvItemTextMonth;
        RadioButton radioDate;
        LinearLayout cardRadioLl;

        private MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemText = itemView.findViewById(R.id.tvItemText);
            tvItemTextMonth = itemView.findViewById(R.id.tvItemTextMonth);
            radioDate = itemView.findViewById(R.id.radioDate);
            cardRadioLl = itemView.findViewById(R.id.cardRadioLl);
        }
    }
}
