package com.altynbekova.carshop;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;

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
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class FilterActivity extends AppCompatActivity {
    private ActivityFilterBinding binding;
    private List<String> brands = new ArrayList<>();
    private Set<Integer> years = new TreeSet<>();

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
        years = repository.getAll().stream()
                .map(car -> car.getYear())
                .collect(Collectors.toSet());

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
                String modelName = binding.model.getText().toString().toLowerCase();
                int count = 0;
                for (Car car : repository.getAll()) {
                    if (brandName.equals(car.getBrand()) && modelName.equals(car.getModel())) {
                        count++;
                    }
                }

                binding.filter.setText(count + " matches");
            }
        };
        binding.brand.addTextChangedListener(textWatcher);
        binding.model.addTextChangedListener(textWatcher);

        binding.filter.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra(Util.BRAND_KEY, binding.brand.getText().toString());
            intent.putExtra(Util.MODEL_KEY, binding.model.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
        });

        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<Integer> adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, new Integer[]{0, 1000, 2000});
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        binding.cost1.setAdapter(adapter);
        binding.cost2.setAdapter(adapter);

        ArrayAdapter<Integer> yearAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, new ArrayList(years));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.year1.setAdapter(yearAdapter);
        binding.year2.setAdapter(yearAdapter);
        binding.year2.setSelection(years.size() - 1);
    }
}