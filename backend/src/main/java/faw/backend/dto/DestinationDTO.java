package faw.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DestinationDTO {

    private NameDTO name;
    private List<String> capital;
    private String region;
    private Long population;
    private Map<String, CurrencyDTO> currencies;
    private FlagsDTO flags;
    private String cca2;  // country code

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class NameDTO {
        private String common;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CurrencyDTO {
        private String name;
        private String symbol;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FlagsDTO {
        private String png;
    }
}