package com.example.yvime.e_parker;


import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.yvime.e_parker.news.news;
import com.example.yvime.e_parker.records.record;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigation_view;
    private Toolbar toolbar;

    private int TIME = 1000;
    private FragmentManager manager;
    private FragmentTransaction transaction;


    private LocationManager mLocationManager;
    private LocationListener mLocationListener;
    private Context context;
    private double lat,lng;
    private final int REQUEST=103;
    public final String LM_GPS = LocationManager.GPS_PROVIDER;
    public final String LM_NETWORK = LocationManager.NETWORK_PROVIDER;
    googlemap googlemap = new googlemap();
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = getSupportFragmentManager();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigation_view = (NavigationView) findViewById(R.id.navigation_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //是否聯網

        ConnectivityManager mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
//如果未連線的話，mNetworkInfo會等於null
        if(     mNetworkInfo.isConnected())
        {

        }


        // 用toolbar做為APP的ActionBar
        setSupportActionBar(toolbar);

        // 將drawerLayout和toolbar整合，會出現「三」按鈕
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //取得gps位址
        mLocationManager = (LocationManager) getSystemService(context.LOCATION_SERVICE);  //取得服務
        mLocationListener = new MyLocationListener();




        //引入googlemap至首頁
        transaction = manager.beginTransaction();
        googlemap map = new googlemap();
        transaction.replace(R.id.center, map, "map");

        transaction.commit();







        View header = navigation_view.getHeaderView(0);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "我的帳戶", Toast.LENGTH_SHORT).show();

                transaction = manager.beginTransaction();
                account account = new account();
                transaction.replace(R.id.center,account, "account");

                transaction.commit();
                drawerLayout.closeDrawer(GravityCompat.START);

            }
        });




        // 為navigatin_view設置點擊事件
        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {


            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                // 點選時收起選單
                drawerLayout.closeDrawer(GravityCompat.START);

                // 取得選項id
                int id = item.getItemId();

                // 依照id判斷點了哪個項目並做相應事件

                if (id == R.id.location) {
                    Toast.makeText(MainActivity.this, "尋找停車格", Toast.LENGTH_SHORT).show();
                    transaction = manager.beginTransaction();
                    googlemap map = new googlemap();
                    transaction.replace(R.id.center, map, "map");

                    transaction.commit();
                    return true;
                }
                else if (id == R.id.news) {

                    Toast.makeText(MainActivity.this, "最新消息", Toast.LENGTH_SHORT).show();
                    transaction = manager.beginTransaction();
                    news news = new news();
                    transaction.replace(R.id.center, news, "news");

                    transaction.commit();
                    return true;
                }
                else if (id == R.id.record) {

                    Toast.makeText(MainActivity.this, "停車紀錄", Toast.LENGTH_SHORT).show();
                    transaction = manager.beginTransaction();
                    record record = new record();
                    transaction.replace(R.id.center, record, "record");

                    transaction.commit();
                    return true;
                }
                else if (id == R.id.friend) {

                    Toast.makeText(MainActivity.this, "邀請朋友", Toast.LENGTH_SHORT).show();
                    transaction = manager.beginTransaction();
                   friend friend = new friend();
                    transaction.replace(R.id.center, friend, "friend");

                    transaction.commit();
                    return true;
                }
                else if (id == R.id.set) {

                    Toast.makeText(MainActivity.this, "設定", Toast.LENGTH_SHORT).show();
                    transaction = manager.beginTransaction();
                  setting setting = new setting();
                    transaction.replace(R.id.center,setting, "setting");

                    transaction.commit();
                     return true;
                }


                return false;
            }
        });
    }
    protected void onResume() {

        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getSystemService(context.LOCATION_SERVICE);
            mLocationListener = new MyLocationListener();
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //如果沒有取得權限的話，就跟使用者要求允許
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_NETWORK_STATE},REQUEST);
        }else {
            mLocationManager.requestLocationUpdates(LM_GPS, 2000, 5, mLocationListener);    //每2秒，並且使用者移動5公尺時update一次
            mLocationManager.requestLocationUpdates(LM_NETWORK, 2000, 5, mLocationListener);

        }


        super.onResume();
    }

    protected void onPause(){
        if (mLocationManager!=null){
            mLocationManager.removeUpdates(mLocationListener);
            mLocationManager=null;

        }
        super.onPause();
    }




    private class MyLocationListener implements LocationListener{

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onLocationChanged(Location location) {
            lat=location.getLatitude();
            lng=location.getLongitude();
            Data data = new Data();
            data.lat=String.valueOf(lat);
            data.lng=String.valueOf(lng);



        }




        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }


}
