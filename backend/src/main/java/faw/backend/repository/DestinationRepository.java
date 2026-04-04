package faw.backend.repository;

import faw.backend.entity.Destination;
import faw.backend.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {
    Page<Destination> findByStatus(Status status, Pageable pageable);
    boolean existsByCountryCode(String countryCode);
}