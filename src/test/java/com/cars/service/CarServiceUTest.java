package com.cars.service;

import com.cars.model.Car;
import com.cars.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CarServiceUTest {

    private CarService carService;

    private CarRepository carRepository = mock(CarRepository.class);

    @BeforeEach
    void setUp() {
        carService = new CarService(carRepository);
    }

    @Nested
    class CreateShould {
        @Test
        void save_a_car_with_small_category_when_price_less_or_equal_to_10000() {
            // when
            carService.create("twingo", 10000);

            // then
            ArgumentCaptor<Car> carArgumentCaptor = ArgumentCaptor.forClass(Car.class);
            verify(carRepository).save(carArgumentCaptor.capture());
            var savedCar = carArgumentCaptor.getValue();
            assertThat(savedCar.getName()).isEqualTo("twingo");
            assertThat(savedCar.getPrice()).isEqualTo(10000);
            assertThat(savedCar.getCategory()).isEqualTo("Small");
        }

        @Test
        void save_a_car_with_medium_category_when_price_between_10000_and_25000() {
            // when
            carService.create("megane", 25000);

            // then
            ArgumentCaptor<Car> carArgumentCaptor = ArgumentCaptor.forClass(Car.class);
            verify(carRepository).save(carArgumentCaptor.capture());
            var savedCar = carArgumentCaptor.getValue();
            assertThat(savedCar.getName()).isEqualTo("megane");
            assertThat(savedCar.getPrice()).isEqualTo(25000);
            assertThat(savedCar.getCategory()).isEqualTo("Medium");
        }

        @Test
        void save_a_car_with_family_category_when_price_greater_than_55000() {
            // when
            carService.create("Espace", 55000);

            // then
            ArgumentCaptor<Car> carArgumentCaptor = ArgumentCaptor.forClass(Car.class);
            verify(carRepository).save(carArgumentCaptor.capture());
            var savedCar = carArgumentCaptor.getValue();
            assertThat(savedCar.getName()).isEqualTo("Espace");
            assertThat(savedCar.getPrice()).isEqualTo(55000);
            assertThat(savedCar.getCategory()).isEqualTo("Family");
        }
    }
}