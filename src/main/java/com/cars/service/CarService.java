package com.cars.service;

import com.cars.model.Car;
import com.cars.repository.CarRepository;

public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public void create(String name, int price) {
        String category;
        if (price >= 55000) {
            category = "Family";
        }
        else if (price > 10000) {
            category = "Medium";
        } else {
            category = "Small";
        }
        carRepository.save(Car.of(name, price, category));
    }
}
