package com.zenwsmp.pmwani;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.zenwsmp.pmwani.adapter.Language_Adapter;
import com.zenwsmp.pmwani.model.LanguageBean;

import java.util.ArrayList;
import java.util.List;

public class LanguageSelection_Activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    List<LanguageBean> recyclerDataArrayList =new ArrayList<>();
    MaterialButton btn_next;
    int mposition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_selection);

        recyclerView=findViewById(R.id.idCourseRV);
        btn_next=findViewById(R.id.btn_next);

        // created new array list..
        recyclerDataArrayList=new ArrayList<>();
        recyclerDataArrayList.clear();
        // added data to array list
        recyclerDataArrayList.add(new LanguageBean("English","en"));
        recyclerDataArrayList.add(new LanguageBean("Hindi","hi"));
        recyclerDataArrayList.add(new LanguageBean("Gujarati","gu"));
        recyclerDataArrayList.add(new LanguageBean("Marathi","mr"));
        recyclerDataArrayList.add(new LanguageBean("Bengali","bn"));
        recyclerDataArrayList.add(new LanguageBean("Telugu","te"));
        recyclerDataArrayList.add(new LanguageBean("Punjabi","pa"));
        recyclerDataArrayList.add(new LanguageBean("Urdu","ur"));
        //recyclerDataArrayList.add(new LanguageBean("Assamese","as"));
        recyclerDataArrayList.add(new LanguageBean("Kannada","kn"));
        recyclerDataArrayList.add(new LanguageBean("Tamil","ta"));

        // added data from arraylist to adapter class.
        Language_Adapter adapter=new Language_Adapter(recyclerDataArrayList,this);

        // setting grid layout manager to implement grid view.
        // in this method '2' represents number of columns to be displayed in grid view.
        GridLayoutManager layoutManager=new GridLayoutManager(this,3);

        // at last set adapter to recycler view.
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, 50, false));
        recyclerView.setAdapter(adapter);

        adapter.setOnTimeSelectedListener((position, time) -> {
            mposition = position;
            Log.d("test","click");
            Context context = LocaleHelper.setLocale(this, recyclerDataArrayList.get(position).getLanguagecode());

        });
       /* adapter.setClickListener((view, groupclass, postion) -> {

            if(view.getId() == R.id.itemview){
                mposition = postion;
                Log.d("test","click");
                Context context = LocaleHelper.setLocale(this, recyclerDataArrayList.get(postion).getLanguagecode());
                adapter.notifyDataSetChanged();
            }
        });*/

        btn_next.setOnClickListener(v -> {

            if(mposition>=0){
                LocaleHelper.setLocale(this, recyclerDataArrayList.get(mposition).getLanguagecode());
                Intent i = new Intent(LanguageSelection_Activity.this,Startup_Activity.class);
                startActivity(i);
            }else{
                Intent i = new Intent(LanguageSelection_Activity.this,Startup_Activity.class);
                startActivity(i);
            }
        });

    }
}