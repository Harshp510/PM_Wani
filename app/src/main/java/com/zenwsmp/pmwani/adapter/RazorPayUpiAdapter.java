package com.zenwsmp.pmwani.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zenwsmp.pmwani.R;

import java.util.ArrayList;



public class RazorPayUpiAdapter extends BaseAdapter {
    private static final String TAG = RazorPayUpiAdapter.class.getSimpleName() ;
    Context context;
    private final ArrayList<String> mItems;


    public RazorPayUpiAdapter(Context applicationContext,ArrayList<String> items) {
        this.context = applicationContext;
        this.mItems = items;
    }


    @Override
    public int getCount() {
        return mItems.size();
    }
    @Override
    public Object getItem(int i) {
        return mItems.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.razorpay_upi_item,null);// inflate the layout
        //ImageView icon =  view.findViewById(R.id.ivUpiIcon); // get the reference of ImageView
        LinearLayout llUpi = view.findViewById(R.id.llUpi); // get the reference of ImageView
        TextView mText = view.findViewById(R.id.textUpiName); // get the reference of ImageView

        mText.setText(mItems.get(i));

        llUpi.setOnClickListener(view1 -> {
            String selectedItem = mItems.get(i);
            Log.e(TAG,"selectedItem: "+selectedItem);
            notifyDataSetChanged();
        });
        return view;
    }
}



/*
public class RazorPayUpiAdapter extends RecyclerView.Adapter<RazorPayUpiAdapter.ViewHolder>  {
    private static final String TAG = RazorPayUpiAdapter.class.getSimpleName() ;
    private ArrayList<String> mItems;
    private ArrayList<String> listFiltered;
    private Context mContext;
    private SelectedPaymentMethod selectedPaymentMethod;


    public interface SelectedPaymentMethod {
        void getSelectedPaymentMethod(String s);
    }

    public void setPayment(SelectedPaymentMethod payment) {
        this.selectedPaymentMethod = payment;
    }

    public RazorPayUpiAdapter(Context context, ArrayList<String> items) {
        mContext = context;
        this.mItems = items;
        this.listFiltered = items;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.mText.setText(listFiltered.get(i));
    }

    @Override
    public int getItemCount() {
        return listFiltered.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View view = inflater.inflate(R.layout.razorpay_upi_item, viewGroup, false);
        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivUpiIcon;
        private TextView mText;
        private LinearLayout llUpi;

        public ViewHolder(final View inflate) {
            super(inflate);
            llUpi = inflate.findViewById(R.id.llUpi);
            ivUpiIcon = inflate.findViewById(R.id.ivUpiIcon);
            mText = inflate.findViewById(R.id.textUpiName);

            View.OnClickListener clickListener = v -> {

                String selectedItem = listFiltered.get(getAdapterPosition());
                Log.e(TAG,"selectedItem: "+selectedItem);
                notifyDataSetChanged();
            };

            itemView.setOnClickListener(clickListener);

        }
    }
}*/
