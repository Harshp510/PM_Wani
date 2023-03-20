package com.zenwsmp.pmwani.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.zenwsmp.pmwani.R;
import com.zenwsmp.pmwani.model.LanguageBean;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Language_Adapter extends RecyclerView.Adapter<Language_Adapter.ViewHolder> {

    private List<LanguageBean> list;
    private Context mCtx;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private boolean isfreez;
    private OnItemClickListenerData clickListener;
    private OnTimeSelectedListener listener;
    private int currentSelection = 0;
    public Language_Adapter(List<LanguageBean> list, Context mCtx) {
        this.list = list;
        this.mCtx = mCtx;

    }

    public void setClickListener(OnItemClickListenerData itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.language_item_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NotNull final ViewHolder holder, int position) {
        LanguageBean siteBean = list.get(position);

        holder.tvEventTime1.setText(siteBean.getLanguagename());
        holder.itemView.setSelected(currentSelection == holder.getAdapterPosition());
        holder.tvEventTime1.setSelected(currentSelection == holder.getAdapterPosition());

        holder.itemView.setOnClickListener(view -> {
            //currentSelection = currentSelection == h.getAdapterPosition() ? -1 : h.getAdapterPosition()
            if (currentSelection != holder.getAdapterPosition()) {
                currentSelection = holder.getAdapterPosition();
                if (listener != null) {
                    listener.onTimeSelected(holder.getLayoutPosition(), siteBean);
                }
                notifyDataSetChanged();
            }
        });


    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvEventTime1,txt_email,txt_connectionurl;

        LinearLayout itemview1;
        public ViewHolder(View itemView) {
            super(itemView);

            tvEventTime1 = itemView.findViewById(R.id.tvEventTime1);

           itemview1 = itemView.findViewById(R.id.itemview);

          // itemview1.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (clickListener != null)
                clickListener.onClick(view, list.get(getAdapterPosition()), getAdapterPosition());
        }
    }

    public void filterList(List<LanguageBean> filterdNames) {
        list = filterdNames;
        notifyDataSetChanged();
    }

    public interface OnItemClickListenerData {
        public void onClick(View view, LanguageBean groupclass, int postion);
    }

    public void setOnTimeSelectedListener(OnTimeSelectedListener listener) {
        this.listener = listener;
    }

    public interface OnTimeSelectedListener {

        void onTimeSelected(int position, LanguageBean time);

    }
}