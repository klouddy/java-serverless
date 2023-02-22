package com.captech.SpringMicroserviceExample;

import com.captech.SpringMicroserviceExample.response.PersonResponse;
import com.github.javafaker.Faker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/people")
public class PeopleController {

    private final Faker faker = new Faker();

    @GetMapping
    public List<PersonResponse> getPeople(
            @RequestParam Integer num
    ) {
        List<PersonResponse> personResponses = new ArrayList<>();
        for(int i = 0; i < num; i++){
            personResponses.add(
                    PersonResponse.builder()
                            .firstName(faker.name().firstName())
                            .lastName(faker.name().lastName())
                            .title(faker.job().title())
                            .quote(faker.backToTheFuture().quote())
                            .build()

            );
        }
        return personResponses;
    }
}
