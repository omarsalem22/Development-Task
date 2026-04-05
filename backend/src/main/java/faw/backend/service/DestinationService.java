package faw.backend.service;

import faw.backend.entity.Destination;
import faw.backend.enums.Status;
import faw.backend.repository.DestinationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DestinationService {

    private final DestinationRepository destinationRepository;

    public Page<Destination> getAll(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
//if no search
        if (search == null || search.isBlank()) {
            return destinationRepository.findByStatus(
                    Status.APPROVED, pageable);
        }

        return destinationRepository.findByStatusAndNameContainingIgnoreCase(
                Status.APPROVED, search, pageable);
    }

    public Destination getOne(Long id) {
        return destinationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Destination not found"));
    }
}