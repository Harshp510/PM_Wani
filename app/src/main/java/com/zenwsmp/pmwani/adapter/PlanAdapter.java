package com.zenwsmp.pmwani.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.zenwsmp.pmwani.R;
import com.zenwsmp.pmwani.model.PlanBean;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ViewHolder> {

    private List<PlanBean> list;
    private Context mCtx;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private boolean isfreez;
    private OnItemClickListenerData clickListener;


    public PlanAdapter(List<PlanBean> list, Context mCtx) {
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
                .inflate(R.layout.plan_item_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NotNull final ViewHolder holder, int position) {
        PlanBean siteBean = list.get(position);
        holder.txt_planname.setText(siteBean.getPlan_name());
        holder.txt_planamount.setText(mCtx.getString(R.string.Rs) +siteBean.getPrice());
        holder.txt_validity.setText(siteBean.getAllow_time()+" "+siteBean.getAllow_time_unit());
        //holder.txt_vlanid.setText(siteBean.getVlanID());






    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView txt_planname,txt_planamount,txt_validity,txt_detail,btn_buy;


        public ViewHolder(View itemView) {
            super(itemView);

            txt_planname = itemView.findViewById(R.id.txt_planname);
            txt_planamount = itemView.findViewById(R.id.txt_planamount);
            txt_validity = itemView.findViewById(R.id.txt_validity);
            txt_detail = itemView.findViewById(R.id.txt_detail);
            btn_buy = itemView.findViewById(R.id.btn_buy);
            btn_buy.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (clickListener != null)
                clickListener.onClick(view, list.get(getAdapterPosition()), getAdapterPosition());
        }
    }

    public void filterList(List<PlanBean> filterdNames) {
        list = filterdNames;
        notifyDataSetChanged();
    }

    public interface OnItemClickListenerData {
        public void onClick(View view, PlanBean groupclass, int postion);
    }
}