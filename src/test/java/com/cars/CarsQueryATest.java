package com.cars;

import com.cars.controller.CarController;
import com.cars.dto.CarDto;
import com.cars.repository.CarRepository;
import com.cars.service.CarService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Quand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.cars.CarsATest.dataTableTransformEntries;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CarsQueryATest {

    private MockMvc mockMvc;

    @Autowired
    private CarRepository carRepository;
    @Autowired
    private CarService carService;

    private ResultActions resultActions;

    @Quand("on affiche une {string}")
    public void onAfficheUne(String name) throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(new CarController(carRepository, carService)).build();

        resultActions = this.mockMvc.perform(get("/api/car/" + name));
    }

    @Alors("on récupère les informations suivantes du body")
    public void onRécupèreLesInformationsSuivantesDuBody(DataTable dataTable) throws Exception {
        List<CarDto> expectedCarDtos = dataTableTransformEntries(dataTable, this::buildCarDto);

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().string("{\"name\":\""+expectedCarDtos.get(0).name()+"\",\"category\":\""+expectedCarDtos.get(0).category()+"\"}"));
    }

    private CarDto buildCarDto(Map<String, String> entry) {
        return new CarDto(entry.get("name"), entry.get("category"));
    }

    @Quand("on affiche toutes les voitures")
    public void onAfficheToutesLesVoitures() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(new CarController(carRepository, carService)).build();

        resultActions = this.mockMvc.perform(get("/api/car/")).andExpect(status().isOk());

    }

    @Alors("on récupère les voitures suivantes du body")
    public void onRécupèreLesVoituresSuivantesDuBody(DataTable dataTable) throws Exception {
        List<CarDto> expectedCarDtos = dataTableTransformEntries(dataTable, this::buildCarDto);

        String jsonArrayOfCars = expectedCarDtos.stream()
                .map(carDto -> "{\"name\":\"" + carDto.name() + "\",\"category\":\"" + carDto.category() + "\"}")
                .collect(Collectors.joining(","));
        jsonArrayOfCars = "[" + jsonArrayOfCars + "]";

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().string(jsonArrayOfCars));
    }
}
