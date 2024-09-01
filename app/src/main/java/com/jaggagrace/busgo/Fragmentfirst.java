package com.jaggagrace.busgo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.jaggagrace.busgo.ui.notifications.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragmentfirst extends Fragment{
    View view;
    EditText endingLoc;
    EditText startingLoc;
    Button goButton;
    private String [] startLocs = new String[2];

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //when view is created, it will inflate the fragment A fragment
        startLocs[0] = "";
        startLocs[1] = "";
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view; //has a variable called view, which is just a reference to the view
        startingLoc = view.findViewById(R.id.startlocation);
        endingLoc = view.findViewById(R.id.endlocation);
        goButton = view.findViewById(R.id.gobutton);
        //goPage();
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                goPage();
            }}
        );
    }

    public void goPage(){
        //how to get input of startLoc and endingLoc
        String s = startingLoc.getText().toString();
        String[] start = s.split("\\s+");
        int startWords=start.length;
        String e = endingLoc.getText().toString();
        String[] end = e.split("\\s+");
        int endWords=end.length;

        String startSTRING="";
        for(int i=0;i<startWords;i++) {
            String curr = start[i];
            if(i==startWords-1) {
                startSTRING += curr;
            }
            else{ //not last part
                if(curr.substring(0,1).equals("#")){
                    startSTRING += "%23";
                    curr = curr.substring(1,curr.length()); //get rid of first element
                }
                if(curr.substring(curr.length() - 1).equals(",")) {//last is comma
                    startSTRING += curr.substring(0,curr.length()-1); //all but comma
                    startSTRING += "%2C%20";
                }
                else{
                    startSTRING += curr;
                    startSTRING += "%20";
                }
            }
        }
        String endSTRING="";
        for(int i=0;i<endWords;i++) {
            String curr = end[i];
            if(i==endWords-1) {
                endSTRING += curr;
            }
            else{ //not last part
                if(curr.substring(0,1).equals("#")){
                    endSTRING += "%23";
                    curr = curr.substring(1,curr.length()); //get rid of first element
                }
                if(curr.substring(curr.length() - 1).equals(",")) {//last is comma
                    endSTRING += curr.substring(0,curr.length()-1); //all but comma
                    endSTRING += "%2C%20";
                }
                else{
                    endSTRING += curr;
                    endSTRING += "%20";
                }
            }
        }

        String fromPlace="https://api.opencagedata.com/geocode/v1/json?q="+startSTRING+
                "&key=03c48dae07364cabb7f121d8c1519492&no_annotations=1&language=en";
        String toPlace="https://api.opencagedata.com/geocode/v1/json?q="+endSTRING+
                "&key=03c48dae07364cabb7f121d8c1519492&no_annotations=1&language=en";

        getLocation(fromPlace, new VolleyCallback() {
            @Override
            public void onSuccess(String[] start) {
                startLocs[0] = start[0];
                startLocs[1] = start[1];
                getLocation(toPlace, new VolleyCallback() { //do calls one after each other
                    @Override
                    public void onSuccess(String[] end) {
                        putPlace(view, end, true);
                    }
                    @Override
                    public void onError(String error) {
                        Log.e("my error is (second part): ", error);
                    }
                });
            }
            @Override
            public void onError(String error) {
                Log.e("my error is (first part): ", error);
            }
        });
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
//                (Request.Method.GET, fromPlace, null, new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            JSONArray val = response.getJSONArray("results");
//                            JSONObject v = val.getJSONObject(0);
//                            JSONObject vv = v.getJSONObject("geometry");
//                            start[0] = vv.getString("lat");
//                            start[1] = vv.getString("lng");
//                            Log.d("tag", "sent request for FROMplace");
//                            //putPlace(view, start, false);
//                            firstPlace(view, start);
//                            Log.d("tag", "finished FROMplace request");
//                        } catch (JSONException e) {
//                            Log.e("went wrong?", String.valueOf(e));
//                            e.printStackTrace();
//                        }}} , new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.e("my error is (first part): ", String.valueOf(error));}});
//
//        jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
//            @Override
//            public int getCurrentTimeout() {return 50000;}
//            @Override
//            public int getCurrentRetryCount() {return 50000;}
//            @Override
//            public void retry(VolleyError error) throws VolleyError {}
//        });
//        MySingleton.getInstance(this.getContext()).addToRequestQueue(jsonObjectRequest);
//
//        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest
//                (Request.Method.GET, toPlace, null, new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            JSONArray val = response.getJSONArray("results");
//                            JSONObject v = val.getJSONObject(0);
//                            JSONObject vv = v.getJSONObject("geometry");
//                            end[0] = vv.getString("lat");
//                            end[1] = vv.getString("lng");
//                            Log.d("tag", "sent request for TOplace");
//                            putPlace(view, end, true);
//                            Log.d("tag", "finished TOplace request");
//                        } catch (JSONException e) {
//                            Log.e("went wrong?", String.valueOf(e));
//                            e.printStackTrace();
//                        }}} , new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.e("my error is (first part): ", String.valueOf(error));}});
//
//
//        jsonObjectRequest1.setRetryPolicy(new RetryPolicy() {
//            @Override
//            public int getCurrentTimeout() {return 50000;}
//            @Override
//            public int getCurrentRetryCount() {return 50000;}
//            @Override
//            public void retry(VolleyError error) throws VolleyError {}
//        });
//        MySingleton.getInstance(this.getContext()).addToRequestQueue(jsonObjectRequest1);
    }

    //go to fragment and only have 2 pages
//    private void putPlace(View view, String[] s, boolean end){
//        //if(end) {
//            Log.d("tag", "entered function for the end");
//            String [] Locs= new String[4];
//            Locs[0]=startLocs[0];Locs[1]=startLocs[1];
//            Locs[2]=s[0]; Locs[3]=s[1];
//            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.fragment_container, FirstNotif.newInstance(Locs), "FirstNotif");
//            ft.commit();
//    }


    private void getLocation(String url, VolleyCallback callback) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray val = response.getJSONArray("results");
                            JSONObject v = val.getJSONObject(0);
                            JSONObject vv = v.getJSONObject("geometry");
                            JSONObject da = response.getJSONObject("timestamp");
                            String date = da.getString("created_http");
                            String[] location = new String[3];
                            location[0] = vv.getString("lat");
                            location[1] = vv.getString("lng");
                            location[2] = date;
                            callback.onSuccess(location);
                        } catch (JSONException e) {
                            callback.onError(e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error.toString());
                    }
                });
        jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {return 50000;}
            @Override
            public int getCurrentRetryCount() {return 50000;}
            @Override
            public void retry(VolleyError error) throws VolleyError {}
        });
        MySingleton.getInstance(this.getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void putPlace(View view, String[] s, boolean end) {
        Log.d("tag", "entered function for the end");
        String[] Locs = new String[5];
        Locs[0] = startLocs[0];
        Locs[1] = startLocs[1];
        Locs[2] = s[0];
        Locs[3] = s[1];
        Locs[4] = s[2];
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, FirstNotif.newInstance(Locs), "FirstNotif");
        ft.commit();
    }
    private interface VolleyCallback {
        void onSuccess(String[] result);
        void onError(String error);
    }


//        }
//        else{
//            Log.d("tag", "entered function for the start");
//            String s1 = s[0];
//            String s2 = s[1];
//            this.startLocs[0] = s1;
//            this.startLocs[1] = s2;
//        }
//    private void newPage(View view, String str, String endingLoc) {
//        FragmentTransaction ft2 = getActivity().getSupportFragmentManager().beginTransaction();
//        ft2.replace(R.id.fragment_container, FragmentA.newInstance(str, endingLoc), "FragmentA");
//        ft2.commit();
//    }

}
