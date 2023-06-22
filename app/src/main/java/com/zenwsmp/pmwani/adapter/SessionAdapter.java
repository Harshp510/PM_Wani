package com.zenwsmp.pmwani.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.zenwsmp.pmwani.R;
import com.zenwsmp.pmwani.model.PlanBean;
import com.zenwsmp.pmwani.model.SessionBean;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.ViewHolder> {

    private List<SessionBean> list;
    private Context mCtx;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private boolean isfreez;
    private OnItemClickListenerData clickListener;


    public SessionAdapter(List<SessionBean> list, Context mCtx) {
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
                .inflate(R.layout.session_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NotNull final ViewHolder holder, int position) {
        SessionBean siteBean = list.get(position);
        holder.txt_ipaddress.setText(siteBean.getIpaddress());
        holder.txt_mac.setText(mCtx.getString(R.string.Rs) +siteBean.getMac());
        holder.txt_connected_since.setText(siteBean.getConnected_since());
        holder.txt_DataUsage.setText(siteBean.getDatausage());
        holder.txt_connected_since.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_event_list_time, 0, 0, 0);
        //holder.txt_vlanid.setText(siteBean.getVlanID());






    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView txt_ipaddress,txt_mac,txt_connected_since,txt_DataUsage;


        public ViewHolder(View itemView) {
            super(itemView);

            txt_ipaddress = itemView.findViewById(R.id.txt_ipaddress);
            txt_mac = itemView.findViewById(R.id.txt_mac);
            txt_connected_since = itemView.findViewById(R.id.txt_connected_since);
            txt_DataUsage = itemView.findViewById(R.id.txt_DataUsage);
           // btn_buy = itemView.findViewById(R.id.btn_buy);
            //btn_buy.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (clickListener != null)
                clickListener.onClick(view, list.get(getAdapterPosition()), getAdapterPosition());
        }
    }

    public void filterList(List<SessionBean> filterdNames) {
        list = filterdNames;
        notifyDataSetChanged();
    }

    public interface OnItemClickListenerData {
        public void onClick(View view, SessionBean groupclass, int postion);
    }
}