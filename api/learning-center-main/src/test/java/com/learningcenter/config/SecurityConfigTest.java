package com.learningcenter.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SecurityConfigTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    void testSecurityConfigBeanExists() {
        assertNotNull(applicationContext.getBean(SecurityConfig.class),
                "SecurityConfig bean should exist in the application context");
    }

    @Test
    void testUserDetailsServiceBeanExists() {
        assertNotNull(userDetailsService,
                "UserDetailsService bean should be registered and available for autowiring");
    }

    @Test
    void testBCryptPasswordEncoderBeanExists() {
        assertNotNull(bCryptPasswordEncoder,
                "BCryptPasswordEncoder bean should be registered and available for autowiring");
    }

    @Test
    void testLoadAdminUserByUsername() {
        UserDetails userDetails = userDetailsService.loadUserByUsername("admin");
        assertNotNull(userDetails, "Admin user should be found");
        assertEquals("admin", userDetails.getUsername(), "Username should be 'admin'");
    }

    @Test
    void testAdminUserHasCorrectRole() {
        UserDetails userDetails = userDetailsService.loadUserByUsername("admin");
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")),
                "Admin user should have ROLE_USER authority");
    }

    @Test
    void testAdminUserPasswordIsEncoded() {
        UserDetails userDetails = userDetailsService.loadUserByUsername("admin");
        String storedPassword = userDetails.getPassword();

        // BCrypt encoded passwords start with $2a$, $2b$, or $2y$
        assertTrue(storedPassword.startsWith("$2") || storedPassword.startsWith("$2a"),
                "Password should be BCrypt encoded");

        // Verify the password matches when encoded
        assertTrue(bCryptPasswordEncoder.matches("admin", storedPassword),
                "BCryptPasswordEncoder should verify the admin password");
    }

    @Test
    void testAdminUserIsEnabled() {
        UserDetails userDetails = userDetailsService.loadUserByUsername("admin");
        assertTrue(userDetails.isEnabled(), "Admin user should be enabled");
    }

    @Test
    void testAdminUserAccountIsNotExpired() {
        UserDetails userDetails = userDetailsService.loadUserByUsername("admin");
        assertTrue(userDetails.isAccountNonExpired(), "Admin user account should not be expired");
    }

    @Test
    void testAdminUserAccountIsNotLocked() {
        UserDetails userDetails = userDetailsService.loadUserByUsername("admin");
        assertTrue(userDetails.isAccountNonLocked(), "Admin user account should not be locked");
    }

    @Test
    void testAdminUserCredentialsAreNotExpired() {
        UserDetails userDetails = userDetailsService.loadUserByUsername("admin");
        assertTrue(userDetails.isCredentialsNonExpired(), "Admin user credentials should not be expired");
    }

    @Test
    void testUserNotFoundThrowsException() {
        assertThrows(UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername("nonexistent"),
                "Should throw UsernameNotFoundException for non-existent user");
    }

    @Test
    void testBCryptEncoderCanEncodePasswords() {
        String rawPassword = "testPassword123";
        String encodedPassword = bCryptPasswordEncoder.encode(rawPassword);

        assertNotNull(encodedPassword, "Encoded password should not be null");
        assertNotEquals(rawPassword, encodedPassword, "Encoded password should differ from raw password");
        assertTrue(bCryptPasswordEncoder.matches(rawPassword, encodedPassword),
                "BCryptPasswordEncoder should verify encoded password");
    }

    @Test
    void testBCryptEncoderProducesDifferentHashesForSamePassword() {
        String rawPassword = "testPassword123";
        String encodedPassword1 = bCryptPasswordEncoder.encode(rawPassword);
        String encodedPassword2 = bCryptPasswordEncoder.encode(rawPassword);

        assertNotEquals(encodedPassword1, encodedPassword2,
                "BCrypt should produce different hashes for the same password due to salt");

        // But both should still match the raw password
        assertTrue(bCryptPasswordEncoder.matches(rawPassword, encodedPassword1),
                "First encoded password should match");
        assertTrue(bCryptPasswordEncoder.matches(rawPassword, encodedPassword2),
                "Second encoded password should match");
    }
}

