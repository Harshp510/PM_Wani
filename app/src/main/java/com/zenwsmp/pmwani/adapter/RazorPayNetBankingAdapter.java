package com.zenwsmp.pmwani.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.zenwsmp.pmwani.R;
import com.zenwsmp.pmwani.objects.RazorPayBankList;

import java.util.ArrayList;




public class RazorPayNetBankingAdapter extends RecyclerView.Adapter<RazorPayNetBankingAdapter.ViewHolder> implements Filterable {
    private static final String TAG = RazorPayNetBankingAdapter.class.getSimpleName() ;
    private int mSelectedItem = -1;
    private final ArrayList<RazorPayBankList> mItems;
    private ArrayList<RazorPayBankList> listFiltered;
    private final Context mContext;
    private SelectedPaymentMethod selectedPaymentMethod;
    private String selectedItem = "";

    @Override
    public Filter getFilter() {
        return new ValueFilter();
    }

    public interface SelectedPaymentMethod {
        void getSelectedPaymentMethod(RazorPayBankList s);
    }

    public void setPayment(SelectedPaymentMethod payment) {
        this.selectedPaymentMethod = payment;
    }

    public RazorPayNetBankingAdapter(Context context, ArrayList<RazorPayBankList> items) {
        mContext = context;
        this.mItems = items;
        this.listFiltered = items;
    }

    public void updateData(){
        selectedItem = "";
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        try {
            //viewHolder.mRadio.setChecked(i == mSelectedItem);
            viewHolder.mRadio.setChecked(listFiltered.get(i).bankName.equals(selectedItem));
            viewHolder.mText.setText(listFiltered.get(i).bankName);
            viewHolder.ivWalletIcon.setVisibility(View.GONE);
            viewHolder.ivPaymentIcon.setVisibility(View.VISIBLE);
            Log.e(TAG,"bank: "+ listFiltered.get(i).bankName+" ,"+listFiltered.get(i).bankIcon);
       /* Glide.with(mContext)
                .load(listFiltered.get(i).bankIcon)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(viewHolder.ivPaymentIcon);*/

            Picasso.get()
                    .load(listFiltered.get(i).bankIcon)
                    .into(viewHolder.ivPaymentIcon);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return listFiltered.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View view = inflater.inflate(R.layout.razorpay_place_item, viewGroup, false);
        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private RadioButton mRadio;
        private TextView mText;
        private ImageView ivPaymentIcon,ivWalletIcon;

        public ViewHolder(final View inflate) {
            super(inflate);
            try {
                mText = inflate.findViewById(R.id.text);
                mRadio = inflate.findViewById(R.id.radio);
                ivPaymentIcon = inflate.findViewById(R.id.ivPaymentIcon);
                ivWalletIcon = inflate.findViewById(R.id.ivWalletIcon);
                View.OnClickListener clickListener = v -> {
                    try {
                        mSelectedItem = getAdapterPosition();
                        selectedItem = listFiltered.get(mSelectedItem).bankName;
                        selectedPaymentMethod.getSelectedPaymentMethod(listFiltered.get(mSelectedItem));
                        notifyDataSetChanged();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                };
                itemView.setOnClickListener(clickListener);
                mRadio.setOnClickListener(clickListener);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    private class ValueFilter extends Filter{
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                ArrayList<RazorPayBankList> filterList = new ArrayList<>();
                for (int i = 0; i < mItems.size(); i++) {
                    if ((mItems.get(i).bankName.toLowerCase()).contains(constraint.toString().toLowerCase())) {
                        filterList.add(mItems.get(i));
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = mItems.size();
                results.values = mItems;
            }
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            listFiltered = (ArrayList<RazorPayBankList>) results.values;
            notifyDataSetChanged();
        }
    }


}