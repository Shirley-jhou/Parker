package com.example.yvime.e_parker;


import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class googlemap_motor extends Fragment {

    public TextView carid,gate1,carlo,carroad;

    public googlemap_motor() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_googlemap_motor, container, false);
        carid = (TextView) view.findViewById(R.id.carid);
        gate1 = (TextView) view.findViewById(R.id.gate);
        carlo=(TextView) view.findViewById(R.id.carlo);
        carroad=(TextView) view.findViewById(R.id.carroad);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());

        String result=DBConnector.executeQuery("SELECT car_ID  FROM user");


    String car = result.replace('"',',');
        String car_id = car.split(",")[3];

        carid.setText(car_id);
        String HI=DBConnector.executeQuery("SELECT gate FROM parking WHERE car_ID='J6U825' and update_time in (SELECT MAX(update_time) FROM parking GROUP BY car_ID)");
    String HI1 = HI.replace('"',',');
     String HI2 = HI1.split(",")[3];
       if(HI2.equals("0")){
       gate1.setText("已離駛");
            carlo.setText("");
            carroad.setText("");
        }
       else
        {

         gate1.setText("停車中");
         carlo.setText("AB01");
         carroad.setText("成功路");}





        return view;}
    }
