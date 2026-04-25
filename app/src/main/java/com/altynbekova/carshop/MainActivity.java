package com.altynbekova.carshop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;


import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.altynbekova.carshop.dao.Repository;
import com.altynbekova.carshop.databinding.ActivityMainBinding;
import com.altynbekova.carshop.model.Car;
import com.google.android.material.snackbar.Snackbar;

import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private CarAdapter carAdapter;
    private List<Car> cars = new ArrayList<>();

    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK &&
                            result.getData() != null) {
                        Intent intent = result.getData();
                        String brand = intent.getStringExtra(Util.BRAND_KEY);
                        String model = intent.getStringExtra(Util.MODEL_KEY);
                        List<Car> filtered = new ArrayList<>();
                        for (Car car : cars) {
                            if(brand.equals(car.getBrand()) &&
                                    model.equals(car.getModel())){
                                filtered.add(car);
                            }
                        }
                        /*filtered = cars.stream().filter(car -> brand.equals(car.getBrand()))
                                .collect(Collectors.toList());*/

                        cars.clear();
                        cars.addAll(filtered);
                        carAdapter.notifyDataSetChanged();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        RecyclerView recyclerView = binding.cars;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Repository repository = new Repository();
        cars = new ArrayList<>( repository.getAll() );
        carAdapter = new CarAdapter(this, cars,
                (car, position) -> {
                    Intent intent = new Intent(this, DetailActivity.class);

                    intent.putExtra(Util.MODEL_KEY, car.getModel());
                    intent.putExtra(Util.BRAND_KEY, car.getBrand());
                    intent.putExtra(Util.YEAR_KEY, car.getYear());
                    intent.putExtra(Util.COST_KEY, car.getCost());
                    startActivity(intent);
                });
        recyclerView.setAdapter(carAdapter);

        binding.search.setOnClickListener(v -> {
            mStartForResult.launch(new Intent(this, FilterActivity.class));
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}