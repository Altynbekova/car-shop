package com.altynbekova.carshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.altynbekova.carshop.model.Car;

import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolder> {
    private LayoutInflater layoutInflater;
    private List<Car> cars;

    public CarAdapter(Context context, List<Car> cars) {
        this.layoutInflater = LayoutInflater.from(context);
        this.cars = cars;
    }

    @Override
    public void onBindViewHolder(@NonNull CarAdapter.ViewHolder holder, int position) {
        Car car = cars.get(position);
        holder.photo.setImageResource(Util.getDrawableId(car.getPhoto()));
        holder.info.setText(String.format("%s, %s (%s)", car.getBrand(), car.getModel(), car.getYear()));
        holder.description.setText(car.getDescription());
        holder.cost.setText(String.valueOf(car.getCost()));
    }

    @NonNull
    @Override
    public CarAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView photo;
        private TextView info, description, cost;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.photo);
            info = itemView.findViewById(R.id.info);
            description = itemView.findViewById(R.id.description);
            cost = itemView.findViewById(R.id.cost);
        }
    }
}
