package faw.backend.service;

import faw.backend.dto.request.LoginRequest;
import faw.backend.dto.request.RegisterRequestDto;
import faw.backend.dto.response.LoginResponse;
import faw.backend.entity.User;
import faw.backend.enums.Role;
import faw.backend.repository.UserRepository;
import faw.backend.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;




// here i make first user is the admin but it's not good way

//    public String register(RegisterRequestDto req) {
//        if (userRepository.findByEmail(req.getEmail()).isPresent()) {
//            throw new RuntimeException("Email already taken");
//        }
//
//        User user = new User();
//        user.setUsername(req.getUsername());
//        user.setEmail(req.getEmail());
//        user.setPassword(passwordEncoder.encode(req.getPassword()));
//
//  // fist one is admin and enyone else i normal user
//        if (userRepository.count() == 0) {
//            user.setRole(Role.ADMIN);
//        } else {
//            user.setRole(Role.USER);
//        }
//
//        userRepository.save(user);
//        return "user registered successfully";
//    }

    public String register(RegisterRequestDto req) {
        if (userRepository.findByEmail(req.getEmail()).isPresent()) {
            throw new RuntimeException("Email already taken");
        }

        User user = new User();
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole(Role.USER);  // always USER, admin created by seeder

        userRepository.save(user);
        return "user registered successfully";
    }

    public LoginResponse login(LoginRequest request) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(()-> new RuntimeException("User not found"));
        var jwtToken = jwtUtil.generateToken(user);
        return LoginResponse.builder().token(jwtToken).username(user.getUsername()).role(user.getRole().name()).build();

    }
}
