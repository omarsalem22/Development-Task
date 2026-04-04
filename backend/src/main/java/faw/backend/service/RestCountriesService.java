package faw.backend.service;

import faw.backend.dto.DestinationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestCountriesService {

    private static final String API_URL =
            "https://restcountries.com/v3.1/all?fields=name,capital,region,population,currencies,flags,cca2";

    private final RestTemplate restTemplate;

    public List<DestinationDTO> fetchAll() {
        DestinationDTO[] response = restTemplate.getForObject(API_URL, DestinationDTO[].class);
        if (response == null) return List.of();

        return Arrays.stream(response)
                .filter(c -> c.getName() != null)
                .filter(c -> c.getCapital() != null && !c.getCapital().isEmpty())
                .collect(Collectors.toList());
    }
}