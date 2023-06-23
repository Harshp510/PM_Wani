package com.zenwsmp.pmwani;

/* *
 *Created By BirenLadva in January,2021
 */

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.loopj.android.http.AsyncHttpClient;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class Base_Drawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected FrameLayout frameLayout;
    private Context context;
    private Toolbar toolbar;
    public TextView txt_menuTitle;
    public ImageView img_menuOption;
    private DrawerLayout drawer;
    ActionBarDrawerToggle toggle;

    private boolean backPressedToExitOnce = false;

    int rotationAngle_hr = 0;
    int duration_time = 180;

    String str_method="";

    LinearLayout ll_hr_menu_detail,ll_dashboard,ll_Employee_Directory,ll_time_keeping,ll_attendance_code,
            ll_Leave_application,ll_early_late_app,ll_Out_on_office_duty,ll_personal_timeout,ll_Work_From_Home_application,
            ll_my_approvals, ll_earnings,ll_taxation,ll_loans;
    RelativeLayout ll_hr_form;
    ImageView img_hr_menu_down,img_Dashboard,img_Employee_Directory,img_time_keeping,img_attendance_code,
            img_my_approvals,img_earnings,img_loans,img_taxation,img_assets;
    public ImageView filter_icon,profile_icon,search_icon;
    TextView txt_dashboard, txt_employee_directory, txt_time_keeping, txt_attendance_code,
            txt_my_approvals, txt_earnings, txt_taxation, txt_loans, txt_assets;
    TextView txt_username,txt_leave_app,txt_early_late_app,txt_out_on_office_duty,txt_personal_timeout,txt_work_from_home_app,txt_half_day_app;

    RelativeLayout RL_Search_Panel;
    ImageView img_search_panel_close,img_search_panel_clear;
    EditText txt_search_panel;



    SharedPreferences sp_user_detail;
    String EmployeeId,Employee_Name,image_url;

    private static AsyncHttpClient client;
    MaterialSearchView sv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_drawer);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);



        sp_user_detail= getSharedPreferences("UserDetail.txt", Context.MODE_PRIVATE);
        EmployeeId=sp_user_detail.getString("EmployeeId",null);
        Employee_Name=sp_user_detail.getString("Employee_Name",null);

      //  Log.d("EmployeeId",EmployeeId);

      //  db_helper=new DB_Helper(Base_Drawer.this);

      /*  RL_Search_Panel=findViewById(R.id.RL_Search_Panel);
        RL_Search_Panel.setVisibility(View.GONE);

        img_search_panel_close=findViewById(R.id.img_search_panel_close);
        img_search_panel_close.setVisibility(View.GONE);
        img_search_panel_clear=findViewById(R.id.img_search_panel_clear);
        img_search_panel_clear.setVisibility(View.GONE);
        txt_search_panel=findViewById(R.id.txt_search_panel);*/

        ll_hr_form=findViewById(R.id.ll_hr_form);
        ll_hr_menu_detail=findViewById(R.id.ll_hr_menu_detail);
        ll_dashboard=findViewById(R.id.ll_dashboard);
        ll_Employee_Directory=findViewById(R.id.ll_Employee_Directory);
        ll_time_keeping=findViewById(R.id.ll_time_keeping);
        ll_attendance_code=findViewById(R.id.ll_attendance_code);
        ll_Leave_application=findViewById(R.id.ll_Leave_application);
        ll_early_late_app=findViewById(R.id.ll_early_late_app);
        ll_Out_on_office_duty=findViewById(R.id.ll_Out_on_office_duty);
        ll_personal_timeout=findViewById(R.id.ll_personal_timeout);
        ll_Work_From_Home_application=findViewById(R.id.ll_Work_From_Home_application);
//        ll_my_approvals=findViewById(R.id.ll_my_approvals);
        ll_earnings=findViewById(R.id.ll_earnings);
        txt_username=findViewById(R.id.txt_username);
//        ll_taxation=findViewById(R.id.ll_taxation);
//        ll_loans=findViewById(R.id.ll_loans);
        sv = findViewById(R.id.sv);

        img_hr_menu_down = findViewById(R.id.img_hr_menu_down);
        img_Dashboard = findViewById(R.id.img_Dashboard);
        img_Employee_Directory = findViewById(R.id.img_Employee_Directory);
        img_time_keeping = findViewById(R.id.img_time_keeping);
        img_attendance_code = findViewById(R.id.img_attendance_code);
//        img_my_approvals = findViewById(R.id.img_my_approvals);
        img_earnings = findViewById(R.id.img_earnings);
//        img_taxation = findViewById(R.id.img_taxation);
//        img_loans = findViewById(R.id.img_loans);

        txt_dashboard = findViewById(R.id.txt_dashboard);
        txt_employee_directory = findViewById(R.id.txt_employee_directory);
        txt_time_keeping = findViewById(R.id.txt_time_keeping);
        txt_attendance_code = findViewById(R.id.txt_attendance_code);
//        txt_my_approvals = findViewById(R.id.txt_my_approvals);
        txt_earnings = findViewById(R.id.txt_earnings);
//        txt_taxation=findViewById(R.id.txt_taxation);
//        txt_loans = findViewById(R.id.txt_loans);

        txt_leave_app=findViewById(R.id.txt_leave_app);
        txt_early_late_app=findViewById(R.id.txt_early_late_app);
        txt_out_on_office_duty=findViewById(R.id.txt_out_on_office_duty);
        txt_personal_timeout=findViewById(R.id.txt_personal_timeout);
        txt_work_from_home_app=findViewById(R.id.txt_work_from_home_app);
        profile_icon=findViewById(R.id.profile_icon);
     //   filter_icon=findViewById(R.id.filter_icon);
        search_icon=findViewById(R.id.search_icon);
     /*   profile_icon=findViewById(R.id.profile_icon);
        filter_icon=findViewById(R.id.filter_icon);
        search_icon=findViewById(R.id.search_icon);

        image_url="https://ui-avatars.com/api/?name="+ URLEncoder.encode(Employee_Name)+"&rounded=true&size=512&color=407BFF&background=d9e4fc";
        Picasso.get().load(image_url).into(profile_icon);*/

        context=this;
        toolbar = findViewById(R.id.toolbar);

        txt_menuTitle = findViewById(R.id.txt_menuTitle);
        img_menuOption = findViewById(R.id.img_menuOption);
        img_menuOption.setBackgroundResource(R.drawable.ic_drawer_menu);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerview = navigationView.getHeaderView(0);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        img_menuOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
                Log.d("flag",""+str_method);
              //  set_background_title(mApplication.getStr_nav_view_flag());
            }
        });

        frameLayout=findViewById(R.id.container);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView.setNavigationItemSelectedListener(this);

      /*  profile_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_method="";
               // mApplication.setStr_nav_view_flag(str_method);
              //  set_background_title(str_method);
               // Intent intent=new Intent(Base_Drawer.this, Profile.class);
               // startActivity(intent);
            }
        });*/
        txt_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_method="Dashboard";
             //   mApplication.setStr_nav_view_flag(str_method);
              //  set_background_title(str_method);
                Intent intent=new Intent(Base_Drawer.this, DashboardActivity.class);
                startActivity(intent);
            }
        });
        txt_employee_directory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   str_method="EmployeeDirectory";
             //   mApplication.setStr_nav_view_flag(str_method);
              //  set_background_title(str_method);
                Intent intent=new Intent(Base_Drawer.this, AvailablePlanActivity.class);
                startActivity(intent);
            }
        });
        txt_time_keeping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Base_Drawer.this, SessionListActivity.class);
                startActivity(intent);
               // str_method="TimeKeeping";
              //  mApplication.setStr_nav_view_flag(str_method);
              //  set_background_title(str_method);

              /*  Cursor cursor_statusemp = db_helper.getuserfacedaata(EmployeeId);
                Log.d("cursor_getuserfacedaata", "" +cursor_statusemp.getCount());

                if (cursor_statusemp.getCount() > 0)
                {
                    Intent intent=new Intent(Base_Drawer.this, Time_Keeping.class);
                    startActivity(intent);
                }
                else {
                    Intent intent=new Intent(Base_Drawer.this, Admin_Home_Activity.class);
                    startActivity(intent);
                }*/
            }
        });
        txt_attendance_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // str_method="Attendance";
               // mApplication.setStr_nav_view_flag(str_method);
              //  set_background_title(str_method);
              //  Intent intent=new Intent(Base_Drawer.this, Attendance_Code.class);
              //  startActivity(intent);
                Intent intent=new Intent(Base_Drawer.this, TransactionListActivity.class);
                startActivity(intent);
            }
        });
        ll_hr_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ll_hr_menu_detail.getVisibility() == View.VISIBLE) {
                    ObjectAnimator anim = ObjectAnimator.ofFloat(img_hr_menu_down, "rotation", rotationAngle_hr, rotationAngle_hr - 180);
                    anim.setDuration(duration_time);
                    anim.start();
                    rotationAngle_hr -= 180;
                    rotationAngle_hr = rotationAngle_hr % 360;
                    collapse(ll_hr_menu_detail);
                } else {
                    ObjectAnimator anim = ObjectAnimator.ofFloat(img_hr_menu_down, "rotation", rotationAngle_hr, rotationAngle_hr + 180);
                    anim.setDuration(duration_time);
                    anim.start();
                    rotationAngle_hr += 180;
                    rotationAngle_hr = rotationAngle_hr % 360;
                    expand(ll_hr_menu_detail);
                }
            }
        });
        txt_leave_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // str_method="LeaveApplication";
               // mApplication.setStr_nav_view_flag(str_method);
               // set_background_title(str_method);
               // Intent intent=new Intent(Base_Drawer.this, Leave_Application.class);
               // startActivity(intent);
            }
        });
        txt_early_late_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  str_method="EarlyLate";
              // mApplication.setStr_nav_view_flag(str_method);
               // set_background_title(str_method);
               // Intent intent=new Intent(Base_Drawer.this, Early_Late_Application.class);
               // startActivity(intent);
            }
        });
        txt_out_on_office_duty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // str_method="OOODApp";
               // mApplication.setStr_nav_view_flag(str_method);
              //  set_background_title(str_method);
               // Intent intent=new Intent(Base_Drawer.this, Out_On_Office_Duty.class);
               // startActivity(intent);
            }
        });
        txt_personal_timeout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  str_method="PersonalTimeout";
              //  mApplication.setStr_nav_view_flag(str_method);
              //  set_background_title(str_method);
               // Intent intent=new Intent(Base_Drawer.this, Personal_Timeout.class);
               // startActivity(intent);
                finish();
            }
        });
        txt_work_from_home_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  str_method="WFHApp";
              //  mApplication.setStr_nav_view_flag(str_method);
              //  set_background_title(str_method);
               // Intent intent=new Intent(Base_Drawer.this, Work_From_Home_Application.class);
               // startActivity(intent);
            }
        });
//        txt_my_approvals.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                str_method="MyApprovals";
//                mApplication.setStr_nav_view_flag(str_method);
//                set_background_title(str_method);
//                Intent intent=new Intent(Base_Drawer.this, My_Approvals.class);
//                startActivity(intent);
//                finish();
//            }
//        });
        txt_earnings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // str_method="Earnings";
               // mApplication.setStr_nav_view_flag(str_method);
               // set_background_title(str_method);
               // Intent intent=new Intent(Base_Drawer.this, Earnings.class);
               // startActivity(intent);
            }
        });
//        txt_loans.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                str_method="Loans";
//                mApplication.setStr_nav_view_flag(str_method);
//                set_background_title(str_method);
//                Intent intent=new Intent(Base_Drawer.this, Loans.class);
//                startActivity(intent);
//                finish();
//            }
//        });
    }

 /*   public void set_background_title(String str_flag) {
        Log.d("method","set_background_title");
        Log.d("method",""+str_flag);

        if(str_flag.equalsIgnoreCase("Dashboard")) {
            ll_dashboard.setBackgroundResource(R.drawable.nav_drawer_bg);
            txt_dashboard.setTextColor(getResources().getColor(R.color.txt_color));
            img_Dashboard.setColorFilter(getResources().getColor(R.color.txt_color));

            ll_Employee_Directory.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_employee_directory.setTextColor(Color.parseColor("#000000"));
            img_Employee_Directory.setColorFilter(getResources().getColor(R.color.img_color));

            ll_time_keeping.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_time_keeping.setTextColor(Color.parseColor("#000000"));
            img_time_keeping.setColorFilter(getResources().getColor(R.color.img_color));

            ll_attendance_code.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_attendance_code.setTextColor(Color.parseColor("#000000"));
            img_attendance_code.setColorFilter(getResources().getColor(R.color.img_color));

            ll_Leave_application.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

            ll_early_late_app.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

            ll_Out_on_office_duty.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

            ll_personal_timeout.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

            ll_Work_From_Home_application.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

//            ll_my_approvals.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
//            txt_my_approvals.setTextColor(Color.parseColor("#000000"));
//            img_my_approvals.setColorFilter(getResources().getColor(R.color.img_color));

            ll_earnings.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_earnings.setTextColor(Color.parseColor("#000000"));
            img_earnings.setColorFilter(getResources().getColor(R.color.img_color));

//            ll_loans.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
//            txt_loans.setTextColor(Color.parseColor("#000000"));
//            img_loans.setColorFilter(getResources().getColor(R.color.img_color));
        }

        else if (str_flag.equalsIgnoreCase("EmployeeDirectory")) {
            ll_dashboard.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_dashboard.setTextColor(Color.parseColor("#000000"));
            img_Dashboard.setColorFilter(getResources().getColor(R.color.img_color));

            ll_Employee_Directory.setBackgroundResource(R.drawable.nav_drawer_bg);
            txt_employee_directory.setTextColor(getResources().getColor(R.color.txt_color));
            img_Employee_Directory.setColorFilter(getResources().getColor(R.color.txt_color));

            ll_time_keeping.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_time_keeping.setTextColor(Color.parseColor("#000000"));
            img_time_keeping.setColorFilter(getResources().getColor(R.color.img_color));

            ll_attendance_code.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_attendance_code.setTextColor(Color.parseColor("#000000"));
            img_attendance_code.setColorFilter(getResources().getColor(R.color.img_color));

            ll_Leave_application.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

            ll_early_late_app.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

            ll_Out_on_office_duty.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

            ll_personal_timeout.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

            ll_Work_From_Home_application.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

//            ll_my_approvals.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
//            txt_my_approvals.setTextColor(Color.parseColor("#000000"));
//            img_my_approvals.setColorFilter(getResources().getColor(R.color.img_color));

            ll_earnings.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_earnings.setTextColor(Color.parseColor("#000000"));
            img_earnings.setColorFilter(getResources().getColor(R.color.img_color));

//            ll_loans.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
//            txt_loans.setTextColor(Color.parseColor("#000000"));
//            img_loans.setColorFilter(getResources().getColor(R.color.img_color));
        }

        else if (str_flag.equalsIgnoreCase("TimeKeeping")) {
            ll_dashboard.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_dashboard.setTextColor(Color.parseColor("#000000"));
            img_Dashboard.setColorFilter(getResources().getColor(R.color.img_color));

            ll_Employee_Directory.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_employee_directory.setTextColor(Color.parseColor("#000000"));
            img_Employee_Directory.setColorFilter(getResources().getColor(R.color.img_color));

            ll_time_keeping.setBackgroundResource(R.drawable.nav_drawer_bg);
            txt_time_keeping.setTextColor(getResources().getColor(R.color.txt_color));
            img_time_keeping.setColorFilter(getResources().getColor(R.color.txt_color));

            ll_attendance_code.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_attendance_code.setTextColor(Color.parseColor("#000000"));
            img_attendance_code.setColorFilter(getResources().getColor(R.color.img_color));

            ll_Leave_application.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

            ll_early_late_app.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

            ll_Out_on_office_duty.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

            ll_personal_timeout.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

            ll_Work_From_Home_application.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

//            ll_my_approvals.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
//            txt_my_approvals.setTextColor(Color.parseColor("#000000"));
//            img_my_approvals.setColorFilter(getResources().getColor(R.color.img_color));

            ll_earnings.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_earnings.setTextColor(Color.parseColor("#000000"));
            img_earnings.setColorFilter(getResources().getColor(R.color.img_color));

//            ll_loans.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
//            txt_loans.setTextColor(Color.parseColor("#000000"));
//            img_loans.setColorFilter(getResources().getColor(R.color.img_color));
        }

        else if (str_flag.equalsIgnoreCase("Attendance")) {
            ll_dashboard.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_dashboard.setTextColor(Color.parseColor("#000000"));
            img_Dashboard.setColorFilter(getResources().getColor(R.color.img_color));

            ll_Employee_Directory.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_employee_directory.setTextColor(getResources().getColor(R.color.black));
            img_Employee_Directory.setColorFilter(getResources().getColor(R.color.img_color));

            ll_time_keeping.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_time_keeping.setTextColor(Color.parseColor("#000000"));
            img_time_keeping.setColorFilter(getResources().getColor(R.color.img_color));

            ll_attendance_code.setBackgroundResource(R.drawable.nav_drawer_bg);
            txt_attendance_code.setTextColor(getResources().getColor(R.color.txt_color));
            img_attendance_code.setColorFilter(getResources().getColor(R.color.txt_color));

            ll_Leave_application.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

            ll_early_late_app.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

            ll_Out_on_office_duty.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

            ll_personal_timeout.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

            ll_Work_From_Home_application.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

//            ll_my_approvals.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
//            txt_my_approvals.setTextColor(Color.parseColor("#000000"));
//            img_my_approvals.setColorFilter(getResources().getColor(R.color.img_color));

            ll_earnings.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_earnings.setTextColor(Color.parseColor("#000000"));
            img_earnings.setColorFilter(getResources().getColor(R.color.img_color));

//            ll_loans.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
//            txt_loans.setTextColor(Color.parseColor("#000000"));
//            img_loans.setColorFilter(getResources().getColor(R.color.img_color));
        }

        else if (str_flag.equalsIgnoreCase("LeaveApplication")) {
            ll_dashboard.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_dashboard.setTextColor(Color.parseColor("#000000"));
            img_Dashboard.setColorFilter(getResources().getColor(R.color.img_color));

            ll_Employee_Directory.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_employee_directory.setTextColor(getResources().getColor(R.color.black));
            img_Employee_Directory.setColorFilter(getResources().getColor(R.color.img_color));

            ll_time_keeping.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_time_keeping.setTextColor(Color.parseColor("#000000"));
            img_time_keeping.setColorFilter(getResources().getColor(R.color.img_color));

            ll_attendance_code.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_attendance_code.setTextColor(Color.parseColor("#000000"));
            img_attendance_code.setColorFilter(getResources().getColor(R.color.img_color));

            ll_Leave_application.setBackgroundResource(R.drawable.nav_drawer_bg);
            txt_leave_app.setTextColor(getResources().getColor(R.color.txt_color));
            if (ll_hr_menu_detail.getVisibility() == View.GONE)
            {
                ObjectAnimator anim = ObjectAnimator.ofFloat(img_hr_menu_down, "rotation", rotationAngle_hr, rotationAngle_hr + 180);
                anim.setDuration(duration_time);
                anim.start();
                rotationAngle_hr += 180;
                rotationAngle_hr = rotationAngle_hr % 360;
                expand(ll_hr_menu_detail);
            }

            ll_early_late_app.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

            ll_Out_on_office_duty.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

            ll_personal_timeout.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

            ll_Work_From_Home_application.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

//            ll_my_approvals.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
//            txt_my_approvals.setTextColor(Color.parseColor("#000000"));
//            img_my_approvals.setColorFilter(getResources().getColor(R.color.img_color));

            ll_earnings.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_earnings.setTextColor(Color.parseColor("#000000"));
            img_earnings.setColorFilter(getResources().getColor(R.color.img_color));

//            ll_loans.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
//            txt_loans.setTextColor(Color.parseColor("#000000"));
//            img_loans.setColorFilter(getResources().getColor(R.color.img_color));
        }

        else if (str_flag.equalsIgnoreCase("EarlyLate")) {
            ll_dashboard.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_dashboard.setTextColor(Color.parseColor("#000000"));
            img_Dashboard.setColorFilter(getResources().getColor(R.color.img_color));

            ll_Employee_Directory.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_employee_directory.setTextColor(getResources().getColor(R.color.black));
            img_Employee_Directory.setColorFilter(getResources().getColor(R.color.img_color));

            ll_time_keeping.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_time_keeping.setTextColor(Color.parseColor("#000000"));
            img_time_keeping.setColorFilter(getResources().getColor(R.color.img_color));

            ll_attendance_code.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_attendance_code.setTextColor(Color.parseColor("#000000"));
            img_attendance_code.setColorFilter(getResources().getColor(R.color.img_color));

            ll_Leave_application.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

            ll_early_late_app.setBackgroundResource(R.drawable.nav_drawer_bg);
            txt_early_late_app.setTextColor(getResources().getColor(R.color.txt_color));
            if (ll_hr_menu_detail.getVisibility() == View.GONE)
            {
                ObjectAnimator anim = ObjectAnimator.ofFloat(img_hr_menu_down, "rotation", rotationAngle_hr, rotationAngle_hr + 180);
                anim.setDuration(duration_time);
                anim.start();
                rotationAngle_hr += 180;
                rotationAngle_hr = rotationAngle_hr % 360;
                expand(ll_hr_menu_detail);
            }

            ll_Out_on_office_duty.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

            ll_personal_timeout.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

            ll_Work_From_Home_application.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

//            ll_my_approvals.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
//            txt_my_approvals.setTextColor(Color.parseColor("#000000"));
//            img_my_approvals.setColorFilter(getResources().getColor(R.color.img_color));

            ll_earnings.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_earnings.setTextColor(Color.parseColor("#000000"));
            img_earnings.setColorFilter(getResources().getColor(R.color.img_color));

//            ll_loans.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
//            txt_loans.setTextColor(Color.parseColor("#000000"));
//            img_loans.setColorFilter(getResources().getColor(R.color.img_color));
        }

        else if (str_flag.equalsIgnoreCase("OOODApp")) {
            ll_dashboard.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_dashboard.setTextColor(Color.parseColor("#000000"));
            img_Dashboard.setColorFilter(getResources().getColor(R.color.img_color));

            ll_Employee_Directory.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_employee_directory.setTextColor(getResources().getColor(R.color.black));
            img_Employee_Directory.setColorFilter(getResources().getColor(R.color.img_color));

            ll_time_keeping.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_time_keeping.setTextColor(Color.parseColor("#000000"));
            img_time_keeping.setColorFilter(getResources().getColor(R.color.img_color));

            ll_attendance_code.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_attendance_code.setTextColor(Color.parseColor("#000000"));
            img_attendance_code.setColorFilter(getResources().getColor(R.color.img_color));

            ll_Leave_application.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

            ll_early_late_app.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

            ll_Out_on_office_duty.setBackgroundResource(R.drawable.nav_drawer_bg);
            txt_out_on_office_duty.setTextColor(getResources().getColor(R.color.txt_color));
            if (ll_hr_menu_detail.getVisibility() == View.GONE)
            {
                ObjectAnimator anim = ObjectAnimator.ofFloat(img_hr_menu_down, "rotation", rotationAngle_hr, rotationAngle_hr + 180);
                anim.setDuration(duration_time);
                anim.start();
                rotationAngle_hr += 180;
                rotationAngle_hr = rotationAngle_hr % 360;
                expand(ll_hr_menu_detail);
            }

            ll_personal_timeout.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

            ll_Work_From_Home_application.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

//            ll_my_approvals.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
//            txt_my_approvals.setTextColor(Color.parseColor("#000000"));
//            img_my_approvals.setColorFilter(getResources().getColor(R.color.img_color));

            ll_earnings.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_earnings.setTextColor(Color.parseColor("#000000"));
            img_earnings.setColorFilter(getResources().getColor(R.color.img_color));

//            ll_loans.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
//            txt_loans.setTextColor(Color.parseColor("#000000"));
//            img_loans.setColorFilter(getResources().getColor(R.color.img_color));
        }

        else if (str_flag.equalsIgnoreCase("PersonalTimeout")) {
            ll_dashboard.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_dashboard.setTextColor(Color.parseColor("#000000"));
            img_Dashboard.setColorFilter(getResources().getColor(R.color.img_color));

            ll_Employee_Directory.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_employee_directory.setTextColor(getResources().getColor(R.color.black));
            img_Employee_Directory.setColorFilter(getResources().getColor(R.color.img_color));

            ll_time_keeping.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_time_keeping.setTextColor(Color.parseColor("#000000"));
            img_time_keeping.setColorFilter(getResources().getColor(R.color.img_color));

            ll_attendance_code.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_attendance_code.setTextColor(Color.parseColor("#000000"));
            img_attendance_code.setColorFilter(getResources().getColor(R.color.img_color));

            ll_Leave_application.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

            ll_early_late_app.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

            ll_Out_on_office_duty.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

            ll_personal_timeout.setBackgroundResource(R.drawable.nav_drawer_bg);
            txt_personal_timeout.setTextColor(getResources().getColor(R.color.txt_color));
            if (ll_hr_menu_detail.getVisibility() == View.GONE)
            {
                ObjectAnimator anim = ObjectAnimator.ofFloat(img_hr_menu_down, "rotation", rotationAngle_hr, rotationAngle_hr + 180);
                anim.setDuration(duration_time);
                anim.start();
                rotationAngle_hr += 180;
                rotationAngle_hr = rotationAngle_hr % 360;
                expand(ll_hr_menu_detail);
            }

            ll_Work_From_Home_application.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

//            ll_my_approvals.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
//            txt_my_approvals.setTextColor(Color.parseColor("#000000"));
//            img_my_approvals.setColorFilter(getResources().getColor(R.color.img_color));

            ll_earnings.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_earnings.setTextColor(Color.parseColor("#000000"));
            img_earnings.setColorFilter(getResources().getColor(R.color.img_color));

//            ll_loans.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
//            txt_loans.setTextColor(Color.parseColor("#000000"));
//            img_loans.setColorFilter(getResources().getColor(R.color.img_color));
        }

        else if (str_flag.equalsIgnoreCase("WFHApp")) {
            ll_dashboard.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_dashboard.setTextColor(Color.parseColor("#000000"));
            img_Dashboard.setColorFilter(getResources().getColor(R.color.img_color));

            ll_Employee_Directory.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_employee_directory.setTextColor(getResources().getColor(R.color.black));
            img_Employee_Directory.setColorFilter(getResources().getColor(R.color.img_color));

            ll_time_keeping.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_time_keeping.setTextColor(Color.parseColor("#000000"));
            img_time_keeping.setColorFilter(getResources().getColor(R.color.img_color));

            ll_attendance_code.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_attendance_code.setTextColor(Color.parseColor("#000000"));
            img_attendance_code.setColorFilter(getResources().getColor(R.color.img_color));

            ll_Leave_application.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

            ll_early_late_app.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

            ll_Out_on_office_duty.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

            ll_personal_timeout.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

            ll_Work_From_Home_application.setBackgroundResource(R.drawable.nav_drawer_bg);
            txt_work_from_home_app.setTextColor(getResources().getColor(R.color.txt_color));
            if (ll_hr_menu_detail.getVisibility() == View.GONE)
            {
                ObjectAnimator anim = ObjectAnimator.ofFloat(img_hr_menu_down, "rotation", rotationAngle_hr, rotationAngle_hr + 180);
                anim.setDuration(duration_time);
                anim.start();
                rotationAngle_hr += 180;
                rotationAngle_hr = rotationAngle_hr % 360;
                expand(ll_hr_menu_detail);
            }

//            ll_my_approvals.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
//            txt_my_approvals.setTextColor(Color.parseColor("#000000"));
//            img_my_approvals.setColorFilter(getResources().getColor(R.color.img_color));

            ll_earnings.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_earnings.setTextColor(Color.parseColor("#000000"));
            img_earnings.setColorFilter(getResources().getColor(R.color.img_color));

//            ll_loans.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
//            txt_loans.setTextColor(Color.parseColor("#000000"));
//            img_loans.setColorFilter(getResources().getColor(R.color.img_color));
        }

        else if (str_flag.equalsIgnoreCase("MyApprovals")) {
            ll_dashboard.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_dashboard.setTextColor(Color.parseColor("#000000"));
            img_Dashboard.setColorFilter(getResources().getColor(R.color.img_color));

            ll_Employee_Directory.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_employee_directory.setTextColor(getResources().getColor(R.color.black));
            img_Employee_Directory.setColorFilter(getResources().getColor(R.color.img_color));

            ll_time_keeping.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_time_keeping.setTextColor(Color.parseColor("#000000"));
            img_time_keeping.setColorFilter(getResources().getColor(R.color.img_color));

            ll_attendance_code.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_attendance_code.setTextColor(Color.parseColor("#000000"));
            img_attendance_code.setColorFilter(getResources().getColor(R.color.img_color));

            ll_Leave_application.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

            ll_early_late_app.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

            ll_Out_on_office_duty.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

            ll_personal_timeout.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

            ll_Work_From_Home_application.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

//            ll_my_approvals.setBackgroundResource(R.drawable.nav_drawer_bg);
//            txt_my_approvals.setTextColor(getResources().getColor(R.color.txt_color));
//            img_my_approvals.setColorFilter(getResources().getColor(R.color.txt_color));

            ll_earnings.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_earnings.setTextColor(Color.parseColor("#000000"));
            img_earnings.setColorFilter(getResources().getColor(R.color.img_color));

//            ll_loans.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
//            txt_loans.setTextColor(Color.parseColor("#000000"));
//            img_loans.setColorFilter(getResources().getColor(R.color.img_color));
        }

        else if (str_flag.equalsIgnoreCase("Earnings")) {
            ll_dashboard.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_dashboard.setTextColor(Color.parseColor("#000000"));
            img_Dashboard.setColorFilter(getResources().getColor(R.color.img_color));

            ll_Employee_Directory.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_employee_directory.setTextColor(getResources().getColor(R.color.black));
            img_Employee_Directory.setColorFilter(getResources().getColor(R.color.img_color));

            ll_time_keeping.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_time_keeping.setTextColor(Color.parseColor("#000000"));
            img_time_keeping.setColorFilter(getResources().getColor(R.color.img_color));

            ll_attendance_code.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_attendance_code.setTextColor(Color.parseColor("#000000"));
            img_attendance_code.setColorFilter(getResources().getColor(R.color.img_color));

            ll_Leave_application.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

            ll_early_late_app.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

            ll_Out_on_office_duty.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

            ll_personal_timeout.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

            ll_Work_From_Home_application.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

//            ll_my_approvals.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
//            txt_my_approvals.setTextColor(Color.parseColor("#000000"));
//            img_my_approvals.setColorFilter(getResources().getColor(R.color.img_color));

            ll_earnings.setBackgroundResource(R.drawable.nav_drawer_bg);
            txt_earnings.setTextColor(getResources().getColor(R.color.txt_color));
            img_earnings.setColorFilter(getResources().getColor(R.color.txt_color));

//            ll_loans.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
//            txt_loans.setTextColor(Color.parseColor("#000000"));
//            img_loans.setColorFilter(getResources().getColor(R.color.img_color));
        }

        else if (str_flag.equalsIgnoreCase("Loans")) {
            ll_dashboard.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_dashboard.setTextColor(Color.parseColor("#000000"));
            img_Dashboard.setColorFilter(getResources().getColor(R.color.img_color));

            ll_Employee_Directory.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_employee_directory.setTextColor(getResources().getColor(R.color.black));
            img_Employee_Directory.setColorFilter(getResources().getColor(R.color.img_color));

            ll_time_keeping.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_time_keeping.setTextColor(Color.parseColor("#000000"));
            img_time_keeping.setColorFilter(getResources().getColor(R.color.img_color));

            ll_attendance_code.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_attendance_code.setTextColor(Color.parseColor("#000000"));
            img_attendance_code.setColorFilter(getResources().getColor(R.color.img_color));

            ll_Leave_application.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

            ll_early_late_app.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

            ll_Out_on_office_duty.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

            ll_personal_timeout.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

            ll_Work_From_Home_application.setBackgroundResource(R.drawable.nav_drawer_bg_plain);

//            ll_my_approvals.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
//            txt_my_approvals.setTextColor(Color.parseColor("#000000"));
//            img_my_approvals.setColorFilter(getResources().getColor(R.color.img_color));

            ll_earnings.setBackgroundResource(R.drawable.nav_drawer_bg_plain);
            txt_earnings.setTextColor(Color.parseColor("#000000"));
            img_earnings.setColorFilter(getResources().getColor(R.color.img_color));

//            ll_loans.setBackgroundResource(R.drawable.nav_drawer_bg);
//            txt_loans.setTextColor(getResources().getColor(R.color.txt_color));
//            img_loans.setColorFilter(getResources().getColor(R.color.txt_color));
        }
    }*/

    private void collapse(LinearLayout mLinearLayout) {
        int finalHeight = mLinearLayout.getHeight();

        ValueAnimator mAnimator = slideAnimator(finalHeight, 0, mLinearLayout);

        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //Height=0, but it set visibility to GONE
                mLinearLayout.setVisibility(View.GONE);
            }
            @Override
            public void onAnimationCancel(Animator animation) {
            }
            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        mAnimator.start();
    }

    private void expand(View mLinearLayout) {
        //set Visible
        mLinearLayout.setVisibility(View.VISIBLE);

        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mLinearLayout.measure(widthSpec, heightSpec);

        ValueAnimator mAnimator = slideAnimator(0, mLinearLayout.getMeasuredHeight(), mLinearLayout);
        mAnimator.start();
    }

    private ValueAnimator slideAnimator(int start, int end,View mLinearLayout) {

        ValueAnimator animator = ValueAnimator.ofInt(start, end);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //Update Height
                int value = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = mLinearLayout.getLayoutParams();
                layoutParams.height = value;
                mLinearLayout.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }

    private void initView() {

        toolbar = findViewById(R.id.toolbar);

        txt_menuTitle = findViewById(R.id.txt_menuTitle);
        img_menuOption = findViewById(R.id.img_menuOption);
        img_menuOption.setBackgroundResource(R.drawable.ic_drawer_menu);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerview = navigationView.getHeaderView(0);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        img_menuOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
                Log.d("flag",""+str_method);
               // set_background_title(mApplication.getStr_nav_view_flag());
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (backPressedToExitOnce) {
                moveTaskToBack(false);
                if(sv.isSearchOpen()) sv.closeSearch();
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startMain);
            } else {
                if(sv.isSearchOpen()) sv.closeSearch();
                this.backPressedToExitOnce = true;
                Toast.makeText(Base_Drawer.this, "Press again to exit", Toast.LENGTH_LONG).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        backPressedToExitOnce = false;
                    }
                }, 3000);
            }
        }
    }





    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}