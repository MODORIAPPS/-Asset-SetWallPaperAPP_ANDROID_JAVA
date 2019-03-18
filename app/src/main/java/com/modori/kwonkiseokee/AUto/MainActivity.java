package com.modori.kwonkiseokee.AUto;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.modori.kwonkiseokee.AUto.ViewPager.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {

    FrameLayout mainFrame;
    //Toolbar toolbar;

//    TabLayout tabLayout;
//    ViewPager viewPager;
    BottomNavigationView navBar;

    private int[] tabIcons = {
            R.drawable.photos_icon,
            R.drawable.photos_icon,
            R.drawable.setauto_icon
    };


    private static final int MY_PERMISSION_REQUEST_READ_EXTERNAL_STORAGE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);
        permissionCheck();

        mainFrame = findViewById(R.id.mainFrame);
        navBar = findViewById(R.id.navBar);
        //toolbar = findViewById(R.id.toolbar);

        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, new tab1_frag()).commit();

        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.goTranslate:
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, new tab1_frag()).commit();
                        return  true;

                    case R.id.goPhotos:
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, new tab2_frag()).commit();
                        return  true;

                    case R.id.goAutoSet:
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, new tab3_frag()).commit();
                        return  true;

                        default:
                            return false;
                }
            }
        });


    }

    public void permissionCheck() {
        //읽기 권한 체크
        int ReadpermissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        //기기에 권한이 있는지 없는지 체크하는 조건문
        if (ReadpermissionCheck == PackageManager.PERMISSION_DENIED) {
            //권한이 없는 경우, 없으므로 권한 요청 메세지 띄운다.
            //요청 메세지가 띄워지면 '허용'과 '거절'이 보이는데 둘중 어느 것을 누르냐에 대한 처리는 onRequestPermissionsResult에서 처리한다.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST_READ_EXTERNAL_STORAGE);

        } else {
            //권한이 있는 경우
            //Toast.makeText(MainActivity.this, "읽기 권한이 있었구만?", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //사용자가 권한 요청을 허용했을 때
                } else {
                    //Toast.makeText(MainActivity.this, "읽기 허용하라우", Toast.LENGTH_SHORT).show();
                    //사용자가 권한 요청을 거부했을 때

                }
        }
    }


}
