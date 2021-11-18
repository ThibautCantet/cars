package com.cars.controller;

import com.cars.dto.CreateCarDto;
import com.cars.repository.CarRepository;
import com.cars.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CarControllerUTest {
    private CarController carController;

    private CarService carService = mock(CarService.class);
    private CarRepository carRepository = mock(CarRepository.class);

    @BeforeEach
    void setUp() {
        carController = new CarController(carRepository, carService);
    }

    @Nested
    class CreateShould {
        @Test
        void call_carService_create() {
            // given
            CreateCarDto createCarDto = new CreateCarDto("twingo", 10000);

            // when
            carController.create(createCarDto);

            // then
            verify(carService).create("twingo", 10000);
        }

        @Test
        void return_created_as_http_status() {
            // given
            CreateCarDto createCarDto = new CreateCarDto("twingo", 10000);

            // when
            var response = carController.create(createCarDto);

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        }
    }
}