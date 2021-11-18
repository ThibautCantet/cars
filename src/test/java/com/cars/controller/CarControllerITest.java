package com.cars.controller;

import com.cars.dto.CreateCarDto;
import com.cars.repository.CarRepository;
import com.cars.service.CarService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CarController.class)
class CarControllerITest {
    @MockBean
    private CarRepository carRepository;
    @MockBean
    private CarService carService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void post_should_create_new_car_and_return_created() throws Exception {
        // given
        String name = "twingo";
        int price = 10000;

        // when
        mockMvc.perform(post("/api/car/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(new CreateCarDto(name, price))))
                .andExpect(status().isCreated());
    }

    private String toJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMobjectMapper = new ObjectMapper();
        objectMobjectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = objectMobjectMapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(object);
    }
}