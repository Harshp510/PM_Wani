package com.zenwsmp.pmwani.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.zenwsmp.pmwani.R;
import com.zenwsmp.pmwani.common.Constant;
import com.zenwsmp.pmwani.objects.RazorPayPayment;

import java.util.ArrayList;




public class RazorPayRadioAdapter extends RecyclerView.Adapter<RazorPayRadioAdapter.ViewHolder> /*implements Filterable*/ {
    private static final String TAG = RazorPayRadioAdapter.class.getSimpleName() ;
    private int mSelectedItemIndex = 0;
    //private ArrayList<RazorPayPayment> mItems;
    private final ArrayList<RazorPayPayment> listFiltered;
    private final Context mContext;
    private SelectedPaymentMethod selectedPaymentMethod;
    private String selectedItem;
    private final String type;

    /*@Override
    public Filter getFilter() {
        return new ValueFilter();
    }*/

    public interface SelectedPaymentMethod {
        void getSelectedPaymentMethod(String s);
    }

    public void setPayment(SelectedPaymentMethod payment) {
        this.selectedPaymentMethod = payment;
    }

    public RazorPayRadioAdapter(Context context, ArrayList<RazorPayPayment> items, String type) {
        mContext = context;
       // this.mItems = items;
        this.listFiltered = items;
        this.type = type;
        if (!Constant.RazorPayPaymentType.WALLET.equals(type)){
            this.selectedItem = listFiltered.get(0).getTitle();
        }else {
            this.selectedItem = "";
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        try {
            //viewHolder.mRadio.setChecked(i == mSelectedItemIndex);
            viewHolder.mRadio.setChecked(listFiltered.get(i).getTitle().equals(selectedItem));
            viewHolder.mText.setText(listFiltered.get(i).getTitle());

            if (type.equals(Constant.RazorPayPaymentType.WALLET)){

                Log.e(TAG,"wallet: "+ listFiltered.get(i).getTitle()+" ,"+listFiltered.get(i).getWalletIcon());
            /*Glide.with(mContext)
                   // .load("https://cdn.razorpay.com/wallet/amazonpay.png")
                    .load(listFiltered.get(i).getWalletIcon())
                    //.skipMemoryCache(false)
                   // .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    //.placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_placeholder)
                    .dontAnimate()
                    .into(viewHolder.ivWalletIcon);*/

                if (Constant.WalletName.PAYTM.equals(listFiltered.get(i).getWalletName())){
                    viewHolder.ivPayTmWalletIcon.setVisibility(View.VISIBLE);
                    viewHolder.ivWalletIcon.setVisibility(View.GONE);
                }else {
                    viewHolder.ivPayTmWalletIcon.setVisibility(View.GONE);
                    if (!TextUtils.isEmpty(listFiltered.get(i).getWalletIcon())) {
                        viewHolder.ivWalletIcon.setVisibility(View.VISIBLE);
                        Picasso.get()
                                .load(listFiltered.get(i).getWalletIcon())
                                .placeholder(R.drawable.ic_placeholder)
                                .error(R.drawable.ic_placeholder)
                                .into(viewHolder.ivWalletIcon);
                    }
                }
            }else {
                viewHolder.ivWalletIcon.setVisibility(View.GONE);
                viewHolder.ivPayTmWalletIcon.setVisibility(View.GONE);
                viewHolder.ivPaymentIcon.setVisibility(View.VISIBLE);
                viewHolder.ivPaymentIcon.setImageResource(listFiltered.get(i).getIcon());
            }
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
        private ImageView ivPaymentIcon,ivWalletIcon,ivPayTmWalletIcon;

        public ViewHolder(final View inflate) {
            super(inflate);
            try {
                mText = inflate.findViewById(R.id.text);
                mRadio = inflate.findViewById(R.id.radio);
                ivPaymentIcon = inflate.findViewById(R.id.ivPaymentIcon);
                ivWalletIcon = inflate.findViewById(R.id.ivWalletIcon);
                ivPayTmWalletIcon = inflate.findViewById(R.id.ivPayTmWalletIcon);
                View.OnClickListener clickListener = v -> {
                    mSelectedItemIndex = getAdapterPosition();
                    selectedItem = listFiltered.get(mSelectedItemIndex).getTitle();
                    if (Constant.RazorPayPaymentType.WALLET.equals(type)) {
                        selectedPaymentMethod.getSelectedPaymentMethod(listFiltered.get(mSelectedItemIndex).getWalletName());
                    }else {
                        selectedPaymentMethod.getSelectedPaymentMethod(listFiltered.get(mSelectedItemIndex).getTitle());
                    }

                    notifyDataSetChanged();
                };
                itemView.setOnClickListener(clickListener);
                mRadio.setOnClickListener(clickListener);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    /*private class ValueFilter extends Filter{
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                ArrayList<RazorPayPayment> filterList = new ArrayList<>();
                for (int i = 0; i < mItems.size(); i++) {
                    if ((mItems.get(i).getTitle().toLowerCase()).contains(constraint.toString().toLowerCase())) {
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
            listFiltered = (ArrayList<RazorPayPayment>) results.values;
            notifyDataSetChanged();
        }
    }*/


}