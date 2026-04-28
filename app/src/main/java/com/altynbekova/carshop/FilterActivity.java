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

import com.altynbekova.carshop.dao.Repository;
import com.altynbekova.carshop.databinding.ActivityFilterBinding;
import com.altynbekova.carshop.model.Car;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FilterActivity extends AppCompatActivity {
    private ActivityFilterBinding binding;
    private List<String> brands = new ArrayList<>();

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

        Repository repository = new Repository();
        for (Car car : repository.getAll()) {
            brands.add(car.getBrand().toLowerCase());
        }

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String brandName = binding.brand.getText().toString().toLowerCase();
                int count = 0;
                for (String brand : brands) {
                    for (Object o : repository.getAll().stream().map(
                            car -> car.getModel()
                    ).collect(Collectors.toList())) {

                    }
                    if(brandName.equals(brand)){
                        count++;
                    }
                }
                binding.filter.setText(count + " matches");
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