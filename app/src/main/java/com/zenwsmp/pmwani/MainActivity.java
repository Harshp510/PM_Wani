package com.zenwsmp.pmwani;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.zenwsmp.pmwani.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

  //  @BindView(R.id.titleTextView)
    TextView mTitleTextView;
  //  @BindView(R.id.descTextView)
    TextView mDescTextView;
  //  @BindView(R.id.aboutTextView)
    TextView mAboutTextView;
   // @BindView(R.id.toTRButton)
    Button mToTRButton;
   // @BindView(R.id.toENButton)
    Button mToENButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitleTextView =findViewById(R.id.titleTextView);
        mDescTextView =findViewById(R.id.descTextView);
        mAboutTextView =findViewById(R.id.aboutTextView);
        mToTRButton =findViewById(R.id.toTRButton);
        mToENButton =findViewById(R.id.toENButton);

        setTitle(getString(R.string.main_activity_toolbar_title));

        mToTRButton.setOnClickListener(v -> {
            updateViews("hi");
        });
        mToENButton.setOnClickListener(v -> {
             updateViews("en");
        });

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }



    private void updateViews(String languageCode) {
        Context context = LocaleHelper.setLocale(this, languageCode);
        Resources resources = context.getResources();

        mTitleTextView.setText(resources.getString(R.string.english));
        mDescTextView.setText(resources.getString(R.string.english));
        mAboutTextView.setText(resources.getString(R.string.hello));
        mToTRButton.setText(resources.getString(R.string.english));
        mToENButton.setText(resources.getString(R.string.english));

        setTitle(resources.getString(R.string.main_activity_toolbar_title));
    }


}