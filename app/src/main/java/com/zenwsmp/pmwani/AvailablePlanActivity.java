package com.zenwsmp.pmwani;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.zenwsmp.pmwani.activity.RazorPayOrderPlaceActivity;
import com.zenwsmp.pmwani.adapter.PlanAdapter;
import com.zenwsmp.pmwani.common.Constant;
import com.zenwsmp.pmwani.model.PlanBean;
import com.zenwsmp.pmwani.objects.TicketSessionData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class AvailablePlanActivity extends Base_Drawer implements MaterialSearchView.OnQueryTextListener {

    private static AsyncHttpClient client = new AsyncHttpClient();
    SharedPreferences sp_userdetail;
    String Str_cpp;
    String Str_UserName;
    String Str_email;
    String Str_password;
    String Str_fullname;
    String Str_mobile;
    SwipeRefreshLayout refreshLayout;
    PlanAdapter adapter;
    List<PlanBean> list=new ArrayList<>();
    LinearLayout connected_client_txt_norecord;
    RecyclerView recycler_view;
    private TicketSessionData eventSessionData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_available_plan);
        View rootView = getLayoutInflater().inflate(R.layout.activity_available_plan, frameLayout);
        search_icon.setVisibility(View.VISIBLE);
        txt_menuTitle.setText("Available Plan");
        sp_userdetail = getSharedPreferences("userdetail.txt", Context.MODE_PRIVATE);
        eventSessionData = new TicketSessionData();
        try {
            //Str_Userid = sp_userdetail.getString("user_id", null);
            Str_fullname = sp_userdetail.getString("full_name", null);
            Str_email = sp_userdetail.getString("email", null);
            Str_mobile = sp_userdetail.getString("mobile", null);
            Str_cpp = sp_userdetail.getString("cpp", null);
            Str_password = sp_userdetail.getString("password", null);

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        sv.setOnQueryTextListener(this);
        connected_client_txt_norecord=findViewById(R.id.connected_client_txt_norecord);
        refreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe_to_refresh);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.maingreen));
        recycler_view= findViewById(R.id.recycle_devicedetail);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));

        refreshLayout.setOnRefreshListener(() -> {
            if(new connectionDector(AvailablePlanActivity.this).isConnectingToInternet()) {

                try {
                    GetPlanList(ConfigAPI.ACCESS_TOKEN);
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
                //getDeviceDetail();
            }
            else
            {
                refreshLayout.setRefreshing(false);
                new ConfigAPI().ShowToastMessage(AvailablePlanActivity.this,"No Internet Connection");
            }

        });
        search_icon.setOnClickListener(v -> {
            sv.showSearch(true);
           // dialog.dismiss();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                swipeRefreshListner.onRefresh();
            }
        });
    }
    SwipeRefreshLayout.OnRefreshListener swipeRefreshListner = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {

            if(new connectionDector(AvailablePlanActivity.this).isConnectingToInternet()) {

                try {
                    GetPlanList(ConfigAPI.ACCESS_TOKEN);
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
                //getDeviceDetail();
            }
            else
            {
                refreshLayout.setRefreshing(false);
                new ConfigAPI().ShowToastMessage(AvailablePlanActivity.this,"No Internet Connection");
            }
        }
    };
    private void GetPlanList(String token) throws UnsupportedEncodingException {



       /* RequestParams params = new RequestParams();
        params.put("",jsonObject);
        // StringEntity entity = new StringEntity(params.toString());
        final ByteArrayEntity entity = new ByteArrayEntity(jsonObject.toString().getBytes("UTF-8"));
        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));*/
        //  params.put("Password",jsonpassword);
        final String url= ConfigAPI.MAIN_URL+"plan/list?token="+token;
        Log.d("url",url);
        client.addHeader(ConfigAPI.Authorization,ConfigAPI.Bearer+sp_userdetail.getString("auth_token",null));
        client.setTimeout(20*1000);
        client.setMaxRetriesAndTimeout(0,10*1000);
        client.get(AvailablePlanActivity.this,url,null,new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("res",response.toString());

                try {

                    int responseCode=response.getInt("responseCode");
                    String responseMsg=response.getString("message");

                    if(responseCode==200){
                        JSONArray array_wlanList = response.getJSONArray("data");
                        Log.d("array_size",""+array_wlanList.length());
                        list.clear();
                        for (int i = 0; i < array_wlanList.length(); i++){
                            PlanBean wlan_bean=new PlanBean();
                            JSONObject object=array_wlanList.getJSONObject(i);

                            wlan_bean.setId(object.getString("id"));
                            wlan_bean.setPlan_name(object.getString("plan_name"));
                            wlan_bean.setDescription(object.getString("description"));
                            wlan_bean.setPrice(object.getInt("price"));
                            wlan_bean.setPrice_unit(object.getString("price_unit"));
                            wlan_bean.setAllow_time(object.getInt("allow_time"));
                            wlan_bean.setAllow_time_unit(object.getString("allow_time_unit"));
                            wlan_bean.setPlan_validity(object.getInt("plan_validity"));
                            wlan_bean.setPlan_validity_unit(object.getString("plan_validity_unit"));


                            list.add(wlan_bean);
                        }

                        adapter = new PlanAdapter(list,AvailablePlanActivity.this);
                        // ((Wlan_Adapter) wlan_adapter).setMode(Attributes.Mode.Single);
                        recycler_view.setAdapter(adapter);
                        refreshLayout.setRefreshing(false);
                        adapter.setClickListener((view, groupclass, postion) -> {
                            switch (view.getId()){
                                case R.id.btn_buy:
                                    Log.d("click", "ok");
                                    eventSessionData.setNetamount(groupclass.getPrice());
                                    Intent intent=new Intent(AvailablePlanActivity.this, RazorPayOrderPlaceActivity.class);
                                    intent.putExtra("planid",groupclass.getId());
                                    intent.putExtra("planname",groupclass.getPlan_name());
                                    intent.putExtra(Constant.ScreenExtras.TICKET_SESSION_DATA,eventSessionData);
                                    startActivity(intent);
                                    break;
                            }
                        });
                        if(list.size()>0)
                        {
                            connected_client_txt_norecord.setVisibility(View.GONE);
                        }
                        else {
                            connected_client_txt_norecord.setVisibility(View.VISIBLE);
                        }

                    }else
                    {

                        new ConfigAPI().ShowToastMessage(AvailablePlanActivity.this,responseMsg);
                    }



                }
                catch (Exception e)
                {

                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                refreshLayout.setRefreshing(false);
                if(errorResponse!=null){
                    try{
                        new ConfigAPI().ShowToastMessage(AvailablePlanActivity.this,errorResponse.getString("message"));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    new ConfigAPI().ShowToastMessage(AvailablePlanActivity.this,"Something went wrong,Please try again later");
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);

                refreshLayout.setRefreshing(false);
                if(responseString!=null){
                    try{
                        new ConfigAPI().ShowToastMessage(AvailablePlanActivity.this,responseString);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    new ConfigAPI().ShowToastMessage(AvailablePlanActivity.this,"Something went wrong,Please try again later");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                refreshLayout.setRefreshing(false);
                if(errorResponse!=null){
                    try{
                        new ConfigAPI().ShowToastMessage(AvailablePlanActivity.this,errorResponse.toString());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    new ConfigAPI().ShowToastMessage(AvailablePlanActivity.this,"Something went wrong,Please try again later");
                }
            }
        });


    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {

        List<PlanBean> tempArrayList = new ArrayList<>();
       // ArrayList<DeviceDetailBean> tempdeviceDetailBeanArrayList = new ArrayList<>();
        int textlength = s.length();
        System.out.println(textlength);
        tempArrayList.clear();
        for (PlanBean c : list) {
            if ((textlength <= c.getPlan_name().length()) ) {
                if ((c.getPlan_name().toLowerCase().contains(s.toLowerCase()))) {

                    tempArrayList.add(c);
                }
            }

        }
        adapter = new PlanAdapter(tempArrayList,AvailablePlanActivity.this);
        recycler_view.setAdapter(adapter);

        return true;
    }


}