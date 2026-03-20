package com.example.galabuddy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.galabuddy.databinding.ActivityLoginBinding;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new Database(this);

        binding.signInBtn.setOnClickListener(view -> {
            String username = binding.usernameInput.getText().toString();
            String password = binding.passwordInput.getText().toString();

            binding.usernameLayout.setErrorEnabled(false);
            binding.passwordLayout.setErrorEnabled(false);

            if (username.isEmpty() || password.isEmpty()) {
                if (username.isEmpty()) {
                    binding.usernameLayout.setErrorEnabled(true);
                    binding.usernameLayout.setError("This is required");
                }
                if (password.isEmpty()) {
                    binding.passwordLayout.setErrorEnabled(true);
                    binding.passwordLayout.setError("This is required");
                }
                Toast.makeText(this, "Please fill in all fields!", Toast.LENGTH_SHORT).show();
                return;
            }

            Account account = db.login(username, password);

            if (account != null) {
                PreferenceUtil.loggedUser = account;
                startActivity(new Intent(this, MainActivity.class));
            } else {
                Toast.makeText(this, "Username or Password is Incorrect!", Toast.LENGTH_SHORT).show();
            }
        });

        binding.registerBtn.setOnClickListener(view -> startActivity(new Intent(this, RegisterActivity.class)));
    }
}