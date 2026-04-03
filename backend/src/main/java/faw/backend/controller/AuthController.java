package faw.backend.controller;

import faw.backend.dto.request.RegisterRequestDto;
import faw.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDto request){
        return ResponseEntity.ok(userService.register(request));   }
}
