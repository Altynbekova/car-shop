package com.altynbekova.carshop.dao;

import com.altynbekova.carshop.model.Car;

import java.util.List;

public class Repository {
    private static List<Car> cars = List.of(
            new Car("brand1", "model1", 2001,
                    "Description of car1", 10000, "photo1"),
            new Car("brand2", "model2", 2002,
                    "Description of car2", 20000, "photo2")
    );

    public List<Car> getAll(){
        return cars;
    }
}
