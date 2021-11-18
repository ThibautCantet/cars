package com.cars;

import com.cars.controller.CarController;
import com.cars.dto.CreateCarDto;
import com.cars.dto.DuplicateCarDto;
import com.cars.dto.UpdatedCarDto;
import com.cars.repository.CarRepository;
import com.cars.service.CarService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Quand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CarsCommandATest {

    private MockMvc mockMvc;

    @Autowired
    private CarRepository carRepository;
    @Autowired
    private CarService carService;
    private ResultActions resultActions;

    @Quand("on crée une nouvelle voiture {string} à {int}€")
    public void onCréeUneNouvelleVoitureÀ€(String name, int price) throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(new CarController(carRepository, carService)).build();
        resultActions = mockMvc.perform(
                post("/api/car/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(new CreateCarDto(name, price)))
        );
    }

    @Alors("on reçoit un Created")
    public void onReçoitUnCreated() throws Exception {
        resultActions.andExpect(status().isCreated());
    }

    @Quand("on duplique une {string} en {string} à {int}€")
    public void onDupliqueUneEnÀ€(String originalName, String newName, int newPrice) throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(new CarController(carRepository, carService)).build();

        resultActions = mockMvc.perform(
                post("/api/car/" + originalName)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(new DuplicateCarDto(newName, newPrice)))
                );
    }

    @Quand("on met à jour le prix à {int} de la voiture \\({int})")
    public void onMetÀJourLePrixÀDeLaVoiture(int newPrice, int id) throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(new CarController(carRepository, carService)).build();

        resultActions = mockMvc.perform(
                patch("/api/car/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(new UpdatedCarDto(newPrice)))
        );
    }

    @Alors("on reçoit un OK")
    public void onReçoitUnOK() throws Exception {
        resultActions.andExpect(status().isNoContent());
    }

    @Alors("on reçoit un non modifié")
    public void onReçoitUnNonModifié() throws Exception {
        resultActions.andExpect(status().isNotModified());
    }

    private String toJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMobjectMapper = new ObjectMapper();
        objectMobjectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = objectMobjectMapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(object);
    }
}
