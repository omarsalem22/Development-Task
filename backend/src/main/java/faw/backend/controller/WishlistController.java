package faw.backend.controller;

import faw.backend.entity.Destination;
import faw.backend.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    // get user's wishlist
    @GetMapping
    public ResponseEntity<List<Destination>> getWishlist(Principal principal) {
        return ResponseEntity.ok(wishlistService.getWishlist(principal.getName()));
    }

    // toggle a destination (add or remove)
    @PostMapping("/{destinationId}")
    public ResponseEntity<Map<String, Boolean>> toggle(
            @PathVariable Long destinationId,
            Principal principal) {
        boolean added = wishlistService.toggle(principal.getName(), destinationId);
        return ResponseEntity.ok(Map.of("wishlisted", added));
    }
}