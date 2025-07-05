package com.examsync.config;

import com.examsync.model.Institute;
import com.examsync.model.User;
import com.examsync.repository.InstituteRepository;
import com.examsync.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final InstituteRepository instituteRepo;
    private final UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // 1. Try to load Institute
        return instituteRepo.findByEmail(email)
                .map(inst -> {
                    if (!inst.isApproved()) {
                        throw new UsernameNotFoundException("Institute not approved yet.");
                    }
                    return new org.springframework.security.core.userdetails.User(
                            inst.getEmail(),
                            inst.getPassword(),
                            List.of(new SimpleGrantedAuthority("ROLE_INSTITUTE"))
                    );
                })

                // 2. If not found, try User
                .orElseGet(() -> {
                    User user = userRepo.findByEmail(email)
                            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
                    return new org.springframework.security.core.userdetails.User(
                            user.getEmail(),
                            user.getPassword(),
                            List.of(new SimpleGrantedAuthority(user.getRole().name()))
                    );
                });
    }
}
