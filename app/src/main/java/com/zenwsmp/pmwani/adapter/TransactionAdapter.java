package com.zenwsmp.pmwani.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.zenwsmp.pmwani.R;
import com.zenwsmp.pmwani.model.PlanBean;
import com.zenwsmp.pmwani.model.TransactionBean;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    private List<TransactionBean> list;
    private Context mCtx;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private boolean isfreez;
    private OnItemClickListenerData clickListener;


    public TransactionAdapter(List<TransactionBean> list, Context mCtx) {
        this.list = list;
        this.mCtx = mCtx;
        this.isfreez = isfreez;
    }

    public void setClickListener(OnItemClickListenerData itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transcation_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NotNull final ViewHolder holder, int position) {
        TransactionBean siteBean = list.get(position);
        holder.txt_planname.setText(siteBean.getPlan_name());
        holder.txt_planamount.setText(mCtx.getString(R.string.Rs) +siteBean.getAmount());
        holder.txt_orderid.setText(siteBean.getOrder_id());
        holder.txt_paymentdate.setText(parseDateToddMMyyyy(siteBean.getPayment_date()));


        if (siteBean.getPayment_status().equals("1")) {
            holder.txt_status.setText("Success");
            holder.txt_status.setTextColor(mCtx.getResources().getColor(R.color.wifigreen));

        } else if (siteBean.getPayment_status().equals("0")) {
            holder.txt_status.setText("Pending");
            holder.txt_status.setTextColor(mCtx.getResources().getColor(R.color.wifiyellow));
        } else {
            holder.txt_status.setText("Failure");
            holder.txt_status.setTextColor(mCtx.getResources().getColor(R.color.wifired));
        }



    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView txt_planname,txt_planamount,txt_orderid,txt_status,txt_paymentdate;


        public ViewHolder(View itemView) {
            super(itemView);

            txt_planname = itemView.findViewById(R.id.txt_planname);
            txt_planamount = itemView.findViewById(R.id.txt_planamount);
            txt_orderid = itemView.findViewById(R.id.txt_orderid);
            txt_status = itemView.findViewById(R.id.txt_status);
            txt_paymentdate = itemView.findViewById(R.id.txt_paymentdate);
          //  btn_buy.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (clickListener != null)
                clickListener.onClick(view, list.get(getAdapterPosition()), getAdapterPosition());
        }
    }

    public void filterList(List<TransactionBean> filterdNames) {
        list = filterdNames;
        notifyDataSetChanged();
    }

    public interface OnItemClickListenerData {
        public void onClick(View view, TransactionBean groupclass, int postion);
    }

    public String parseDateToddMMyyyy(String time) {
        String str = null;
        if (!time.isEmpty() && time != null) {
            String outputPattern = "dd-MM-yyyy hh:mm a";
            String inputPattern = "yyyy-MM-dd hh:mm:ss";
            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
            SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

            Date date = null;


            try {
                date = inputFormat.parse(time);
                str = outputFormat.format(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            str="";
        }


        return str;
    }
}