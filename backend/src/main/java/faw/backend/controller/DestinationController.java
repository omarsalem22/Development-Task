package faw.backend.controller;

import faw.backend.entity.Destination;
import faw.backend.service.DestinationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/destinations")
@RequiredArgsConstructor
public class DestinationController {

    private final DestinationService destinationService;

    @GetMapping
    public ResponseEntity<Page<Destination>> getAll(
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "")   String search
    ) {
        return ResponseEntity.ok(destinationService.getAll(page, size, search));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Destination> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(destinationService.getOne(id));
    }
}