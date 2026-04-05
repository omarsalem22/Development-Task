package faw.backend.service;

import faw.backend.entity.Destination;
import faw.backend.entity.User;
import faw.backend.entity.Wishlist;
import faw.backend.repository.DestinationRepository;
import faw.backend.repository.UserRepository;
import faw.backend.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository  wishlistRepository;
    private final UserRepository      userRepository;
    private final DestinationRepository destinationRepository;

    // get all wishlist destinations for the logged-in user
    public List<Destination> getWishlist(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return wishlistRepository.findByUser(user)
                .stream()
                .map(Wishlist::getDestination)
                .toList();
    }

    // toggle — add if not exists, remove if exists
    public boolean toggle(String email, Long destinationId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Destination destination = destinationRepository.findById(destinationId)
                .orElseThrow(() -> new RuntimeException("Destination not found"));

        var existing = wishlistRepository.findByUserAndDestination(user, destination);

        if (existing.isPresent()) {
            wishlistRepository.delete(existing.get());
            return false; // removed
        } else {
            wishlistRepository.save(
                    Wishlist.builder().user(user).destination(destination).build());
            return true;  // added
        }
    }

    // check if a destination is in the user's wishlist
    public boolean isWishlisted(String email, Long destinationId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Destination destination = destinationRepository.findById(destinationId)
                .orElseThrow(() -> new RuntimeException("Destination not found"));
        return wishlistRepository.existsByUserAndDestination(user, destination);
    }
}