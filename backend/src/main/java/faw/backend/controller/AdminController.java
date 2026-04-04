package faw.backend.controller;

import faw.backend.dto.DestinationDTO;
import faw.backend.entity.Destination;
import faw.backend.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    // 1. fetch preview from REST Countries (does not save)
    @GetMapping("/fetch")
    public ResponseEntity<List<DestinationDTO>> fetchFromApi() {
        return ResponseEntity.ok(adminService.fetchFromApi());
    }

    // 2. add single destination
    @PostMapping("/destinations")
    public ResponseEntity<Destination> addOne(@RequestBody DestinationDTO dto) {
        return ResponseEntity.ok(adminService.addOne(dto));
    }

    // 3. bulk add
    @PostMapping("/destinations/bulk")
    public ResponseEntity<Map<String, Integer>> bulkAdd(@RequestBody List<DestinationDTO> dtos) {
        int saved = adminService.bulkAdd(dtos);
        return ResponseEntity.ok(Map.of("saved", saved));
    }

    // 4. remove (soft delete)
    @DeleteMapping("/destinations/{id}")
    public ResponseEntity<Void> remove(@PathVariable Long id) {
        adminService.remove(id);
        return ResponseEntity.noContent().build();
    }

    // 5. get all saved destinations
    @GetMapping("/destinations")
    public ResponseEntity<List<Destination>> getSaved() {
        return ResponseEntity.ok(adminService.getSaved());
    }
}