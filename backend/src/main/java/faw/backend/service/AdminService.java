package faw.backend.service;

import faw.backend.dto.DestinationDTO;
import faw.backend.entity.Destination;
import faw.backend.enums.Status;
import faw.backend.repository.DestinationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final DestinationRepository destinationRepository;
    private final RestCountriesService  restCountriesService;
    private final JdbcTemplate          jdbcTemplate;

    // fetch from REST Countries — preview only, does NOT save
    public List<DestinationDTO> fetchFromApi() {
        return restCountriesService.fetchAll();
    }

    // save a single destination
    public Destination addOne(DestinationDTO dto) {
        Destination d = toEntity(dto);
        return destinationRepository.save(d);
    }

    // bulk save — uses JdbcTemplate for performance
    public int bulkAdd(List<DestinationDTO> dtos) {
        // i use nateive qyury
        String sql = """
            INSERT INTO destinations
              (name, capital, region, population, currency, flag_url, country_code, status)
            VALUES (?, ?, ?, ?, ?, ?, ?, 'APPROVED')
            ON CONFLICT DO NOTHING
            """;

        int[][] result = jdbcTemplate.batchUpdate(sql, dtos, 50, (ps, dto) -> {
            ps.setString(1, dto.getName().getCommon());
            ps.setString(2, dto.getCapital().get(0));
            ps.setString(3, dto.getRegion());
            ps.setLong(4,   dto.getPopulation() != null ? dto.getPopulation() : 0L);
            ps.setString(5, extractCurrency(dto));
            ps.setString(6, dto.getFlags() != null ? dto.getFlags().getPng() : null);
            ps.setString(7, dto.getCca2());
        });

        return Arrays.stream(result)
                .mapToInt(batch -> Arrays.stream(batch).sum())
                .sum();
    }

    public void remove(Long id) {
        destinationRepository.findById(id).ifPresent(d -> {
            d.setStatus(Status.REMOVED);
            destinationRepository.save(d);
        });
    }

    // get all saved APPROVED destinations
    public List<Destination> getSaved() {
        return destinationRepository.findAll()
                .stream()
                .filter(d -> d.getStatus() == Status.APPROVED)
                .toList();
    }

    private String extractCurrency(DestinationDTO dto) {
        if (dto.getCurrencies() == null || dto.getCurrencies().isEmpty()) return "N/A";
        var entry = dto.getCurrencies().entrySet().iterator().next();
        return entry.getKey() + " - " + entry.getValue().getName();
    }

    private Destination toEntity(DestinationDTO dto) {
        return Destination.builder()
                .name(dto.getName().getCommon())
                .capital(dto.getCapital().get(0))
                .region(dto.getRegion())
                .population(dto.getPopulation())
                .currency(extractCurrency(dto))
                .flagUrl(dto.getFlags() != null ? dto.getFlags().getPng() : null)
                .countryCode(dto.getCca2())
                .status(Status.APPROVED)
                .build();
    }
}