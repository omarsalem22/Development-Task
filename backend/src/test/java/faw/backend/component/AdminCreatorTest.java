package faw.backend.component;

import faw.backend.entity.User;
import faw.backend.enums.Role;
import faw.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminCreatorTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AdminCreator adminCreator;

    @BeforeEach
    void setUp() {
    }

    // No admin exists → should create one
    @Test
    void run_WhenNoAdminExists_ShouldCreateDefaultAdmin() throws Exception {
        when(userRepository.existsByRole(Role.ADMIN)).thenReturn(false);
        when(passwordEncoder.encode("123456789")).thenReturn("hashed_password");

        adminCreator.run();

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertEquals("admin",           savedUser.getUsername());
        assertEquals("omar@salem.com",  savedUser.getEmail());
        assertEquals("hashed_password", savedUser.getPassword());
        assertEquals(Role.ADMIN,        savedUser.getRole());
    }

    //  Admin already exists → should NOT create another one
    @Test
    void run_WhenAdminAlreadyExists_ShouldNotCreateAdmin() throws Exception {
        when(userRepository.existsByRole(Role.ADMIN)).thenReturn(true);

        adminCreator.run();

        verify(userRepository, never()).save(any(User.class));
        verify(passwordEncoder, never()).encode(anyString());
    }

    // ✅ CASE 3: Password is encoded, never stored as plain text
    @Test
    void run_ShouldStoreEncodedPassword_NotPlainText() throws Exception {
        when(userRepository.existsByRole(Role.ADMIN)).thenReturn(false);
        when(passwordEncoder.encode("123456789")).thenReturn("$2a$encoded");

        adminCreator.run();

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        assertNotEquals("123456789", userCaptor.getValue().getPassword());
        assertEquals("$2a$encoded", userCaptor.getValue().getPassword());
    }

    // ✅ CASE 4: existsByRole is always checked exactly once
    @Test
    void run_ShouldAlwaysCheckForExistingAdmin() throws Exception {
        when(userRepository.existsByRole(Role.ADMIN)).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("hashed");

        adminCreator.run();

        verify(userRepository, times(1)).existsByRole(Role.ADMIN);
    }

    // ✅ CASE 5: Assigned role must be ADMIN, not USER or anything else
    @Test
    void run_ShouldAssignAdminRole() throws Exception {
        when(userRepository.existsByRole(Role.ADMIN)).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("hashed");

        adminCreator.run();

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        assertNotEquals(Role.USER, userCaptor.getValue().getRole());  // adjust Role.USER to your actual non-admin role
        assertEquals(Role.ADMIN,  userCaptor.getValue().getRole());
    }

    // ✅ CASE 6: save() is called exactly once when no admin exists
    @Test
    void run_WhenNoAdminExists_ShouldSaveExactlyOnce() throws Exception {
        when(userRepository.existsByRole(Role.ADMIN)).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("hashed");

        adminCreator.run();

        verify(userRepository, times(1)).save(any(User.class));
    }
}