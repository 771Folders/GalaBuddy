package com.example.galabuddy;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.galabuddy.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, 0);
            return insets;
        });

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentContainer, new MapsFragment())
                .commit();

        binding.bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_home) {
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragmentContainer, new MapsFragment())
                        .commit();
            }
            if (item.getItemId() == R.id.nav_saved) {
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragmentContainer, new SavedFragment())
                        .commit();
            }
            if (item.getItemId() == R.id.nav_settings) {
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragmentContainer, new SettingsFragment())
                        .commit();
            }
            return true;
        });
    }
}