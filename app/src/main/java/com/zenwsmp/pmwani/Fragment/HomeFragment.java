package com.zenwsmp.pmwani.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zenwsmp.pmwani.Account_Activity;
import com.zenwsmp.pmwani.R;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {


    CircleImageView profile_image;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
       // args.putString("Cloud",Cloud);
        /*args.putString(PARAM_UPDATE_FLAG, data);
        args.putString(PARAM_UPDATE_SUCCESS, Success);
        args.putString(PARAM_UPDATE_FAIL, Fail);
        args.putInt(PARAM_UPDATE_uq_SUCCESS, uq_success);
        args.putInt(PARAM_UPDATE_uq_FAIL, uq_fail);
        args.putString(PARAM_UPDATE_LIST, strmain);*/
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profile_image =view.findViewById(R.id.profile_image);
        profile_image.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), Account_Activity.class);
            startActivity(i);
        });
    }
}