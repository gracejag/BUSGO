package com.jaggagrace.busgo.ui.notifications;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.jaggagrace.busgo.FirstNotif;
import com.jaggagrace.busgo.FragmentA;
import com.jaggagrace.busgo.Fragmentfirst;
import com.jaggagrace.busgo.R;
import com.jaggagrace.busgo.databinding.FragmentNotificationsBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    String hello;
    String testing1str;
    String testing2str;
    String testing3str;
    String introStr;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        FragmentTransaction ft = getParentFragmentManager()  .beginTransaction();
        ft.add(R.id.fragment_container1, new FirstNotif(), "FirstNotif");
        ft.setReorderingAllowed(true);
        ft.commit();
//        final TextView textView = binding.textNotifications;
//        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        return root;
    }
//
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);

//        final TextView textView = view.findViewById(R.id.testing);
//        TextView intro = view.findViewById(R.id.intro);
//        Button b1 = view.findViewById(R.id.button1);
//        Button b2 = view.findViewById(R.id.button2);
//        Button b3 = view.findViewById(R.id.button3);
//
//        String url = "http://10.16.188.111:8080/otp/routers/default/plan?fromPlace=38.90372475251106%2C-77.04145431518556&toPlace=38.90051858534433%2C-77.03046798706056&time=11%3A24am&date=01-22-2024&mode=TRANSIT%2CWALK&arriveBy=false&wheelchair=false&showIntermediateStops=true&locale=en";
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
//                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            JSONObject val = response.getJSONObject("plan");
//                            JSONArray val2 = val.getJSONArray("itineraries");
//                            String from3lon = val.getJSONObject("from").getString("lon");
//                            String from3lat = val.getJSONObject("from").getString("lat");
//                            String to3lon = val.getJSONObject("to").getString("lon");
//                            String to3lat = val.getJSONObject("to").getString("lat");
//                            introStr = "ROUTE PLANNING FOR: \n\n" +"From:  ("+from3lon+",  "+from3lat+")\n"+
//                                    "To:  ("+to3lon+",  "+to3lat+")\n";
//                            hello = "There are " + String.valueOf(val2.length())+" different options for commute:";
//                                     //options based on preferences - walking, specific areas
//                            //itinerary 1
//                            testing1str = "ITINERARY OF ROUTE 1: \n"
//                                    + "duration: " + val2.getJSONObject(0).getString("duration")
//                                    + "\ncost: $" + val2.getJSONObject(0).getString("generalizedCost");
//                            //itinerary 2
//                            testing2str = "ITINERARY OF ROUTE 2: \n"
//                                    + "duration: " + val2.getJSONObject(1).getString("duration")
//                                    + "\ncost: $" + val2.getJSONObject(1).getString("generalizedCost");
//                            //itinerary 3
//                            testing3str = "ITINERARY OF ROUTE 3: \n"
//                                    + "duration: " + val2.getJSONObject(2).getString("duration")
//                                    + "\ncost: $" + val2.getJSONObject(2).getString("generalizedCost");
//                            //each itinerary is a button that opens a new fragment
//                            //one fragment will always open, just depends what info you send it based on which option they choose
//                            //in the fragment OR POP UP, it will display from where to where
//                                //all the stops + time info - easy to get
//                            //a step where you can actually choose a path AFTER being able to see all of them in popups
//                            //finalFragment?
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        textView.setText(hello);
//                        intro.setText(introStr);
//                        b1.setText(testing1str);
//                        b2.setText(testing2str);
//                        b3.setText(testing3str);
//
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.e("my error is: ", String.valueOf(error));
//                        // TODO: Handle error
//                        textView.setText("didn't work");
//                    }
//                });
//        MySingleton.getInstance(this.getContext()).addToRequestQueue(jsonObjectRequest);
//
//        jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
//            @Override
//            public int getCurrentTimeout() {
//                return 50000;
//            }
//
//            @Override
//            public int getCurrentRetryCount() {
//                return 50000;
//            }
//
//            @Override
//            public void retry(VolleyError error) throws VolleyError {
//            }
//        });

   // }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    //have to have an onclick button method that opens PlanFragment
//    private void option1(View view, String str, String endingLoc) {
//        FragmentTransaction ft2 = getActivity().getSupportFragmentManager().beginTransaction();
//        ft2.replace(R.id.fragment_container, FragmentA.newInstance(str, endingLoc), "FragmentA");
//        //oh notifications itself has to be a whole fragment container now...ugh
//        //have to do same with dash
//        ft2.commit();
//    }

}