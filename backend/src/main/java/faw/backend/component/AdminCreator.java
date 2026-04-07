package faw.backend.component;

import faw.backend.entity.User;
import faw.backend.enums.Role;
import faw.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminCreator implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
// create admin when run the project if its has no admins
    @Override
    public void run(String... args) {
        if (!userRepository.existsByRole(Role.ADMIN)) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("omar@salem.com");
            admin.setPassword(passwordEncoder.encode("123456789"));
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);
            System.out.println("Default admin created: omar@salem.com / 123456789");
        }
    }
}