package com.captech.VirtualThreadsTomcat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MainController {

    private final RestTemplate restTemplate;
    private final String downStreamURL;

    @Autowired
    public MainController(
            RestTemplate restTemplate,
            @Value("${captech.downstream.url}") String downstreamURL
            ) {
        this.restTemplate = restTemplate;
        this.downStreamURL = downstreamURL;
    }

    @GetMapping
    public List<Post> getAllPosts() {
        return List.of(this.restTemplate.getForEntity(downStreamURL, Post[].class).getBody());
    }
}
