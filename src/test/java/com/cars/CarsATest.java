package com.cars;

import com.cars.model.Car;
import com.cars.repository.CarRepository;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Etantdonné;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
@Transactional
@AutoConfigureCache
@AutoConfigureDataJpa
@AutoConfigureTestEntityManager
@SpringBootTest
@DirtiesContext
@CucumberContextConfiguration
public class CarsATest {

    @Autowired
    protected TestEntityManager entityManager;

    @Autowired
    protected CarRepository carRepository;

    private final List<Car> savedCars = new ArrayList<>();

    @Etantdonné("Les voitures suivantes")
    public void lesVoituresSuivantes(DataTable dataTable) {
        List<Car> cars = dataTableTransformEntries(dataTable, this::buildCar);

        for (Car car : cars) {
            var savedCar = entityManager.persist(car);
            savedCars.add(savedCar);
        }
    }

    private Car buildCar(Map<String, String> entry) {
        return Car.of(entry.get("name"), Integer.parseInt(entry.get("price")), entry.get("category"));
    }

    private Car buildCarInfo(Map<String, String> entry) {
        if (entry.containsKey("price")) {
            return Car.of(entry.get("name"), Integer.parseInt(entry.get("price")), entry.get("category"));
        }
        return Car.of(entry.get("name"), entry.get("category"));
    }

    public static <T> List<T> dataTableTransformEntries(DataTable dataTable, Function<Map<String, String>, T> transformFunction) {
        final List<T> transformResults = new ArrayList<>();
        final List<Map<String, String>> dataTableEntries = dataTable.asMaps(String.class, String.class);
        dataTableEntries.forEach(mapEntry -> {
            transformResults.add(transformFunction.apply(mapEntry));
        });
        return transformResults;
    }

    @Alors("on récupère les informations suivantes de la base")
    public void onRécupèreLesInformationsSuivantesDeLaBase(DataTable dataTable) {
        List<Car> expectedCars = dataTableTransformEntries(dataTable, this::buildCarInfo);

        final Integer carId = carRepository.findAll().iterator().next().getId();
        final Car car = carRepository.findById(carId).get();
        assertThat(car.getName()).isEqualTo(expectedCars.get(0).getName());
        assertThat(car.getPrice()).isEqualTo(expectedCars.get(0).getPrice());
        assertThat(car.getCategory()).isEqualTo(expectedCars.get(0).getCategory());
    }

    private Integer findFirstSavedCarId() {
        return savedCars.get(0).getId();
    }
}
