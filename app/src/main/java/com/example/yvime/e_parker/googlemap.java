package com.example.yvime.e_parker;


import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


/**
 * A simple {@link Fragment} subclass.
 */
public class googlemap extends Fragment implements OnMapReadyCallback, LocationListener {
String text="";
    private Handler handler = new Handler( );
    private Runnable runnable;

    public MapView mMapView;
    public GoogleMap map;
TextView t3;

    AppCompatImageButton search, layer, motor, loca;

    public FragmentManager manager;
    public FragmentTransaction transaction;
    public String aaa = "";
Data data=new Data();
    double lat=Double.parseDouble(data.lat);
    double lng=Double.parseDouble(data.lng);
    public String h2,h3;
    public int c =0,d=0;
    public boolean a ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_googlemap, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // needed to get the map to display immediately
        mMapView.setClickable(true);

        manager = getFragmentManager();
        final FragmentTransaction[] transaction = {manager.beginTransaction()};
        search = rootView.findViewById(R.id.search);
        layer = rootView.findViewById(R.id.layout);
        motor = rootView.findViewById(R.id.mymotor);
        loca = rootView.findViewById(R.id.loca);


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
        String HI=DBConnector.executeQuery("SELECT gate FROM parking WHERE car_ID='J6U825' and update_time in (SELECT MAX(update_time) FROM parking GROUP BY car_ID)");
        String pay=DBConnector.executeQuery("SELECT debit FROM parking WHERE car_ID='J6U825' and update_time in (SELECT MAX(update_time) FROM parking GROUP BY car_ID)");
        String HI1 = HI.replace('"',',');
        h2 = HI1.split(",")[3];

        String p = pay.replace('"',',');
        h3 = p.split(",")[3];




        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction[0] = manager.beginTransaction();
                googlemap_search map = new googlemap_search();
                transaction[0].replace(R.id.a01, map, "map");
                transaction[0].commit();

            }
        });
        layer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c+=1;
                if(c%2==1){a =true;
                    mMapView.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap mMap) {
                            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                            mMap.clear(); //clear old markers
                            mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(lat,lng))
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.mylo))
                                    .title("目前位置"));

                            mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(22.995000,120.218050))
                                    .title("勝利路停車位")
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.minus))
                                    .rotation(90)
                                    .visible(a));


                        }
                    });
                }
                else {a=false;


                    mMapView.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap mMap) {
                            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                            mMap.clear(); //clear old markers
                            mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(lat,lng))
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.mylo))
                                    .title("目前位置"));



                        }
                    });
                }





            }
        });
        motor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction[0] = manager.beginTransaction();
                googlemap_motor map = new googlemap_motor();


                if(h2.equals("1")){


                    mMapView.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap mMap) {
                            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                            mMap.clear(); //clear old markers
                            CameraPosition googlePlex = CameraPosition.builder()
                                    .target(new LatLng(lat,lng))
                                    .zoom(17)
                                    .bearing(0)
                                    .tilt(45)
                                    .build();
                            mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(lat,lng))
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.mylo))
                                    .title("目前位置"));

                            mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(22.995000,120.218000))
                                    .title("your car!")
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.sun)));

                            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 3000, null);

                        }
                    });


                    d++;


                }
                else{
                    if(h3.equals("1")){
                        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                        alert.setTitle("扣款通知")
                                .setIcon(R.drawable.sun)
                                .setMessage("您已成功停車於A01車位，支付20$!")
                                .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(getContext(),"good",Toast.LENGTH_SHORT).show();
                                    }
                                });
                        alert.show();
                        h3="0";
                        Data data = new Data();
                        data.aaaa+=1;
                    }
                    mMapView.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap mMap) {
                        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        mMap.clear(); //clear old markers
                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(lat,lng))
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mylo))
                                .title("目前位置"));
                    }
                });}


                transaction[0].replace(R.id.a01, map, "map");
                transaction[0].commit();
            }
        });

        loca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction[0] = manager.beginTransaction();
                googlemap_loca maploca = new googlemap_loca();

                transaction[0].replace(R.id.a01, maploca, "map");

                transaction[0].commit();

                mMapView.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap mMap) {
                        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                        CameraPosition googlePlex = CameraPosition.builder()
                                .target(new LatLng(lat,lng))
                                .zoom(17)
                                .bearing(0)
                                .tilt(45)
                                .build();

                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(lat,lng))
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mylo))
                                .title("目前位置"));

                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 3000, null);

                    }
                });

            }
        });


        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                mMap.clear(); //clear old markers




                CameraPosition googlePlex = CameraPosition.builder()
                        .target(new LatLng(22.996773,120.218024))
                        .zoom(17)
                        .bearing(0)
                        .tilt(45)
                        .build();

           /*     mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(22.996773,120.218024))
                        .title("目前位置")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.logo)));*/


                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 2000, null);


            /*    mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(22.996700,120.218000))
                        .title("勝利路停車位路段")
                        .snippet("剩餘停車位:8"))
                        .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));


                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(22.996750,120.218050))
                        .title("成功大學路邊停車格"));*/
               mMap.setOnMarkerClickListener(gmapListner);


            }
        });



        return rootView;
    }

    private GoogleMap.OnMarkerClickListener gmapListner = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            String m = marker.getTitle();
            if(m.equals("勝利路停車位")){
                manager = getFragmentManager();
                final FragmentTransaction[] transaction = {manager.beginTransaction()};
                transaction[0] = manager.beginTransaction();
                googlemap_layer map = new googlemap_layer();

                transaction[0].replace(R.id.a01, map, "map");

                transaction[0].commit();}
            return true;
        }
    };
    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }



}