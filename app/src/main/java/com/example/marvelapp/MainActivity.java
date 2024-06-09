package com.example.marvelapp;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {



    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    ComicFragment comicFragment = new ComicFragment();
    PerfilFragment perfilFragment = new PerfilFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.inicio) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
                    return true;
                } else if (itemId == R.id.comic) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new ComicFragment()).commit();
                    return true;
                } else if (itemId == R.id.setting) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new PerfilFragment()).commit();
                    return true;
                } else {
                    return false;
                }
            }
        });

    }
}