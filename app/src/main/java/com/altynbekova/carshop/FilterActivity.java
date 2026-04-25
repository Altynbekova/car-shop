package com.altynbekova.carshop;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.altynbekova.carshop.databinding.ActivityFilterBinding;

public class FilterActivity extends AppCompatActivity {
    private ActivityFilterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityFilterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {

            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.brand.getText().toString();
                int count = 0;
                //todo подсчитываем количество подходящих машин

                binding.filter.setText(count + " matches");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
        };
        binding.brand.addTextChangedListener(textWatcher);

        binding.filter.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra(Util.BRAND_KEY, binding.brand.getText().toString());
            intent.putExtra(Util.MODEL_KEY, binding.model.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
        });
    }
}