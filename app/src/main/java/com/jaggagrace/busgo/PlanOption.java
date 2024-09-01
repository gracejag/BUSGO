package com.jaggagrace.busgo;



import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class PlanOption extends Fragment {
    View view;
    static JSONObject plan;

    public static PlanOption newInstance(JSONObject a){
        PlanOption planFragment = new PlanOption(); //calls constructor
        Bundle args = new Bundle();
//        args.putString("dur", dur);
//        args.putString("cost", cost);
//        args.putString("link", url);
        plan=a;
        planFragment.setArguments(args);
        return planFragment ;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //when view is created, it will inflate the fragment A fragment
        return inflater.inflate(R.layout.fragment_planoption, container, false);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view; //has a variable called view, which is just a reference to the view
        TextView firstItem = view.findViewById(R.id.step1);
        ListView firstSteps = view.findViewById(R.id.step1list);
        ArrayList<String> arrayListOne = new ArrayList<String>();
        ArrayAdapter<String> adapterOne;

        TextView secondItem = view.findViewById(R.id.step2);
        ListView secondSteps = view.findViewById(R.id.step2list);
        ArrayList<String> arrayListTwo = new ArrayList<String>();
        ArrayAdapter<String> adapterTwo;

        TextView thirdItem = view.findViewById(R.id.step3);
        ListView thirdSteps = view.findViewById(R.id.step3list);
        ArrayList<String> arrayListThree = new ArrayList<String>();
        ArrayAdapter<String> adapterThree;


        TextView initial = view.findViewById(R.id.intro);
        //second steps are gonna now be listviews not textviews

        //how many possible items could there be? 3 always - 4 to be safe? or way more if farther!

        try {
            JSONArray r=plan.getJSONArray("legs");
            int times = r.length();
            initial.setText("start and stop times add later");
            Long fullStart = plan.getLong("startTime");
            long fd = Long.valueOf(fullStart);// its need to be in milisecond
            Date fd1 = new Date(fd);
            String fd2 = new SimpleDateFormat("hh:mma").format(fd1);
            Long fullEnd = plan.getLong("endTime");
            long fd3 = Long.valueOf(fullEnd);// its need to be in milisecond
            Date fd4 = new Date(fd3);
            String fd5 = new SimpleDateFormat("hh:mma").format(fd4);
            String intro = "Begin travel at: " + fd2 + " and arrive at "+ fd5;
            initial.setText(intro);

            for(int i=0; i<times;i++){ //for each of the steps
                JSONObject step = r.getJSONObject(i);
                String mode = step.getString("mode").toLowerCase(Locale.ROOT);
                if(mode.equals("bus")){
                    //have to do differently than walking mode
                    String agency = step.getString("agencyName"); //should always be DC Circulator
                    String busName = step.getString("routeLongName");
                    //cost and timings
                    String board = step.getJSONObject("from").getString("name"); //board at
                    String dismount = step.getJSONObject("to").getString("name"); //dismount on
                    int intermediate = step.getJSONArray("intermediateStops").length();
                    int transitTime = (int)(step.getDouble("duration")/60);
                    Long arrival = step.getLong("startTime");
                    long dv = Long.valueOf(arrival);// its need to be in milisecond
                    Date df = new Date(dv);
                    String ar = new SimpleDateFormat("hh:mma").format(df);
                    Long departure = step.getLong("endTime");
                    long dv1 = Long.valueOf(departure);// its need to be in milisecond
                    Date df1 = new Date(dv1);
                    String de = new SimpleDateFormat("hh:mma").format(df1);


                    //steps SHOULD be 0
                    //for bus, NEED from and to
                    String busLine = agency + ", "+busName+"\nTotal transit time: "
                            +transitTime+ " minutes";
                    if(i==0){firstItem.setText(busLine);}
                    else if(i==1){secondItem.setText(busLine);}
                    else if(i==2){thirdItem.setText(busLine);}
                    String busSteps ="Board at "+board+" at "+ ar+ ".\n\nThere are "+
                            intermediate+" intermediate stops.\n\n" +
                            "Depart at "+dismount+" at "+ de;

                    if(i==0) {
                        arrayListOne.add("Board at " + board + " at " + ar + ".");
                        arrayListOne.add("There are "+intermediate+" intermediate stops.");
                        arrayListOne.add("Depart at "+dismount+" at "+ de);
                        adapterOne = new ArrayAdapter<String>(getActivity(), R.layout.list,R.id.item_text, arrayListOne); //arrayListOne
                        firstSteps.setAdapter(adapterOne);
                        //arrayListOne.setAdapter(adapterOne);
                        // firstSteps.setText(busSteps);}
                    }
                    else if(i==1){
                        arrayListTwo.add("Board at " + board + " at " + ar + ".");
                        arrayListTwo.add("There are "+intermediate+" intermediate stops.");
                        arrayListTwo.add("Depart at "+dismount+" at "+ de);
                        adapterTwo = new ArrayAdapter<String>(getActivity(), R.layout.list,R.id.item_text, arrayListTwo);
                        secondSteps.setAdapter(adapterTwo);
                    }
                    else if(i==2){
                        arrayListThree.add("Board at " + board + " at " + ar + ".");
                        arrayListThree.add("There are "+intermediate+" intermediate stops.");
                        arrayListThree.add("Depart at "+dismount+" at "+ de);
                        adapterThree = new ArrayAdapter<String>(getActivity(), R.layout.list,R.id.item_text, arrayListThree);
                        thirdSteps.setAdapter(adapterThree);
                    }

                }
                else{ //walking
                    Double in1 = step.getDouble("duration")/60;
                        //For minutes they don't round up they round down always. YES
                    String timeForMode = String.valueOf((int)(in1.doubleValue()));
                    Double d = Double.parseDouble(step.getString("distance"))/1609;
                    String distVar=" miles ";
                    String timeVar =" minutes";
                    if(d<0.1){
                        distVar=" feet ";d=d*5280;}
                    if(timeForMode.equals("1")){timeVar=" minute";}
                    //if distance is <1, keep decimal of miles and miles stays plural
                    String distance;
                    if(d<1){
                        double rr= d*100; //oh we know for sure its only 0.
                        int rrr = (int)(rr+0.5); //48
                        distance = "0."+rrr;
                    }
                    else{distance = String.valueOf( (int) (d+0.5));}
                    if(distance.equals("1")){
                        if(distVar.equals("feet")){distVar="foot";}
                        else{distVar=" mile";}
                    }
                    String from = step.getJSONObject("from").getString("name");
                    String to = step.getJSONObject("to").getString("name");
                    if(from.equals("Origin")){from="here";}
                    String line = mode +" "+distance+ distVar +" for "+timeForMode+ timeVar+" to "+to+".";
                    if(i==0){firstItem.setText(line);}
                    else if(i==1){secondItem.setText(line);}
                    else if(i==2){thirdItem.setText(line);}
                    //different if statements based

                    //now to show the steps WITHIN STEP
                    JSONArray steps = step.getJSONArray("steps");
                    int amountofSteps = steps.length();
                    String buildStep = ""; //long string that holds direction
                        //later can do it in a list format --> listView in xml file!
                    ArrayList<String> stepList = new ArrayList<String>();
                    for(int j=0;j<amountofSteps;j++){
                        String fs = steps.getJSONObject(j).getString("relativeDirection").toLowerCase(Locale.ROOT); //depart
                        String fsON = steps.getJSONObject(j).getString("streetName"); //on
                        String fsdir = steps.getJSONObject(j).getString("absoluteDirection"); //west
                        Double fsDis = steps.getJSONObject(j).getDouble("distance")*3.281; //feet miles?
                        String fsDi = String.valueOf((int)(fsDis+0.5));
                        String fs1Var = " feet";
                        //if its 520 ft or more, convert to miles
                        if(Integer.parseInt(fsDi)>528){ //feet not mile
                            fs1Var = " miles ";
                            fsDis = fsDis/5280;
                            int fsr = (int)((fsDis*100)+0.5);
                            fsDi = "0."+fsr;
                            //within a mile, so keep the decimal
                            //fsDi = String.valueOf((int)(fsDis+0.5));
                        }
                        String stepLine = fs+ " on "+fsON+" "+fsdir+" for "+fsDi + fs1Var;
                        stepList.add(stepLine);
//                        if(j==0){buildStep+=stepLine;}
//                        else{
                       // buildStep += stepLine;
                       // if(i==0){arrayListOne.add(stepLine);
                       // }
                    }
                    if(i==0){
                        for(int h=0;h<stepList.size();h++){
                            arrayListOne.add(stepList.get(h));
                        }
                        adapterOne = new ArrayAdapter<String>(getActivity(), R.layout.list,R.id.item_text, arrayListOne);
                        firstSteps.setAdapter(adapterOne);
                    } //do an arraylist containing all the textviews, correspond to i value
                    else if(i==1){
                        for(int h=0;h<stepList.size();h++){
                            arrayListTwo.add(stepList.get(h));
                        }
                        adapterTwo = new ArrayAdapter<String>(getActivity(), R.layout.list,R.id.item_text, arrayListTwo);
                        secondSteps.setAdapter(adapterTwo);
                    }
                    else if(i==2){
                        for(int h=0;h<stepList.size();h++){
                            arrayListThree.add(stepList.get(h));
                        }
                        adapterThree = new ArrayAdapter<String>(getActivity(), R.layout.list,R.id.item_text, arrayListThree);
                        thirdSteps.setAdapter(adapterThree);
                    }

                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
