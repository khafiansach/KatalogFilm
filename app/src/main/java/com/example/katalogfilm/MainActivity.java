package com.example.katalogfilm;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (view, insets) -> {
                    Insets systemBars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars()
                    );

                    view.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;
                }
        );

        bottomNavigation = findViewById(R.id.bottomNavigation);

        if (savedInstanceState == null) {
            tampilkanFragment(new FilmFragment());
            bottomNavigation.setSelectedItemId(R.id.nav_film);
        }

        bottomNavigation.setOnItemSelectedListener(item -> {
            Fragment fragment;

            if (item.getItemId() == R.id.nav_film) {
                fragment = new FilmFragment();
            } else if (item.getItemId() == R.id.nav_profil) {
                fragment = new ProfilFragment();
            } else {
                return false;
            }

            tampilkanFragment(fragment);
            return true;
        });
    }

    private void tampilkanFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}
