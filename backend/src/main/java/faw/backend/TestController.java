package faw.backend;

import faw.backend.dto.HelloResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")   // Only needed if CORS still gives issue
public class TestController {

    @GetMapping("/hello")
    public HelloResponse hello() {
        return new HelloResponse("Hello from Spring Boot Backend!");
    }

    @GetMapping("/hello/{name}")
    public HelloResponse helloWithName(@PathVariable String name) {
        return new HelloResponse("Hello " + name + " from Spring Boot!");
    }
}