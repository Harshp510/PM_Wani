package com.zenwsmp.pmwani;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.zenwsmp.pmwani.adapter.PlanAdapter;
import com.zenwsmp.pmwani.adapter.SessionAdapter;
import com.zenwsmp.pmwani.model.PlanBean;
import com.zenwsmp.pmwani.model.SessionBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class SessionListActivity extends Base_Drawer {
    private static AsyncHttpClient client = new AsyncHttpClient();
    SharedPreferences sp_userdetail;
    String Str_cpp;
    String Str_UserName;
    String Str_email;
    String Str_password;
    String Str_fullname;
    String Str_mobile;
    SwipeRefreshLayout refreshLayout;
    SessionAdapter adapter;
    List<SessionBean> list=new ArrayList<>();
    LinearLayout connected_client_txt_norecord;
    RecyclerView recycler_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_session_list);
        View rootView = getLayoutInflater().inflate(R.layout.activity_session_list, frameLayout);
        search_icon.setVisibility(View.GONE);
        txt_menuTitle.setText("Recent Sessions");
        sp_userdetail = getSharedPreferences("userdetail.txt", Context.MODE_PRIVATE);

        try {
            //Str_Userid = sp_userdetail.getString("user_id", null);
            Str_fullname = sp_userdetail.getString("full_name", null);
            Str_email = sp_userdetail.getString("email", null);
            Str_mobile = sp_userdetail.getString("mobile", null);
            Str_cpp = sp_userdetail.getString("cpp", null);
            Str_password = sp_userdetail.getString("password", null);
            txt_username.setText("Hi "+Str_fullname.toUpperCase());
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        connected_client_txt_norecord=findViewById(R.id.connected_client_txt_norecord);
        refreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe_to_refresh);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.maingreen));
        recycler_view= findViewById(R.id.recycle_devicedetail);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));

        refreshLayout.setOnRefreshListener(() -> {
            if(new connectionDector(SessionListActivity.this).isConnectingToInternet()) {

                try {
                    GetSessionList();
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
                //getDeviceDetail();
            }
            else
            {
                refreshLayout.setRefreshing(false);
                new ConfigAPI().ShowToastMessage(SessionListActivity.this,"No Internet Connection");
            }

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

            if(new connectionDector(SessionListActivity.this).isConnectingToInternet()) {

                try {
                    GetSessionList();
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
                //getDeviceDetail();
            }
            else
            {
                refreshLayout.setRefreshing(false);
                new ConfigAPI().ShowToastMessage(SessionListActivity.this,"No Internet Connection");
            }
        }
    };
    private void GetSessionList() throws UnsupportedEncodingException {



       /* RequestParams params = new RequestParams();
        params.put("",jsonObject);
        // StringEntity entity = new StringEntity(params.toString());
        final ByteArrayEntity entity = new ByteArrayEntity(jsonObject.toString().getBytes("UTF-8"));
        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));*/
        //  params.put("Password",jsonpassword);
        final String url= ConfigAPI.MAIN_URL+"session_list";
        Log.d("url",url);
        client.addHeader(ConfigAPI.Authorization,ConfigAPI.Bearer+sp_userdetail.getString("auth_token",null));
        client.setTimeout(20*1000);
        client.setMaxRetriesAndTimeout(0,10*1000);
        client.get(SessionListActivity.this,url,null,new JsonHttpResponseHandler()
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
                            SessionBean wlan_bean=new SessionBean();
                            JSONObject object=array_wlanList.getJSONObject(i);

                            wlan_bean.setIpaddress(object.getString("from_ipaddress"));
                            wlan_bean.setMac(object.getString("calling_mac"));
                            wlan_bean.setDatausage(object.getString("total_data"));
                            wlan_bean.setConnected_since(object.getString("session_time"));



                            list.add(wlan_bean);
                        }

                        adapter = new SessionAdapter(list,SessionListActivity.this);
                        // ((Wlan_Adapter) wlan_adapter).setMode(Attributes.Mode.Single);
                        recycler_view.setAdapter(adapter);
                        refreshLayout.setRefreshing(false);
                       /* adapter.setClickListener((view, groupclass, postion) -> {
                            switch (view.getId()){
                                case R.id.btn_buy:
                                    Log.d("click", "ok");


                                    break;
                            }
                        });*/
                        if(list.size()>0)
                        {
                            connected_client_txt_norecord.setVisibility(View.GONE);
                        }
                        else {
                            connected_client_txt_norecord.setVisibility(View.VISIBLE);
                        }

                    }else
                    {

                        new ConfigAPI().ShowToastMessage(SessionListActivity.this,responseMsg);
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
                        new ConfigAPI().ShowToastMessage(SessionListActivity.this,errorResponse.getString("message"));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    new ConfigAPI().ShowToastMessage(SessionListActivity.this,"Something went wrong,Please try again later");
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);

                refreshLayout.setRefreshing(false);
                if(responseString!=null){
                    try{
                        new ConfigAPI().ShowToastMessage(SessionListActivity.this,responseString);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    new ConfigAPI().ShowToastMessage(SessionListActivity.this,"Something went wrong,Please try again later");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                refreshLayout.setRefreshing(false);
                if(errorResponse!=null){
                    try{
                        new ConfigAPI().ShowToastMessage(SessionListActivity.this,errorResponse.toString());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    new ConfigAPI().ShowToastMessage(SessionListActivity.this,"Something went wrong,Please try again later");
                }
            }
        });


    }
}