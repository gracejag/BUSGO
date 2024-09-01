package com.jaggagrace.busgo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jaggagrace.busgo.ui.notifications.MySingleton;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.w3c.dom.Text;

public class FragmentA extends Fragment {
    View view;
    public static FragmentA newInstance(String loc, String endingloc){
        FragmentA fragmentA = new FragmentA(); //calls constructor
        Bundle args = new Bundle();
        args.putString("loc", loc);
        args.putString("ending", endingloc);
        fragmentA.setArguments(args);
        return fragmentA ;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_a, container, false);
        //when view is created, it will inflate the fragment A fragment
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        TextView inputDest = view.findViewById(R.id.destinationChange);
        TextView endingDest = view.findViewById(R.id.currentLoc);
        String name = getArguments().getString("loc", "blah");
        String name2 = getArguments().getString("ending", "blah");
        inputDest.setText(name);
        endingDest.setText(name2);
        //name and name2 are the strings - convert to lon and lat
        String firstW="lincoln";
        String secondW="memorial";
        String[] latlon = new String[2];
        String getPlace="https://api.opencagedata.com/geocode/v1/json?q="+firstW +"%20"+secondW+"&key=03c48dae07364cabb7f121d8c1519492&no_annotations=1&language=en";

        //don't even need searchview, just enter values --> or go to notifications tab from there?


    }









}
