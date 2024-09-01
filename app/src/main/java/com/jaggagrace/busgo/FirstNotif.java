package com.jaggagrace.busgo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
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

public class FirstNotif extends Fragment {
    View view;
    String hello;
    static String[] ll = new String[4]; //4 lat and lons
    String testing1str;
    String testing2str;
    String testing3str;
    String introStr;
    Button b1Press;
    boolean optionPressed=false;


    public static FirstNotif newInstance(String []s){
        FirstNotif firstNotif = new FirstNotif(); //calls constructor
        ll=s;
//        Bundle args = new Bundle();
//        args.putString("loc", loc);
//        args.putString("ending", endingloc);
//        fragmentA.setArguments(args);
        return firstNotif ;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //when view is created, it will inflate the fragment A fragment
        return inflater.inflate(R.layout.firstnotif, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view; //has a variable called view, which is just a reference to the view

        final TextView textView = view.findViewById(R.id.testing);
        TextView intro = view.findViewById(R.id.intro);
        Button b1 = view.findViewById(R.id.button1);
        b1Press=b1;
        String[] bD = new String[3];
        String[] bD2 = new String[3];
        JSONObject[] o = new JSONObject[1];
        JSONObject[] o2 = new JSONObject[1];
        JSONObject[] o3 = new JSONObject[1];
        JSONObject[][] arrayOfArrays = new JSONObject[3][];
// Assign the individual arrays to the two-dimensional array
        arrayOfArrays[0] = o;
        arrayOfArrays[1] = o2;
        arrayOfArrays[2] = o3;
        Button b2 = view.findViewById(R.id.button2);
        Button b3 = view.findViewById(R.id.button3);
        String val = ll[4];
       // String str = "Hello I'm your String";
        String[] split = val.split(" ");
        String day=split[1];
        String month=split[2]; //word form
        String year=split[3];
        String time=split[4];
        String[] months={"January", "February", "March", "April", "May", "June", "July"
        ,"August", "September", "October", "November", "December"};
        for(int i=0;i<months.length;i++){
            String s = months[i];
            if(s.substring(0,3).equals(month.substring(0,3))){
                if(i+1<10){
                    String r = String.valueOf(i+1);
                    month = "0"+r;
                }
                else {
                    month = String.valueOf(i + 1);
                }
                break;
            }
        }
        String[] times = time.split(":");
        String am = "am";
        int og = Integer.parseInt(times[0]);
        if(og>12){
            og -=16;
            am = "pm";
        }
        String hour = String.valueOf(og);
        String min = times[1];
        String timeDate = "&time="+hour+"%3A"+min+am+"&date="+month+"-"+day+"-"+year+"&";
        String timeDateOPTION="&time=8%3A30pm&date=03-11-2024&"; //6:35
        //SCHOOL: check for room specific url
        String IPvaluehome="10.0.0.10";
        String IPvalueschool="10.16.184.93"; //change this value
        //10.16.184.93
        //DIFFERENT BASED ON WHERE THE LOCATION IS - AT HOME OR AT SCHOOL
        String schoolurl="http://"+IPvalueschool+":8080/otp/routers/default/plan?fromPlace="+ll[0]+"%2C"+ll[1]+
                "&toPlace="+ll[2]+"%2C"+ll[3]+timeDate+"mode=TRANSIT%2CWALK&arriveBy=false&w" +
                "heelchair=false&showIntermediateStops=true&locale=en\n";
        String url="http://"+IPvalueschool+":8080/otp/routers/default/plan?fromPlace="+ll[0]+"%2C"+ll[1]+
                "&toPlace="+ll[2]+"%2C"+ll[3]+timeDateOPTION+"mode=TRANSIT%2CWALK&arriveBy=false&w" +
                "heelchair=false&showIntermediateStops=true&locale=en\n";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject val = response.getJSONObject("plan");
                            JSONArray val2 = val.getJSONArray("itineraries");
                            String from3lon = val.getJSONObject("from").getString("lon");
                            String from3lat = val.getJSONObject("from").getString("lat");
                            String to3lon = val.getJSONObject("to").getString("lon");
                            String to3lat = val.getJSONObject("to").getString("lat");
                            //save itinerary values in different JSONObjects
                            //check how many itinerary options there are - first of all...why are there only one option
                            int gg=3;
                            if(val2.length()<3){
                                gg=val2.length();
                            }
                            for(int i=0;i<gg;i++){ //only 3 being shown, could still be less
                                //can create more later
                                arrayOfArrays[i][0]=val2.getJSONObject(i);
                            }
                            String b1Dur=val2.getJSONObject(0).getString("duration");
                            String b1Cost=val2.getJSONObject(0).getString("generalizedCost");
                            bD[0] =b1Dur; bD[1]=b1Cost; //bD[2]=url;

                            introStr = "ROUTE PLANNING FOR: \n\n" +"From:  ("+from3lon+",  "+from3lat+")\n"+
                                    "To:  ("+to3lon+",  "+to3lat+")\n";
                            hello = "There are " + String.valueOf(val2.length())+" different options for commute:";
                                     //options based on preferences - walking, specific areas
                            //itinerary 1
                            testing1str = "ITINERARY OF ROUTE 1: \n"
                                    + "duration: " + val2.getJSONObject(0).getString("duration")
                                    + "\ncost: $" + val2.getJSONObject(0).getString("generalizedCost");
                            //itinerary 2
                            testing2str = "ITINERARY OF ROUTE 2: \n"
                                    + "duration: " + val2.getJSONObject(1).getString("duration")
                                    + "\ncost: $" + val2.getJSONObject(1).getString("generalizedCost");
                            //itinerary 3
                            testing3str = "ITINERARY OF ROUTE 3: \n"
                                    + "duration: " + val2.getJSONObject(2).getString("duration")
                                    + "\ncost: $" + val2.getJSONObject(2).getString("generalizedCost");
                            //each itinerary is a button that opens a new fragment
                            //one fragment will always open, just depends what info you send it based on which option they choose
                            //in the fragment OR POP UP, it will display from where to where
                                //all the stops + time info - easy to get
                            //a step where you can actually choose a path AFTER being able to see all of them in popups
                            //finalFragment?
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        textView.setText(hello);
                        intro.setText(introStr);
                        b1.setText(testing1str);
                        b2.setText(testing2str);
                        b3.setText(testing3str);

                        b1Press.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                option1(view, o);
                            }
                        });
                        b2.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                option1(view, o2); //send o2 not o
                            }
                        });
                        b3.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                option1(view, o3); //send o2 not o
                            }
                        });
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("my error is: ", String.valueOf(error));
                        // TODO: Handle error
                        textView.setText("didn't work");
                    }
                });
        MySingleton.getInstance(this.getContext()).addToRequestQueue(jsonObjectRequest);

        jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {return 50000;}
            @Override
            public int getCurrentRetryCount() {return 50000;}
            @Override
            public void retry(VolleyError error) throws VolleyError {}
        });

    }

    public String getPlace1(int i){return this.ll[i];}

    private void option1(View view, JSONObject[]b) {
        if(!optionPressed) {
            FragmentTransaction ft2 = getActivity().getSupportFragmentManager().beginTransaction();
            ft2.replace(R.id.fragment_container, PlanOption.newInstance(b[0]), "PlanOption");
            ft2.commit();
        }
        optionPressed=true;
    }
}
