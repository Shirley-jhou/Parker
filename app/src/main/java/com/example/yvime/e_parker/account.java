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
public class account extends Fragment {
TextView first_name,last_name,emai,addr;

    public account() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_account, container, false);
        first_name=view.findViewById(R.id.firstname);
        last_name=view.findViewById(R.id.lastname);
        emai=view.findViewById(R.id.email);
        addr=view.findViewById(R.id.address);
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
        String fn=DBConnector.executeQuery("SELECT first_name FROM user   ");
        String f = fn.replace('"',',');
        String f_n = f.split(",")[3];

        String ln=DBConnector.executeQuery("SELECT last_name FROM user   ");
        String l = ln.replace('"',',');
        String l_n = l.split(",")[3];

        String em=DBConnector.executeQuery("SELECT email FROM user   ");
        String e = em.replace('"',',');
        String email = e.split(",")[3];

        String ad=DBConnector.executeQuery("SELECT address FROM user   ");
        String a = ad.replace('"',',');
        String a_d = a.split(",")[3];

        first_name.setText(f_n);
        last_name.setText(l_n);
        emai.setText(email);
        addr.setText(a_d);
    return view;
    }

}
