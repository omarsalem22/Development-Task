package faw.backend.repository;

import faw.backend.entity.Wishlist;
import faw.backend.entity.User;
import faw.backend.entity.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findByUser(User user);
    Optional<Wishlist> findByUserAndDestination(User user, Destination destination);
    boolean existsByUserAndDestination(User user, Destination destination);
}