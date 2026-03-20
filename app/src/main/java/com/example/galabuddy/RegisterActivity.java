package com.example.galabuddy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.galabuddy.databinding.ActivityRegisterBinding;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new Database(this);

        binding.signUpBtn.setOnClickListener(view -> {
            String username = binding.usernameInput.getText().toString().trim();
            String password = binding.passwordInput.getText().toString();
            String confirmPassword = binding.confirmPasswordInput.getText().toString();

            binding.usernameLayout.setErrorEnabled(false);
            binding.passwordLayout.setErrorEnabled(false);
            binding.confirmPasswordLayout.setErrorEnabled(false);

            ArrayList<Account> accountsList = db.getAllAccounts();
            ArrayList<String> usernames = new ArrayList<>();

            for (Account account : accountsList) {
                usernames.add(account.getUsername());
            }

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                if (username.isEmpty()) {
                    binding.usernameLayout.setErrorEnabled(true);
                    binding.usernameLayout.setError("This is required");
                }
                if (password.isEmpty()) {
                    binding.passwordLayout.setErrorEnabled(true);
                    binding.passwordLayout.setError("This is required");
                }
                if (confirmPassword.isEmpty()) {
                    binding.confirmPasswordLayout.setErrorEnabled(true);
                    binding.confirmPasswordLayout.setError("This is required");
                }
                Toast.makeText(this, "Please fill in all fields!", Toast.LENGTH_SHORT).show();
                return;
            } else if (usernames.contains(username)) {
                Toast.makeText(this, "This username already exists!", Toast.LENGTH_SHORT).show();
                binding.usernameLayout.setErrorEnabled(true);
                binding.usernameLayout.setError("Please use a different username");
                return;
            } else if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                binding.passwordLayout.setErrorEnabled(true);
                binding.passwordLayout.setError(" ");
                binding.confirmPasswordLayout.setErrorEnabled(true);
                binding.confirmPasswordLayout.setError(" ");
                return;
            }

            db.register(username, password);
            Toast.makeText(this, "Account Created Successfully!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
        });

        binding.cancelBtn.setOnClickListener(view -> startActivity(new Intent(this, LoginActivity.class)));
    }
}